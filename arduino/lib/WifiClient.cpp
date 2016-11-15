//
//  WifiClient.cpp
//  
//
//  Created by Matheus Castro on 2/20/16.
//
//

#include "WifiClient.h"

WifiClient::WifiClient(SoftwareSerial &_wifi){
    wifi = &_wifi;
}

/****  Basic  ****/
bool WifiClient::test(){    
    return findOK(command("AT\r\n", 1500));
}
bool WifiClient::restart(){
    return findOK(command("AT+RST\r\n", 2000));
}
String WifiClient::version(){
    String response = command("AT+GMR\r\n", 2000);
    int okIndex = response.indexOf("OK");
    if(okIndex != -1) {
        response.replace("OK\r\n","");
    } 
   return response; 
}
bool WifiClient::enableEcho(){
    return echo(1);
}
bool WifiClient::disableEcho(){
    return echo(0);
}

bool WifiClient::begin(){
    return restart() && disableEcho() && stationMode() && disconnectAP();
}

/**** Wifi Layer ****/
bool WifiClient::apMode(){
    return wifiMode(2);
}
bool WifiClient::stationMode(){
    return wifiMode(1);
}
bool WifiClient::dualMode(){
    return wifiMode(3);
}
bool WifiClient::connectAP(const char* ssid, const char* passwd){
    String connectAPCommand = "AT+CWJAP=";
    connectAPCommand.concat("\""); // "
    connectAPCommand.concat(ssid); // ssid
    connectAPCommand.concat("\""); // "
    connectAPCommand.concat(","); // ,
    connectAPCommand.concat("\""); // "
    connectAPCommand.concat(passwd); // passwd
    connectAPCommand.concat("\""); // "
    connectAPCommand.concat("\r\n"); // \r\n
    return findOK(command(connectAPCommand, 4000));
}
String WifiClient::listAPS(){
    return command("AT+CWLAP\r\n", 4000);
}
bool WifiClient::disconnectAP(){
    return findOK(command("AT+CWQAP\r\n", 3000));
}
String WifiClient::listClientsConnected(){
    return command("AT+CWLIF\r\n", 4000);
}

/**** TCP Layer ****/
bool WifiClient::hasIP(){
    return tcpStatus(2);
}
bool WifiClient::connected(){
    return tcpStatus(3);
}
bool WifiClient::disconnected(){
    return tcpStatus(4);
}
bool WifiClient::tcpStart(const char* ip, const int port){
    String startCommand = "AT+CIPSTART=";
    startCommand.concat("\"TCP\""); // TCP 
    startCommand.concat("\""); // "
    startCommand.concat(ip); // ip 
    startCommand.concat("\""); // "
    startCommand.concat(","); // ,
    startCommand.concat(port); // port
    startCommand.concat("\r\n"); // \r\n
    return findOK(command(startCommand, 4000));
}
String WifiClient::sendData(String request){ // REVER PQ PRIMEIRO VEM >> DEPOIS SEND OK
    String sendCommand = "AT+CIPSEND=";
    int requestLength = request.length();
    if(requestLength > 2048)
        requestLength = 2048;
    sendCommand.concat(requestLength);
    startCommand.concat("\r\n"); // \r\n
    
    if(find(command(sendCommand, 4000), ">")){
        String response = find(command(request, 4000), "SEND OK");
        int ipdIndex = response.indexOf("+IPD");
        int httpIndex = response.indexOf("HTTP");
        if(ipdIndex != -1 && httpIndex != -1){
            response.replace(response.substring(ipdIndex, httpIndex),"");
        }        
        int okIndex = response.indexOf("OK");
        if(okIndex != -1){
            response.replace("OK\r\n", "");
        }
        return response;
    } else {
        return "";
    } 
}
bool WifiClient::tcpClose(){
    return findOK(command("AT+CIPCLOSE\r\n", 3000));
}
String WifiClient::getIP(){
    String response = command("AT+CIFSR\r\n",2000);
    int okIndex = response.indexOf("OK");
    if(okIndex != -1){
        response.replace("OK\r\n", "");
    }
    return response;
}
bool WifiClient::multipleConnections(){
    return tcpConnections(1);
}
bool WifiClient::singleConnection(){
    return tcpConnections(0);
}

/**** Private ****/
bool WifiClient::echo(int mode){
    String echoCommand = "ATE";
    echoCommand.concat(mode);
    echoCommand.concat("\r\n");
    return findOK(command(echoCommand, 2000));
}

bool WifiClient::wifiMode(int mode){
    String wifiModeCommand = "AT+CWMODE=";
    wifiModeCommand.concat(mode);
    wifiModeCommand.concat("\r\n");
    return findOK(command(wifiModeCommand, 2000));
}

bool WifiClient::tcpStatus(int status){
    String response = command("AT+CIPSTATUS\r\n", 2000);
    int okIndex = response.indexOf("OK");
    int statusIndex = response.indexOf("STATUS:");
    if(okIndex != -1) {
        response.replace("OK\r\n","");
    }
    if(statusIndex != -1){
        response.replace("STATUS:");
    }
    if (response.toInt() == status) {
        return true;
    } else {
        return false;
    }
}

bool WifiClient::tcpConnections(int mode){
    String connectionCommand = "AT+CIPMUX=";
    connectionCommand.concat(mode);
    connectionCommand.concat("\r\n");
    return findOK(command(connectionCommand, 2000));
}

bool WifiClient::find(String response, String word){
    if (response.indexOf(word) == -1) {
        return false;
    } else {
        return true;
    }
}

bool WifiClient::findOK(String response){
    return find(response, "OK");
}

bool WifiClient::transmissionMode(int mode){
    String transmissionCommand = "AT+CIPMODE=";
    transmissionCommand.concat(mode);
    transmissionCommand.concat("\r\n");
    return findOK(command(transmissionCommand, 1500));
}

String WifiClient::command(const char* command, const int timeout){
    String response = "";
    wifi->print(command); // send the read character to the esp8266
    long int time = millis();
    while( (time+timeout) > millis()) {
      while(wifi->available()) {
        // The esp has data so display its output to the serial window 
        char c = wifi->read(); // read the next character.
        response+=c;
      }  
    }
    return response;
}
