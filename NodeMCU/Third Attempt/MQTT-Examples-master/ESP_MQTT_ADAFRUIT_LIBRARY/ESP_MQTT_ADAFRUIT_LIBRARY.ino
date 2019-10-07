/***************************************************
  Adafruit MQTT Library ESP8266 Example

  Must use ESP8266 Arduino from:
    https://github.com/esp8266/Arduino

  Works great with Adafruit's Huzzah ESP board:
  ----> https://www.adafruit.com/product/2471

  Adafruit invests time and resources providing this open source code,
  please support Adafruit and open-source hardware by purchasing
  products from Adafruit!

  Written by Tony DiCola for Adafruit Industries.
  MIT license, all text above must be included in any redistribution
 ***************************************************
 *
 * Code by Andreas Spiess
  */
#include <ESP8266WiFi.h>
#include "Adafruit_MQTT.h"
#include "Adafruit_MQTT_Client.h"
#include "credentials_CloudMQTT.h"

/*
 *  I save my credentials in a file to protect them. Please comment this line and fill out
 *  your credentials below
 *
 */
//#include "credentials_Adafruit_IO.h"

#define LED  15


/* definition of credentials and feeds
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
bool clientStatus, prevClientStatus;
float luminosity, prevLumiosity;
unsigned long entryL, entryS;



#define userID         "sensorsiot"


// Create an ESP8266 WiFiClient class to connect to the MQTT server.
WiFiClient client;

// Setup the MQTT client class by passing in the WiFi client and MQTT server and login details.
Adafruit_MQTT_Client mqtt(&client, SERVER, SERVERPORT, MQTT_USERNAME, MQTT_KEY);

/****************************** Feeds ***************************************/

// Setup a feed called 'photocell' for publishing.
// Notice MQTT paths for AIO follow the form: <username>/feeds/<feedname>
const char LUMINOSITY_FEED[] PROGMEM = USERNAME  PREAMBLE  T_LUMINOSITY;
Adafruit_MQTT_Publish tLuminosity = Adafruit_MQTT_Publish(&mqtt, LUMINOSITY_FEED);

// Setup a feed called 'clientStatus' for publishing.
// Notice MQTT paths for AIO follow the form: <username>/feeds/<feedname>
const char CLIENTSTATUS_FEED[] PROGMEM = USERNAME  PREAMBLE  T_CLIENTSTATUS;
Adafruit_MQTT_Publish tClientStatus = Adafruit_MQTT_Publish(&mqtt, CLIENTSTATUS_FEED);

// Setup a feed called 'command' for subscribing to changes.
const char COMMAND_FEED[] PROGMEM = USERNAME  PREAMBLE  T_COMMAND;
Adafruit_MQTT_Subscribe tCommand = Adafruit_MQTT_Subscribe(&mqtt, COMMAND_FEED);

// Setup a feed called 'test' for subscribing to changes.
const char TEST_FEED[] PROGMEM = USERNAME  PREAMBLE  "test";
Adafruit_MQTT_Subscribe tTest = Adafruit_MQTT_Subscribe(&mqtt, TEST_FEED);



//
void setup() {
  Serial.begin(115200);
  delay(100);
  pinMode(LED, OUTPUT);



  connectWLAN();

  Serial.println("Connecting to MQTT server");
  entry = 0;
  Serial.begin(115200);
  delay(10);
  // connect to WiFi
  connectWLAN();

  // Setup MQTT subscription for onoff feed.
  mqtt.subscribe(&tCommand);
  mqtt.subscribe(&tTest);

  Serial.println(USERNAME  PREAMBLE  T_COMMAND);

}

//
void loop() {
  yield();
  // Ensure the connection to the MQTT server is alive (this will make the first
  // connection and automatically reconnect when disconnected).  See the MQTT_connect
  // function definition further below.
  connectMQTT();

  int hi = receiveCommand();
  if (hi == 1) clientStatus = true;
  if (hi == 0) clientStatus = false;

  int luminosity = analogRead(A0);

  if (abs(luminosity - prevLumiosity) > 90 || (millis() - entryL > 1000)) {  // publish if value changed or after 1 second
    //   Serial.print(" LUMinosity: ");
    //   Serial.println(abs(luminosity - prevLumiosity));
    if (publishLuminosity(luminosity)) entryL = millis();
    prevLumiosity = luminosity;
    entryL = millis();
  }

  if (clientStatus != prevClientStatus || (millis() - entryS > 1000)) {  // publish if value changed or after 1 second
    //    Serial.print(clientStatus );
    //    Serial.print(" STATUS ");
    //    Serial.println(prevClientStatus);
    publishClientStatus(clientStatus);
    prevClientStatus = clientStatus;
    entryS = millis();
  }
}

void connectWLAN() {
  // Connect to WiFi access point.
  Serial.println();
  Serial.println();
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
}


void connectMQTT() {
  int8_t ret;

  // Stop if already connected.
  if (mqtt.connected()) {
    return;
  }
  Serial.print("Connecting to MQTT... ");

  while ((ret = mqtt.connect()) != 0) { // connect will return 0 for connected
    Serial.println(mqtt.connectErrorString(ret));
    Serial.println("Retrying MQTT connection in 5 seconds...");
    mqtt.disconnect();
    delay(5000);  // wait 5 seconds
  }
  Serial.println("MQTT Connected!");
}


bool publishLuminosity(float value) {
  // Now we can publish stuff!
  bool success = false;
  Serial.print(F("\nSending Luminosity "));
  Serial.print(value);
  Serial.print("...");
  if (! tLuminosity.publish(value, 1)) {
    Serial.println(F("Failed"));
  } else {
    Serial.println(F("OK!"));
  }
  return success;
}

bool publishClientStatus(bool clientStat) {
  bool success = false;
  Serial.print(F("\nSending clientStatus "));
  Serial.print(clientStat);
  Serial.print("...");

  if (! tClientStatus.publish(clientStat, 1)) Serial.println(F("Failed"));
  else {
    Serial.println(F(" OK!"));
    success = true;
  }
  return success;
}



int receiveCommand() {
  int clientSt = 99;
  // this is our 'wait for incoming subscription packets' busy subloop
  Adafruit_MQTT_Subscribe *subscription;
  while ((subscription = mqtt.readSubscription(10))) {
    if (subscription == &tCommand) {
      Serial.print(F("Got: "));
      Serial.println((char*)tCommand.lastread);

      // Switch on the LED if an 1 was received as first character
      if (tCommand.lastread[1] == 'N') {
        clientSt = true;
        digitalWrite(LED, HIGH);
        //digitalWrite(BUILTIN_LED, LOW);   // Turn the LED on (Note that LOW is the voltage level
        // but actually the LED is on; this is because
        // it is acive low on the ESP-01)
      }
      if (tCommand.lastread[1] == 'F') {
        clientSt = false;
        digitalWrite(LED, LOW);
        //digitalWrite(BUILTIN_LED, HIGH);  // Turn the LED off by making the voltage HIGH
      }

    }
    if (subscription == &tTest) {
      Serial.print(F("Got: "));
      Serial.println((char*)tCommand.lastread);

      // Switch on the LED if an 1 was received as first character
      if (tTest.lastread[1] == 'N') {
        clientSt = true;
        digitalWrite(LED, HIGH);
        //digitalWrite(BUILTIN_LED, LOW);   // Turn the LED on (Note that LOW is the voltage level
        // but actually the LED is on; this is because
        // it is acive low on the ESP-01)
      }
      if (tTest.lastread[1] == 'F') {
        clientSt = false;
        digitalWrite(LED, LOW);
        //digitalWrite(BUILTIN_LED, HIGH);  // Turn the LED off by making the voltage HIGH
      }
    }
  }
  return clientSt;
}
