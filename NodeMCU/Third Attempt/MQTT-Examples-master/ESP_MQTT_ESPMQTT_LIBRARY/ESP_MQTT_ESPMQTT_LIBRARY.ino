
/*
 * Library used:
 * https://github.com/i-n-g-o/esp-mqtt-arduino
 * 
 * 
 * Code by Andreas Spiess
 * 
 * 
 * 
 */






#include <ESP8266WiFi.h>
#include <MQTT.h>


/*
 *  I save my credentials in a file to protect them. Please comment this line and fill out
 *  your credentials below
 *
 */
//#include "credentials_CloudMQTT.h"
#include "credentials_Adafruit_IO.h"

/*
 * 
#define ssid          "..."
#define password      "..."

#define SERVER          "io.adafruit.com"
#define SERVERPORT      1883
#define MQTT_USERNAME   "..."
#define MQTT_KEY        "..."

#define USERNAME          "sensorsiot/"
#define PREAMBLE          "feeds/"
#define T_LUMINOSITY      "luminosity"
#define T_CLIENTSTATUS    "clientStatus"
#define T_COMMAND         "command"
*
*/

unsigned long entry;
bool clientStatu;
float luminosity;


// create MQTT object
MQTT myMqtt(USERNAME, SERVER, SERVERPORT);

//
void setup() {
  Serial.begin(115200);
  delay(100);
  Serial.print("Connecting to ");
  Serial.println(ssid);


  WiFi.begin(ssid, password);

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }

  Serial.println("");
  Serial.println("WiFi connected");
  Serial.println("IP address: ");
  Serial.println(WiFi.localIP());

  Serial.println("Connecting to MQTT server");

  // setup callbacks
  myMqtt.onConnected(myConnectedCb);
  myMqtt.onDisconnected(myDisconnectedCb);
  myMqtt.onPublished(myPublishedCb);
  myMqtt.onData(myDataCb);

  Serial.println("connect mqtt...");
  myMqtt.setUserPwd(MQTT_USERNAME, MQTT_KEY);
  myMqtt.connect();
  delay(10);
}

//
void loop() {
  yield();
  int luminosity = analogRead(A0);

  String valueStr(luminosity);
  String statStr(clientStatu);

  // publish value to topic
  myMqtt.publish(USERNAME  PREAMBLE  T_LUMINOSITY, valueStr);
  myMqtt.publish(USERNAME  PREAMBLE  T_CLIENTSTATUS, statStr);

  delay(1000);
}


//----------------------------------------------------------------------
void myConnectedCb() {
  Serial.println("connected to MQTT server");

  Serial.println("subscribe to topic...");
  myMqtt.subscribe(USERNAME  PREAMBLE  T_COMMAND,1);
}

void myDisconnectedCb() {
  Serial.println("disconnected. try to reconnect...");

  delay(500);
  myMqtt.connect();
}

void myPublishedCb() {
  // Serial.println("published.");
}

void myDataCb(String& topic, String& data) {

  Serial.print(topic);
  Serial.print(": ");
  Serial.println(data);
  if (data[1] == 'F')  clientStatu = false;
  if (data[1] == 'N')  clientStatu = true;
}



