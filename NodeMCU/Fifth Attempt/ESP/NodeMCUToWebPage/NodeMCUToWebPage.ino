#include <ESP8266WiFi.h>
#include <ESP8266WebServer.h>
#include <SoftwareSerial.h>

//SoftwareSerial NodeMCU(D2,D3);
ESP8266WebServer server(80);

const char* deviceName = "NodeMCU Module";
const char* ssid="WiFi-Router";
const char* password="T0JENMTJ7N2";

IPAddress ip(192,168,8,114);
IPAddress gateway(192,168,8,1);
IPAddress subnet(255,255,255,0);

String Website,XML,Javascript;
String data=(String)0;

unsigned long timeNow=0;
long period = 1000*5;

void javascriptContent(){
    Javascript ="<SCRIPT>\n";
    Javascript+="var xmlHttp=createXmlHttpObject();\n";
    Javascript+="function createXmlHttpObject(){\n";
    Javascript+="if(window.XMLHttpRequest){\n";
    Javascript+="xmlHttp=new XMLHttpRequest();\n";
    Javascript+="}else{\n";
    Javascript+="xmlHttp=new ActiveXObject('Microsoft.XMLHTTP');\n";
    Javascript+="}\n";
    Javascript+="return xmlHttp;\n";
    Javascript+="}\n";
    Javascript+="\n";
    Javascript+="function response(){\n";
    Javascript+="xmlResponse=xmlHttp.responseXML;\n";
    Javascript+="xmldoc = xmlResponse.getElementsByTagName('data');\n";
    Javascript+="message = xmldoc[0].firstChild.nodeValue;\n";
    Javascript+="document.getElementById('div1').innerHTML=message;\n";
    Javascript+="}\n";
    Javascript+="function process(){\n";
    Javascript+="xmlHttp.open('PUT','xml',true);\n";
    Javascript+="xmlHttp.onreadystatechange=response;\n";
    Javascript+="xmlHttp.send(null);\n";
    Javascript+="setTimeout(function(){location.reload();},500);\n";
    Javascript+="}\n";
    Javascript+="</SCRIPT>\n";
  }

void WebsiteContent(){
    javascriptContent();
    Website="<html>\n";
    Website+="<body onload='process()'>";
    Website+="<div id='div1'>"+data+"</div></body></html>";
    Website+=Javascript;
    server.send(200,"text/html",Website);
  }

void XMLcontent(){

  XML ="<?xml version='1.0'?>";
  XML+="<data>";
  XML+=data;
  XML+="</data>";
  
  server.send(200,"text/xml",XML);
  }

void setup() {
  // put your setup code here, to run once:



  
  //NodeMCU.begin(9600);
 // pinMode(D2,INPUT);
 // pinMode(D3,OUTPUT);
  WiFi.begin(ssid,password);
  WiFi.disconnect();
  WiFi.hostname(deviceName);
  WiFi.config(ip,gateway,subnet);
  WiFi.begin(ssid,password);
  WiFi.mode(WIFI_STA);
  while(WiFi.status()!=WL_CONNECTED){
    delay(500);
  }

///////////////////////////////////////////////////
  Serial.begin(9600);
  while(Serial.available()<0){
     Serial.begin(9600);
  }
  
  String val=Serial.readString();
  while(val.length()<30){
    val=Serial.readString();
    if(val.length()<40){
      data=val;
    }
  }

///////////////////////////////////////////////////////
  
  //Serial.print(WiFi.localIP());
  server.on("/",WebsiteContent);
  server.on("/xml",XMLcontent);
  server.begin();


}

void loop() {

   server.handleClient();
   timeNow =millis();
   while(millis()<(timeNow+period)){
    server.handleClient();
    }
   ESP.deepSleep(10000000);
}
 /* timeNow=millis();
  while(millis()<(timeNow+period)){
  if(Serial.available()>0){
    String val=Serial.readString();
    if(val.length()>1){
      if(val.length()<40){data=val;
     
        }
      }
    }
  }
  ESP.deepSleep(20 * 1e6);
}
     */
    
