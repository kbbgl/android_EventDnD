package com.kobbigal.downloadingwebcontent;

import java.util.List;

/**
 * Created by kobbigal on 6/16/17.
 */

public class WeatherClass {
    public Coord coord;
    public List<Weather> weather;
    public String base;
    public Main main;
    public int visibility;
    public Wind wind;
    public Clouds clouds;
    public int dt;
    public Sys sys;
    public int id;
    public String name;
    public int cod;

    public class Sys
    {
        public int type;
        public int id;
        public double message;
        public String country;
        public int sunrise;
        public int sunset;
    }
    public class Clouds
    {
        public int all;
    }
    public class Coord
    {
        public double lon;
        public double lat;
    }

    public class Weather
    {
        public int id;
        public String main;
        public String description;
        public String icon;
    }

    public class Main
    {
        public double temp;
        public double temp_min;
        public double temp_max;
        public int pressure;
        public int humidity;
    }

    public class Wind
    {
        public double speed;
        public int deg;
    }
}
