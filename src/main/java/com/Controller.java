package com;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import org.json.JSONArray;
import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    ResourceBundle resources;

    @FXML
    URL location;

    @FXML
    AnchorPane mainTextField;

    @FXML
    TextField enterCity;

    @FXML
    Button buttShow;

    @FXML
    Text temperature;

    @FXML
    Text pressure;

    @FXML
    Text humidity;

    @FXML
    Text wind;

    @FXML
    Text precipitation;

    @FXML
    Text clouds;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buttShow.addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>(){
                    @Override
                    public void handle(MouseEvent event) {
                        buttShow.setOnAction(e->{
                            String getUserCity = enterCity.getText().trim();
                            if(!getUserCity.equals("")) {
                                String os = geturl("https://api.openweathermap.org/data/2.5/weather?q=" + getUserCity + "&appid=e8c9e4db1c2e983ee496587d31f658e5");
                                JSONObject obj = new JSONObject(os);
                                double temp;
                                temp = obj.getJSONObject("main").getDouble("temp");
                                temp = temp-273.f;
                                double press;
                                press = obj.getJSONObject("main").getDouble("pressure");
                                press = press * 0.75006375541921;
                                DecimalFormat format = new DecimalFormat("##.00");
//                                temperature.setText("Temperature: " + obj.getJSONObject("main").getDouble("temp"));
                                temperature.setText("Temperature" + format.format(temp));
                                pressure.setText("Pressure: " + format.format(press)+" mmHg");
//                                pressure.setText("Pressure: " + obj.getJSONObject("main").getDouble("pressure"));
                                humidity.setText("Humidity: " + obj.getJSONObject("main").getDouble("humidity")+" %");
                                wind.setText("Wind: " +  obj.getJSONObject("wind").getDouble("speed") + " м/с");
                                clouds.setText("Clouds: " + obj.getJSONObject("clouds").getDouble("all")+" %");

                                System.out.println(os);
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
