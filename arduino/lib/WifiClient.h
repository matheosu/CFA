//
//  WifiClient.h
//  
//
//  Created by Matheus Castro on 2/20/16.
//
//

#ifndef WifiClient_h
#define WifiClient_h

#include <Arduino.h>
#include <SoftwareSerial.h>

class WifiClient {

    public:
        WifiClient(SoftwareSerial &_esp8266);

        /****  Basic  ****/
    
        /* Test if AT system works correctly */
        bool test();
        /* Reset the module */
        bool restart();
        /* Print firmware version */
        String version();
        /* Enable echo (Sends back received command before response) */
        bool enableEcho();
        /* Disable echo (Doesn’t send back received command) */
        bool disableEcho();
        /* Restart and Disable Echo */
        void begin();

        /**** Wifi Layer ****/
        
        /* AP mode (host) (2) */
        bool apMode();
        /* Station mode (client) (1) */
        bool stationMode();
        /* AP + Station mode (3) */
        bool dualMode();

        /* Connect a SSID with supplied password */
        bool connectAP(const char* ssid, const char* passwd);
        /* Lists available Access Points */
        String listAPs();
        /* Disconnect ESP8266 from the AP is currently connected to */
        bool disconnectAP();
        /* List information on of connected clients */
        String listClientsConnected();
        
        /**** TCP Layer ****/

        /* TCPStatus is GotIP (2) */
        bool hasIP();
        /* TCPStatus is Connected (3) */
        bool connected();
        /* TCPStatus is Disconnected (4) */
        bool disconnected();
        /* Start a connection as client. (Single connection mode) */
        bool tcpStart(const char* ip, const int port);
        /* Send a data to client tcp start */
        String sendData(String request);
        /* Close TCP or UDP connection.For single connection mode */
        bool tcpClose();
        /* Get local IP address */
        String getIP();
        /* Enable multiplex mode (up to 4 conenctions) */
        bool multipleConnections();
        /* Enable single mode */
        bool singleConnection();
    
        /* Transmission Mode*/
        bool transmissionMode(int mode);

    private:
        SoftwareSerial* wifi;
        
        bool echo(int mode);

        bool wifiMode(int mode);

        bool tcpStatus(int status);
        
        bool tcpConnections(int mode);

        bool find(String response, String word);

        bool findOK(String response);

        String command(String command, int timeout);

};

#endif /* WifiClient.h */
