#include <CayenneMQTTESP8266.h>
#define CAYENNE_DEBUG
#define CAYENNE_PRINT Serial
char ssid[]="Thinuli";
char password[]="ck0713425680";

char username[] ="1cc69a40-e413-11e9-b49d-5f4b6757b1bf"; 
char mqtt_password[]="8df1f577ad5c2a57b7860a9ac593cc779d5aa31f";
char client_id[]="38348b70-e413-11e9-a38a-d57172a4b4d4";
void setup() {
  // put your setup code here, to run once:
  Cayenne.begin(username,mqtt_password,client_id,ssid,password);
  pinMode(2,OUTPUT);
  digitalWrite(2,HIGH);
}

void loop() {
  // put your main code here, to run repeatedly:
  Cayenne.loop();
}

CAYENNE_IN(0){
  digitalWrite(2,!getValue.asInt());
  }
