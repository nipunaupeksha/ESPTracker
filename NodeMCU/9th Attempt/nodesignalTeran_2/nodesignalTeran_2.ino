#include <ESP8266WiFi.h>

int strengths [9]={00,00,00,00,00,00,00,00,00};
int iteration=10;

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

String str=" ";

int LED=4;

void setup() {
  pinMode(LED,OUTPUT);
  digitalWrite(LED,HIGH);
  Serial.begin(9600);

  
  WiFi.mode(WIFI_STA);
  WiFi.disconnect(); 
  delay(50);
  
  digitalWrite(LED,LOW);
  delay(20);
  digitalWrite(LED,HIGH);

}

void loop() {  
  int n = WiFi.scanNetworks();
  
  
  if (n == 0){
    
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
    }
   }
  } 
  
  
  iteration=iteration+1;
  if(iteration==99){
    iteration=10;
  }
  
  ss = (String)iteration+comma+S1+comma+S2+comma+S3+comma+S4+comma+S5+comma+S6+comma+S7+comma+S8+comma+S9+comma+"BB";

  
  S1 = "**" ;
  S2 = "**" ;
  S3 = "**" ;
  S4 = "**" ;
  S5 = "**" ;
  S6 = "**" ;
  S7 = "**" ;
  S8 = "**" ;
  S9 = "**" ;

 while(str.length()<100){
  str=Serial.readString();
  str.trim();
  Serial.println(ss); 
  delay(50);  
 }


 ESP.deepSleep(10000000);
}
