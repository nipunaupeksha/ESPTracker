#include <ESP8266WiFi.h>
#include <ESP8266WebServer.h>
#include <SoftwareSerial.h>

SoftwareSerial NodeMCU(D2,D3);
ESP8266WebServer server(80);

const char* deviceName = "NodeMCU Module";
const char* ssid="Thinuli";
const char* password="ck0713425680";

IPAddress ip(192,168,1,114);
IPAddress gateway(192,168,1,1);
IPAddress subnet(255,255,255,0);

String Website,XML,Javascript;
String data=(String)0;
int inc=0;

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
    Javascript+="setTimeout('process()',200);\n";
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
  Serial.begin(9600);
  NodeMCU.begin(9600);
  pinMode(D2,INPUT);
  pinMode(D3,OUTPUT);
  WiFi.begin(ssid,password);
  WiFi.disconnect();
  WiFi.hostname(deviceName);
  WiFi.config(ip,gateway,subnet);
  WiFi.begin(ssid,password);
  WiFi.mode(WIFI_STA);
  while(WiFi.status()!=WL_CONNECTED){
  Serial.print(".");
  delay(500);
  }
  Serial.println(WiFi.localIP());
  server.on("/",WebsiteContent);
  server.on("/xml",XMLcontent);
  server.begin();
  data = random(0,100);
  Serial.println(data);
  server.handleClient();
  ESP.deepSleep(3000);
}

void loop() {
  /*
  inc = inc + 1;
  
  while(NodeMCU.available()==0){
    Serial.print(".");
    delay(500);
    }
    data=inc;
  if(NodeMCU.available()>0){
    String val=NodeMCU.readString();
    if(val.length()>0){
      data=val;
      //Serial.println(data);
      }
    }
  server.handleClient();*/
}
     
    
