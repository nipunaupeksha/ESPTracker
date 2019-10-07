#include <ESP8266WiFi.h>
#include <ESP8266WebServer.h>
#include <ArduinoJson.h>

ESP8266WebServer server(80);

const char* deviceName = "NodeMCU Module";
const char* ssid="Thinuli";
const char* password="ck0713425680";

IPAddress ip(192,168,1,114);
IPAddress gateway(192,168,1,1);
IPAddress subnet(255,255,255,0);

/**********************************/
int strengths [9]={00,00,00,00,00,00,00,00,00};
int iteration=0;
String ss;
String comma ="," ;
String S1 = "**" ;
String S2 = "**" ;
String S3 = "**" ;
String S4 = "**" ;
String S5 = "**" ;
String S6 = "**" ;
String S7 = "**" ;
String S8 = "**" ;
String S9 = "**" ;
/**********************************/

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
    Javascript+="setTimeout(function(){location.reload();},5000);\n"; //'process()'
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
  handleData();
  XML ="<?xml version='1.0'?>";
  XML+="<data>";
  XML+=data;
  XML+="</data>";
  server.send(200,"text/xml",XML);
  }

void handleData(){
  int n = WiFi.scanNetworks();
  if (n == 0){
    Serial.println("No Networks Found");
  }
  else {
    for (int i = 0; i < 20; ++i)
    {
      if ((WiFi.SSID(i) == "Sanota1")||(WiFi.SSID(i) == "Sanota2")||(WiFi.SSID(i) == "Sanota3")||(WiFi.SSID(i) == "Sanota4")||(WiFi.SSID(i) == "Sanota5")||(WiFi.SSID(i) == "Sanota6")||(WiFi.SSID(i) == "Sanota7")||(WiFi.SSID(i) == "Sanota8")||(WiFi.SSID(i) == "Sanota9")){
       if ((-1*WiFi.RSSI(i))>9){
        if ((WiFi.SSID(i) == "Sanota1")){
          strengths [0]= -1*WiFi.RSSI(i);
          S1 = String(strengths[0]);
        }
        else if ((WiFi.SSID(i) == "Sanota2")){
          strengths [1]= -1*WiFi.RSSI(i);
          S2 = String(strengths[1]);          
        }
        else if ((WiFi.SSID(i) == "Sanota3")){
          strengths [2]= -1*WiFi.RSSI(i);
          S3 = String(strengths[2]);          
        }         
        else if ((WiFi.SSID(i) == "Sanota4")){
          strengths [3]= -1*WiFi.RSSI(i);
          S4 = String(strengths[3]);          
        }
        else if ((WiFi.SSID(i) == "Sanota5")){
          strengths [4]= -1*WiFi.RSSI(i);
          S5 = String(strengths[4]);          
        }
        else if ((WiFi.SSID(i) == "Sanota6")){
          strengths [5]= -1*WiFi.RSSI(i);
          S6 = String(strengths[5]);          
        }
        else if ((WiFi.SSID(i) == "Sanota7")){
          strengths [6]= -1*WiFi.RSSI(i);
          S7 = String(strengths[6]);          
        }         
        else if ((WiFi.SSID(i) == "Sanota8")){
          strengths [7]=-1*WiFi.RSSI(i);
          S8 = String(strengths[7]);          
        }             
        else if((WiFi.SSID(i) == "Sanota9")){
          strengths [8]= -1*WiFi.RSSI(i);
          S9 = String(strengths[8]);          
        }
      }
       //delay(10);      
    }
   }
  } 
  iteration=iteration+1;
  if(iteration==10){
    iteration=0;
    } 
  ss = (String)iteration+"A"+comma+S1+comma+S2+comma+S3+comma+S4+comma+S5+comma+S6+comma+S7+comma+S8+comma+S9+comma+"BB";
  S1 = "**" ;
  S2 = "**" ;
  S3 = "**" ;
  S4 = "**" ;
  S5 = "**" ;
  S6 = "**" ;
  S7 = "**" ;
  S8 = "**" ;
  S9 = "**" ;
  data=ss;
  server.send(200,"text/plain",data);
  }

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
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
  Serial.print(WiFi.localIP());
  server.on("/",WebsiteContent);
  server.on("/xml",XMLcontent);
  server.on("/handle",handleData);
  server.begin();
  //server.handleClient();
  //ESP.deepSleep(30 * 1e6);
}

void loop() {
  for(int i=0;i<20;i++){
  server.handleClient();
  delay(1000);
  }
  //server.end();
  ESP.deepSleep(30 * 1e6);
}
     
    
