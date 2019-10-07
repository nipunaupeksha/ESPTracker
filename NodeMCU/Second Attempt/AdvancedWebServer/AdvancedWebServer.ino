/*
 * Copyright (c) 2015, Majenko Technologies
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright notice, this
 *   list of conditions and the following disclaimer in the documentation and/or
 *   other materials provided with the distribution.
 *
 * * Neither the name of Majenko Technologies nor the names of its
 *   contributors may be used to endorse or promote products derived from
 *   this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

#include <ESP8266WiFi.h>
#include <WiFiClient.h>
#include <ESP8266WebServer.h>
#include <ESP8266WiFi.h>
#include <ESP8266mDNS.h>

//const char *ssid = "PROLINK_H5004NK_DBBCC";
//const char *password = "A6036cd12";

const char *ssid = /*WiFi-Router*/"Thinuli";
const char *password = /*T0JENMTJ7N2*/"ck0713425680";

IPAddress ip(192,168,1,114);
IPAddress gateway(192,168,1,1);
IPAddress subnet(255,255,255,0);

union command_message
{
  struct 
  {
    unsigned char header1;
    unsigned char header2;
    unsigned char device_id;
    unsigned char function;
    unsigned short address;
    unsigned short count_value;
    unsigned char checksum;
  }_;
  unsigned char uc[9];
};

union short_values
{
  short s;
  unsigned char uc[2];
};

byte read_state=0,write_state=0;
byte device_id,function,short_count;
short start_address, read_values[128];
long read_sent_time=0, write_sent_time=0;

ESP8266WebServer server ( 80 );

const int led = 13;

void handleRoot() {
	digitalWrite ( led, 1 );
	char temp[1200];

	snprintf ( temp, 1200,

"<html>\
  <head>\
    <title>Inverter</title>\
    <style>\
      body { background-color: #cccccc; font-family: Arial, Helvetica, Sans-Serif; Color: #000088; }\
    </style>\
  </head>\
  <body>\
    <form action=\"/read\" method=\"post\">\
      <fieldset>\
        <legend>Read From Device</legend>\
        Device ID:<br>\
        <input type=\"text\" name=\"id\" value=\"1\"><br>\
        Start Address:<br>\
        <input type=\"text\" name=\"addr\" value=\"\"><br><br>\
        Count:<br>\
        <input type=\"text\" name=\"count\" value=\"\"><br><br>\
        <input type=\"submit\" value=\"Read\">\
      </fieldset>\
    </form>\
    <form action=\"/write\" method=\"post\">\
      <fieldset>\
        <legend>Write To Device</legend>\
        Device ID:<br>\
        <input type=\"text\" name=\"id\" value=\"1\"><br>\
        Address:<br>\
        <input type=\"text\" name=\"addr\" value=\"\"><br><br>\
        Value:<br>\
        <input type=\"text\" name=\"value\" value=\"\"><br><br>\        
        <input type=\"submit\" value=\"Write\">\
      </fieldset>\
    </form>\
  </body>\
</html>"
	);
	server.send ( 200, "text/html", temp );
	digitalWrite ( led, 0 );
}

void handleNotFound() {
	digitalWrite ( led, 1 );
	String message = "File Not Found\n\n";
	message += "URI: ";
	message += server.uri();
	message += "\nMethod: ";
	message += ( server.method() == HTTP_GET ) ? "GET" : "POST";
	message += "\nArguments: ";
	message += server.args();
	message += "\n";

	for ( uint8_t i = 0; i < server.args(); i++ ) {
		message += " " + server.argName ( i ) + ": " + server.arg ( i ) + "\n";
	}

	server.send ( 404, "text/plain", message );
	digitalWrite ( led, 0 );
}

void setup ( void ) {
	pinMode ( led, OUTPUT );
	digitalWrite ( led, 0 );
	Serial.begin (9600);

  //Serial1.begin(11500);
  
	WiFi.mode ( WIFI_STA );
	WiFi.begin ( ssid, password );
  /*
  IPAddress ip(192,168,43,120);   
  IPAddress gateway(192,168,43,1);   
  IPAddress subnet(255,255,255,0); */  
  WiFi.config(ip, gateway, subnet);
  
	//Serial1.println ( "" );

	// Wait for connection
	while ( WiFi.status() != WL_CONNECTED ) {
		delay ( 500 );
		//Serial1.print ( "." );
	}

	//Serial1.println ( "" );
	Serial.print ( "Connected to " );
	Serial.println ( ssid );
	Serial.print ( "IP address: " );
	Serial.println ( WiFi.localIP() );

	if ( MDNS.begin ( "esp8266" ) ) {
		//Serial1.println ( "MDNS responder started" );
	}

	server.on ( "/", handleRoot );
	server.on ( "/read", handleRead );
  server.on ( "/write", handleWrite );
	server.on ( "/inline", []() {
		server.send ( 200, "text/plain", "this works as well" );
	} );
	server.onNotFound ( handleNotFound );
	server.begin();
	//Serial1.println ( "HTTP server started" );
}

void loop ( void ) {
  byte byte_count,count;
  byte comm_state=0, checksum=0, b_count,s_count;
  byte rx;
  union short_values temp;
  
	server.handleClient();


  /*
  while(Serial.available()>0)
  {
    rx = Serial.read();
    switch(comm_state)
    {
      case 0:
        if('S'==rx)
          comm_state=1;
        break;
      case 1:
        if('e'==rx)
          comm_state=2;
        else
          comm_state=0; 
        break;
      case 2:
        switch(rx)
        {
          case 'S':
            //status_bar_set_text_thread_safe("Command Execution Successful");
            if(1==read_state)
              read_state=3;
            if(1==write_state)
              write_state=3;
            comm_state=0;
            break;
          case 'E':
            //status_bar_set_text_thread_safe("Command Execution Error");
            if(1==read_state)
              read_state=4;
            if(1==write_state)
              write_state=4;
            comm_state=0;
            break;
          case 'I':
            //status_bar_set_text_thread_safe("Invalid Command Parameter Or Checksum Error");
            if(1==read_state)
              read_state=5;
            if(1==write_state)
              write_state=5;
            comm_state=0;
            break;
          default:
            device_id=rx;
            checksum=rx;
            comm_state=3;
        }
        break;
      case 3:
        function=rx;
        checksum+=rx;
        comm_state=4;
        break;
      case 4:
        byte_count=rx;
        checksum+=rx;
        count=0;
        s_count=0;
        b_count=0;
        comm_state=5;
        break;
      case 5:
        if(count<byte_count)
        {
          checksum+=rx;
          temp.uc[b_count++]=rx;
          if(b_count>1)
          {
            b_count=0;
            read_values[s_count]=temp.s;
            s_count++;
          }
          count++;
        }
        else
        {
          checksum+=rx;
          if(0==checksum)
          {
            read_state=2;
          }
          else
          {
            read_state=6;
          }
          comm_state=0;
        }
        break;
      default:
        comm_state=0;
    }
  }
  if((millis()-read_sent_time)>10000)
    read_state=0;
  if((millis()-write_sent_time)>10000)
    write_state=0;*/
    int n =WiFi.scanNetworks();
}

void handleRead() {
  union command_message cmd;
  short id,addr,count;
  unsigned char cs;
  String reply;

  switch(read_state)
  {
    case 0:
      if(server.args()>2)
      {
        id=server.arg(0).toInt();
        addr=server.arg(1).toInt();
        count=server.arg(2).toInt();
    
        cmd._.header1='S';
        cmd._.header2='e';
        cmd._.device_id=id;
        cmd._.function=3;
        cmd._.address=addr;
        cmd._.count_value=count;
        
        start_address=addr;
        short_count=count;        
        
        cs=0;
        for(int i=2;i<8;i++)
        {
          cs+=cmd.uc[i];
        }
        cmd._.checksum = -cs;
        Serial.write(cmd.uc,9);
        read_sent_time=millis();
        read_state=1;
      }
      else
      {
        server.send ( 203, "text/plain", "Insufficient arguments" );
        break;
      }
    case 1:
      server.send ( 200, "text/html", "<html>\
                                          <head>\
                                            <meta http-equiv='refresh' content='5'/>\
                                          </head>\
                                          <body>\
                                            Command sent\
                                          </body>\
                                        </html>" );
      break;
    case 2:
      reply = "<html><body>";
      for(int i=0;i<short_count;i++)
      {
        reply += (start_address+i);
        reply += ",";
        reply += read_values[i];
        reply += "<br/>";
      }
      reply += "</body></html>";
      server.send ( 200, "text/html", reply );
      read_state=0;
      break;
    case 3:
      server.send ( 200, "text/plain", "Command Successful" );
      read_state=0;
      break;
    case 4:
      server.send ( 500, "text/plain", "Execution Error" );
      read_state=0;
      break;
    case 5:
      server.send ( 500, "text/plain", "Invalid Parameter or Tx Checksum Error" );
      read_state=0;
      break;
    case 6:
      server.send ( 500, "text/plain", "Rx Checksum Error" );
      read_state=0;
      break;
  }
}

void handleWrite() {
  union command_message cmd;
  short id,addr,value;
  unsigned char cs;
  String reply;

  switch(write_state)
  {
    case 0:
      if(server.args()>2)
      {
        id=server.arg(0).toInt();
        addr=server.arg(1).toInt();
        value=server.arg(2).toInt();
    
        cmd._.header1='S';
        cmd._.header2='e';
        cmd._.device_id=id;
        cmd._.function=6;
        cmd._.address=addr;
        cmd._.count_value=value;

        cs=0;
        for(int i=2;i<8;i++)
        {
          cs+=cmd.uc[i];
        }
        cmd._.checksum = -cs;
        Serial.write(cmd.uc,9);
        write_sent_time = millis();
        write_state=1;
      }
      else
      {
        server.send ( 203, "text/plain", "Insufficient arguments" );
        break;
      }
    case 1:
      server.send ( 200, "text/html", "<html>\
                                          <head>\
                                            <meta http-equiv='refresh' content='5'/>\
                                          </head>\
                                          <body>\
                                            Command sent\
                                          </body>\
                                        </html>" );
      break;
    case 3:
      server.send ( 200, "text/plain", "Command Successful" );
      write_state=0;
      break;
    case 4:
      server.send ( 500, "text/plain", "Execution Error" );
      write_state=0;
      break;
    case 5:
      server.send ( 500, "text/plain", "Invalid Parameter or Tx Checksum Error" );
      write_state=0;
      break;
  }
}
