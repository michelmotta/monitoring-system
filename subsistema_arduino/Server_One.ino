#include <DHT.h>
#include <ArduinoJson.h>
#include <ESP8266WiFi.h>
#include <ESP8266WebServer.h>
#include <IRremoteESP8266.h>
#include <IRsend.h>

#define DHTPIN D7
#define DHTTYPE DHT22

DHT dht(DHTPIN, DHTTYPE);
ESP8266WebServer server;
IRsend irsend(4);

String SERVER_ID = "1";
String SERVER_NAME = "Server One";

const char* ssid = "Your_Network_SSID";
const char* password = "Your_Network_Password";

float humidity;
float temperature;

String successResponse;

void setup() {
  Serial.begin(115200);
  dht.begin();
  irsend.begin();
  WiFi.begin(ssid, password);
  
  while(WiFi.status() != WL_CONNECTED){
    Serial.print(".");
    delay(500);
  }
  
  Serial.println("");
  Serial.print("IP Address: ");
  Serial.print(WiFi.localIP());
  Serial.println("");
  Serial.print("MAC Address: ");
  Serial.print(WiFi.macAddress());

  StaticJsonBuffer<100> jsonBufferSuccess;
  JsonObject& rootSuccess = jsonBufferSuccess.createObject();
  rootSuccess["status"] = "success";
  rootSuccess.printTo(successResponse);

  server.on("/", inicio);
  server.on("/api", api);
  server.on("/api/sensor", sensor);

  server.begin();
}

void loop() {
  server.handleClient();
}

void sensor(){
  StaticJsonBuffer<200> jsonBuffer;
  JsonObject& root = jsonBuffer.createObject();

  temperature = dht.readTemperature();
  humidity = dht.readHumidity();

  root["server_id"] = SERVER_ID;
  root["server_name"] = SERVER_NAME;
  root["temperature"] = temperature;
  root["humidity"] = humidity;

  String serverResponse;
  root.printTo(serverResponse);
  
  server.send(200, "application/json", serverResponse);
}

void inicio(){
  String serverResponse;
  serverResponse = "<!doctype html>";
  serverResponse +="<html lang='pt-br'>";
  serverResponse +=  "<head>";
  serverResponse +=    "<meta charset='utf-8'>";
  serverResponse +=    "<meta name='viewport' content='width=device-width, initial-scale=1, shrink-to-fit=no'>";
  serverResponse +=    "<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css' integrity='sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm' crossorigin='anonymous'>";
  serverResponse +=    "<link href='https://fonts.googleapis.com/css?family=Ubuntu:300,400,500,700' rel='stylesheet'>";
  serverResponse +=    "<script defer src='https://use.fontawesome.com/releases/v5.0.7/js/all.js'></script>";
  serverResponse +=    "<title>" + SERVER_NAME + "</title>";
  serverResponse +=  "</head>";
  serverResponse +=  "<body style='background: #191919; font-family: 'Ubuntu', sans-serif;'>";
  serverResponse +=    "<div class='container'>";
  serverResponse +=     "<h1 style='text-align: center;color: #fff; margin-top: 30px;'><i class='fas fa-server' style='font-size: 80px;'></i> <br>Welcome to " + SERVER_NAME + "</h1>";
  serverResponse +=     "<div class='row'>";
  serverResponse +=       "<div class='col-md-6'>";
  serverResponse +=         "<div style='background: #282828; margin-top: 20px; padding: 40px; border-top: 2px solid #76B900;'>";
  serverResponse +=           "<h2 style='text-align: center;color: #fff'>TEMPERATURE</h2>";
  serverResponse +=           "<h3 style='text-align: center;color: #fff; font-size: 50px; margin-top: 20px;'><i class='fas fa-thermometer-half' style='color: #76B900;'></i> <span id='temperature'>0</span>ºC</h3>";
  serverResponse +=         "</div>";
  serverResponse +=       "</div>";
  serverResponse +=       "<div class='col-md-6'>";
  serverResponse +=         "<div style='background: #282828; margin-top: 20px; padding: 40px; border-top: 2px solid #76B900;'>";
  serverResponse +=           "<h2 style='text-align: center;color: #fff'>UMIDITY</h2>";
  serverResponse +=           "<h3 style='text-align: center;color: #fff; font-size: 50px; margin-top: 20px;'><i class='fas fa-umbrella' style='color: #76B900;'></i> <span id='umidity'>0</span>%</h3>";
  serverResponse +=         "</div>";
  serverResponse +=       "</div>";
  serverResponse +=     "</div>";
  serverResponse +=     "<div class='row'>";
  serverResponse +=       "<div class='col-md-6'>";
  serverResponse +=         "<div style='background: #282828; margin-top: 20px; padding: 40px; border-top: 2px solid #76B900;'>";
  serverResponse +=           "<h2 style='text-align: center;color: #fff'>TECHNICAL INFORMATION</h2>";
  serverResponse +=           "<p style='color: #fff; margin-bottom: 0px;'>Server Id: <bold>" + SERVER_ID + "</bold></p>";
  serverResponse +=           "<p style='color: #fff; margin-bottom: 0px;'>Server Name: <bold>" + SERVER_NAME + "</bold></p>";
  serverResponse +=           "<p style='color: #fff; margin-bottom: 0px;'>IP Connected To: <bold>" + WiFi.localIP().toString() + "</bold></p>";
  serverResponse +=         "</div>";
  serverResponse +=       "</div>";
  serverResponse +=       "<div class='col-md-6'>";
  serverResponse +=         "<div style='background: #282828; margin-top: 20px; padding: 40px; border-top: 2px solid #76B900;'>";
  serverResponse +=           "<h2 style='text-align: center;color: #fff'>API</h2>";
  serverResponse +=           "<center><a href='http://" + WiFi.localIP().toString() + "/api' style='margin-top:34px;' class='btn btn-success'>Access API</a></center>";
  serverResponse +=         "</div>";
  serverResponse +=       "</div>";
  serverResponse +=     "</div>";
  serverResponse +=    "</div>";
  serverResponse +=    "<script src='https://code.jquery.com/jquery-3.3.1.min.js' integrity='sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8=' crossorigin='anonymous'></script>";
  serverResponse +=    "<script src='https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js' integrity='sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q' crossorigin='anonymous'></script>";
  serverResponse +=    "<script src='https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js' integrity='sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl' crossorigin='anonymous'></script>";
  serverResponse +="<script type='text/javascript'>";
  serverResponse +=  "$.ajax({ method: 'GET', url:'http://" + WiFi.localIP().toString() + "/api/sensor'})";
  serverResponse +=    ".done(function(msg) {";
  serverResponse +=        "$('#temperature').replaceWith('<span id=\"temperature\">' + msg.temperature + '</span>');";
  serverResponse +=        "$('#umidity').replaceWith('<span id=\"umidity\">' + msg.humidity + '</span>');";
  serverResponse +=    "});";
  serverResponse +="</script>";
  serverResponse +=  "</body>";
  serverResponse +="</html>";
  server.send(200, "text/html", serverResponse);
}

void api(){
  String serverResponse;
  serverResponse ="<!doctype html>";
  serverResponse +="<html lang='pt-br'>";
  serverResponse +=  "<head>";
  serverResponse +=    "<meta charset='utf-8'>";
  serverResponse +=    "<meta name='viewport' content='width=device-width, initial-scale=1, shrink-to-fit=no'>";
  serverResponse +=    "<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css' integrity='sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm' crossorigin='anonymous'>";
  serverResponse +=    "<link href='https://fonts.googleapis.com/css?family=Ubuntu:300,400,500,700' rel='stylesheet'> ";
  serverResponse +=    "<script defer src='https://use.fontawesome.com/releases/v5.0.7/js/all.js'></script>";
  serverResponse +=    "<title>" + SERVER_NAME + " - API</title>";
  serverResponse +=  "</head>";
  serverResponse +=  "<body style='background: #191919; font-family: 'Ubuntu', sans-serif;'>";
  serverResponse +=    "<div class='container'>";
  serverResponse +=      "<h1 style='text-align: center;color: #fff; margin-top: 30px;'><i class='fas fa-server' style='font-size: 80px;'></i> <br>Welcome to Server One - API</h1>";
  serverResponse +=     "<div class='row'>";
  serverResponse +=       "<div class='col-md-6'>";
  serverResponse +=       "<div style='background: #282828; margin-top: 50px; padding: 40px; border-top: 2px solid #76B900;'>";
  serverResponse +=         "<h4 style='color: #fff;'>Sensor API data</h4>";
  serverResponse +=         "<p style='color: #fff'>Url for requests: <bold>http://" + WiFi.localIP().toString() + "/api/sensor</bold></p>";
  serverResponse +=         "<p style='color: #fff'>Returned Data:</p>";
  serverResponse +=         "<ul style='color: #fff'>";
  serverResponse +=           "<li>server_id: Server id from the board.</li>";
  serverResponse +=           "<li>temperature: Temperature read by DHT22 (ºC).</li>";
  serverResponse +=           "<li>humidity: Humidity read by DHT22 (%).</li>";
  serverResponse +=         "</ul>";
  serverResponse +=       "</div>";
  serverResponse +=       "</div>";
  serverResponse +=       "<div class='col-md-6'>";
  serverResponse +=       "<div style='background: #282828; margin-top: 50px; padding: 40px; border-top: 2px solid #76B900;'>";
  serverResponse +=         "<h4 style='color: #fff;'>Infrared signal emission</h4>";
  serverResponse +=         "<p style='color: #fff'>Url for requests: <bold>http://" + WiFi.localIP().toString() + "/api/on-off</bold></p>";
  serverResponse +=         "<p style='color: #fff'>Returned Data:</p>";
  serverResponse +=         "<ul style='color: #fff'>";
  serverResponse +=           "<li>status: Status of requisition</li>";
  serverResponse +=         "</ul>";
  serverResponse +=       "</div>";
  serverResponse +=       "</div>";
  serverResponse +=     "</div>";
  serverResponse +=    "</div>";
  serverResponse +=    "<script src='https://code.jquery.com/jquery-3.3.1.min.js' integrity='sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8=' crossorigin='anonymous'></script>";
  serverResponse +=    "<script src='https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js' integrity='sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q' crossorigin='anonymous'></script>";
  serverResponse +=    "<script src='https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js' integrity='sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl' crossorigin='anonymous'></script>";
  serverResponse +=  "</body>";
  serverResponse +="</html>";
  server.send(200, "text/html", serverResponse);
}
