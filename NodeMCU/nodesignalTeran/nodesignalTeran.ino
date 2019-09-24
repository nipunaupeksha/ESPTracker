#include <SoftwareSerial.h>
#include <ESP8266WiFi.h>
SoftwareSerial NodeMCU(D2,D3);
int strengths [9]={00,00,00,00,00,00,00,00,00};
int iteration=0;
//int c=0;
//String cc[]={"1A,10,20,30,40,50,60,70,80,90,BB","2A,11,21,31,41,51,61,71,81,91,BB","3A,12,22,32,42,52,62,72,82,92,BB","4A,13,23,33,43,53,63,73,83,93,BB","5A,10,20,30,40,50,60,70,80,90,BB","6A,10,20,30,40,50,60,70,80,90,BB","7A,10,20,30,40,50,60,70,80,90,BB","8A,10,20,30,40,50,60,70,80,90,BB","9A,10,20,30,40,50,60,70,80,90,BB"};

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

void setup() {
  NodeMCU.begin(9600);
  pinMode(D2,INPUT);
  pinMode(D3,OUTPUT);
  Serial.begin(9600);
  Serial.println(""); 
  
  WiFi.mode(WIFI_STA);
  WiFi.disconnect(); 
  delay(100);
  
  Serial.println("WiFi Netwoek Scan Started");
}

void loop() {
 
  int n = WiFi.scanNetworks();
   
  if (n == 0){
    Serial.println("No Networks Found");
  }
  else {
    for (int i = 0; i < n; ++i)
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
       delay(10);      
    }
   }
  } 
  iteration=iteration+1;
  if(iteration==10){
    iteration=0;
    } 
  ss = (String)iteration+"A"+comma+S1+comma+S2+comma+S3+comma+S4+comma+S5+comma+S6+comma+S7+comma+S8+comma+S9+comma+"BB";
  Serial.println(ss); 
 
  NodeMCU.print(ss);
  
 
 S1 = "**" ;
 S2 = "**" ;
 S3 = "**" ;
 S4 = "**" ;
 S5 = "**" ;
 S6 = "**" ;
 S7 = "**" ;
 S8 = "**" ;
 S9 = "**" ;
  
  
  
  
  
  //delay(1000);
}
