// UNIGRANRIO - Universidade do Grande Rio
// Matheus Castro

#include <EtherCard.h>

#define REQUEST_RATE 5000 // milliseconds

//Ethernet interface MAC Address
static byte mymac[] = { 0x74,0x69,0x69,0x2D,0x31,0x31 };

//Conectivity
const char server[] = "192.168.25.100";

//Response
byte Ethernet::buffer[700];
boolean hasResponse = false;
static uint32_t timer;

//Sensor
volatile byte pulseCount;
const byte sensorPin        = 2;
const byte sensorInterrupt  = 0;
// The hall-effect flow sensor outputs approximately 4.5 pulses per second per
// litre/minute of flow.
float calibrationFactor = 4.5;

//Flow
int count;
Stash stash;
String type;
float totalValue;
float availableValue;
unsigned long oldTime;


//Method called when the request is complete
static void response (byte status, word off, word len) {
  String result = String((const char*) Ethernet::buffer + off);
  int separatorIndex = result.indexOf("&");
  type  = result.substring(0,separatorIndex);
  availableValue = (result.substring(separatorIndex+1,result.length())).toFloat();
  Serial.println("Flow Restriction:" + type);
  Serial.print("Available Flow:");
  Serial.println(availableValue);
  hasResponse = true;
}

//Interrupt Service Routine
void pulseCounter(){
  // Increment the pulse counter
  pulseCount++;
}

void setup() {
  Serial.begin(57600);
  Serial.println("\nUNIGRANRIO");
  
  //Active Pin Sensor Flow
  pinMode(sensorPin, INPUT);
  digitalWrite(sensorPin, HIGH);
  
  //Init Values
  count             = 0;
  oldTime           = 0;
  pulseCount        = 0;
  totalValue        = 0;
  availableValue    = 0;


  // The Hall-effect sensor is connected to pin 2 which uses interrupt 0.
  // Configured to trigger on a FALLING state change (transition from HIGH
  // state to LOW state)
  attachInterrupt(sensorInterrupt, pulseCounter, FALLING);

  //Active Ethernet
  if (ether.begin(sizeof Ethernet::buffer, mymac) == 0) 
    Serial.println("Failed to access Ethernet controller");
  
  //Setup DHCP
  if (!ether.dhcpSetup())
    Serial.println("DHCP failed");
  
  //GET IP's
  ether.printIp("IP    :", ether.myip);
  ether.printIp("GW IP :", ether.gwip);
  ether.printIp("DNS IP:", ether.dnsip);

  //DNS Lookup
  if (!ether.dnsLookup(server))
    Serial.println("DNS failed");
  ether.printIp("Server:", ether.hisip);
  
  //SET PORT
  ether.hisport = 8081;
}

void loop() {
  if(hasResponse == false){
    ether.packetLoop(ether.packetReceive());
    if (millis() > timer + REQUEST_RATE) {
     timer = millis();
     ether.browseUrl(PSTR("/cfa/flows/available/101\r\n"), NULL,"Accept: text/plain\r\n", server, response);
    }
  }else{
    if(availableValue > 0){
      if((millis() - oldTime) > 1000) { // Only process counters once per second  
        count++;
        // Disable the interrupt while calculating flow rate and sending the value to
        // the host
        detachInterrupt(sensorInterrupt);
        // Because this loop may not complete in exactly 1 second intervals we calculate
        // the number of milliseconds that have passed since the last execution and use
        // that to scale the output. We also apply the calibrationFactor to scale the output
        // based on the number of pulses per second per units of measure (litres/minute in
        // this case) coming from the sensor.
        float flowRate = ((1000.0 / (millis() - oldTime)) * pulseCount) / calibrationFactor;

        // Note the time this processing pass was executed. Note that because we've
        // disabled interrupts the millis() function won't actually be incrementing right
        // at this point, but it will still return the value it was set to just before
        // interrupts went away.
        oldTime = millis();
        
        // Divide the flow rate in litres/minute by 60 to determine how many litres have
        // passed through the sensor in this 1 second interval, then multiply by 1000 to
        // convert to millilitres.
        unsigned int flowMilliLitres = (flowRate / 60) * 1000;
        if(flowMilliLitres > 0){
          Serial.println(flowMilliLitres);
          totalValue += flowMilliLitres;
        }

        if(type == "TIME"){
          availableValue--;  
        } else {
          availableValue -= flowMilliLitres;  
        }
        
        if(count == 60){
          Serial.println("1min");
          count = 0;
          byte sd = stash.create();
          stash.print("uuid=");
          stash.print(97);
          stash.print("&userId=");
          stash.print(101);
          stash.print("&measure=");
          stash.println(totalValue);
          stash.save();
          Stash::prepare(PSTR("POST /cfa/flows HTTP/1.1" "\r\n"
                      "Host: $F" "\r\n"
                      "Content-type: application/x-www-form-urlencoded" "\r\n"
                      "Content-Length: $D" "\r\n"
                      "\r\n"
                      "$H"), server, stash.size(), sd);
          ether.tcpSend();
          totalValue = 0;
        }
        
        // Reset the pulse counter so we can start incrementing again
        pulseCount = 0;
        
        // Enable the interrupt again now that we've finished sending output
        attachInterrupt(sensorInterrupt, pulseCounter, FALLING);
      }
    } else {
      delay(10000);
      Serial.println("Fluxo indisponível");
    }
  }
}


