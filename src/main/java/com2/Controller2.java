package com2;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;


public class Controller2 implements Initializable {
    @FXML
    ResourceBundle resources;
    @FXML
    TextField enterText;
    @FXML
    Label cityAndTime;
    @FXML
    Label dayInfo;
    @FXML
    Label nightDeg;
    @FXML
    ImageView nightImage;
    @FXML
    ImageView morningImage;
    @FXML
    ImageView dayImage;
    @FXML
    ImageView eveningImage;
    @FXML
    Label morningDeg;
    @FXML
    Label dayDeg;
    @FXML
    Label eveningDeg;
    @FXML
    Text temperature;
    @FXML
    Text pressure;
    @FXML
    Text humidity;
    @FXML
    Text wind;
    @FXML
    Text clouds;
    @FXML
    Text sunrise;
    @FXML
    Text sunset;
    @FXML
    Text night;
    @FXML
    Text morning;
    @FXML
    Text day;
    @FXML
    Text evening;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        enterText.addEventHandler(KeyEvent.KEY_PRESSED,
                new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        enterText.setOnKeyPressed(e->{
                            if(e.getCode() == KeyCode.ENTER) {
                                String getUserCity = enterText.getText().trim();
                                if (!getUserCity.equals("")) {
                                    String os1 = geturl("https://api.openweathermap.org/data/2.5/weather?q="
                                            + getUserCity + "&appid=e8c9e4db1c2e983ee496587d31f658e5");
                                    String osDate = geturl("");
                                    JSONObject jsonObject = new JSONObject(os1);
                                    System.out.println(os1);

                                    double tempCels;
                                    tempCels = jsonObject.getJSONObject("main").getDouble("temp");
                                    tempCels = tempCels - 273.f;
                                    double press2;
                                    press2 = jsonObject.getJSONObject("main").getDouble("pressure");
                                    press2 = press2 * 0.75006375541921;

                                    //ссылка на форум где есть перевод из unix https://stackoverflow.com/questions/17432735/convert-unix-timestamp-to-date-in-java
                                    long sunriseHour;
                                    sunriseHour = jsonObject.getJSONObject("sys").getLong("sunrise");
                                    // convert seconds to milliseconds
                                    Date date1 = new java.util.Date(sunriseHour*1000L);
                                    // the format of your date
                                    SimpleDateFormat sdf1 = new java.text.SimpleDateFormat("HH:mm:ss");
                                    // give a timezone reference for formatting (see comment at the bottom)
//                                    sdf1.setTimeZone(java.util.TimeZone.getTimeZone("GMT-3"));
                                    String formattedDateSUNRISE = sdf1.format(date1);

                                    long sunsetHour;
                                    sunsetHour = jsonObject.getJSONObject("sys").getLong("sunset");
                                    // convert seconds to milliseconds
                                    Date date2 = new java.util.Date(sunsetHour*1000L);
                                    // the format of your date
                                    SimpleDateFormat sdf2 = new java.text.SimpleDateFormat("HH:mm:ss");
                                    // give a timezone reference for formatting (see comment at the bottom)
//                                    sdf2.setTimeZone(java.util.TimeZone.getTimeZone("GMT-3"));
                                    String formattedDateSUNSET = sdf2.format(date2);

                                    long currTime;
                                    currTime = jsonObject.getLong("dt");
                                    // convert seconds to milliseconds
                                    Date date3 = new java.util.Date(currTime*1000L);
                                    // the format of your date
                                    SimpleDateFormat sdf3 = new java.text.SimpleDateFormat("HH:mm:ss");
                                    // give a timezone reference for formatting (see comment at the bottom)
//                                    sdf2.setTimeZone(java.util.TimeZone.getTimeZone("GMT-3"));
                                    String formattedDate = sdf3.format(date3);


                                    DecimalFormat format = new DecimalFormat("##.00");
                                    temperature.setText("Temperature " + format.format(tempCels));
                                    pressure.setText("Pressure " + format.format(press2));
                                    humidity.setText("Humidity " + jsonObject.getJSONObject("main").getDouble("humidity") + " %");
                                    wind.setText("Wind " + jsonObject.getJSONObject("wind").getDouble("speed") + " m/s");
                                    clouds.setText("Clouds " + jsonObject.getJSONObject("clouds").getDouble("all") + " %");
                                    sunrise.setText("Sunrise "+ formattedDateSUNRISE);
                                    sunset.setText("Sunset "+ formattedDateSUNSET);
                                    cityAndTime.setText(getUserCity+","+ formattedDate);
                                    night.setText("night");
                                    morning.setText("morning");
                                    day.setText("day");
//                                    evening.setText("evening");
                                    evening.setText(jsonObject.getJSONObject("weather").getString("main"));



// TODO: 25.10.2021 если я могу сделать хоть фотку для текущей погоды

//                                    if(jsonObject.getJSONObject("weather").getString("main").equals("Rain")) {
//                                        Image rain = new Image("com2/rain.jpg");
//                                        dayImage.setImage(rain);
//                                    }else if(jsonObject.getJSONObject("weather").getString("main").equals("Clouds")){
//                                        Image clouds = new Image("com2/clouds.jpg");
//                                        dayImage.setImage(clouds);
//                                    }else  if(jsonObject.getJSONObject("weather").getString("main").equals("Clear")){
//                                        Image sun = new Image("com2/sun.jpg");
//                                        dayImage.setImage(sun);
//                                    }else {
//                                        Image def = new Image("com2/sunClouds.jpg");
//                                        System.out.println("def");
//                                        dayImage.setImage(def);
//                                    }

                                    

//                                    {"coord":{"lon":37.6156,"lat":55.7522},"weather":[{"id":804,"main":"Clouds","description":"overcast clouds","icon":"04d"}],
//                                    "base":"stations","main":{"temp":274.18,"feels_like":270.46,"temp_min":273.3,"temp_max":275.03,"pressure":1029,"humidity":50,"sea_level":1029,"grnd_level":1010},
//                                    "visibility":10000,"wind":{"speed":3.53,"deg":254,"gust":5.07},"clouds":{"all":100},"dt":1635154211,"sys":{"type":2,"id":2000314,"country":"RU","sunrise":1635135645,"sunset":1635170772},
//                                    "timezone":10800,"id":524901,"name":"Moscow","cod":200}



        // TODO: 23.10.2021 также вот ссылка для просмотра json в онлайне (тип с интерфейсом)
        //https://jsonformatter.curiousconcept.com/#


                // TODO: 25.10.2021 там написано про чтение json  но тип не факт что оно правильное
//                https://howtodoinjava.com/java/library/json-simple-read-write-json-examples/

                                }
                            }
                        });
                    }
                });
    }
    private static String geturl(String urlAdress) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            URL url = new URL(urlAdress);
            URLConnection urlConnection = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine())!=null){
                stringBuilder.append(line + "\n");
            }
            bufferedReader.close();
        }catch (Exception e){
            System.out.println("/");
        }
        return stringBuilder.toString();
    }



}
