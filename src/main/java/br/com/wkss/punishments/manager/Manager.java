package br.com.wkss.punishments.manager;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import network.zentry.manager.connections.Mysql;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

public class Manager {

    private static Mysql mysql;
    public static Mysql getMysql() {
        return mysql;
    }

    private static SimpleDateFormat dateFormat;
    public static SimpleDateFormat getDateFormat() {
        return dateFormat;
    }

    public static BaseComponent buildComponent(String text, HoverEvent hover, ClickEvent click) {
        BaseComponent component = new TextComponent(text);
        component.setHoverEvent(hover);
        component.setClickEvent(click);
        return component;
    }

    public static long stringToLong(String paramOfString) {
        String[] arrayOfString;
        Integer templateInteger;
        Integer integer;
        String[] timeString = paramOfString.split(",");
        Integer day = 0, hour = 0, minute = 0, second = 0;

        templateInteger = (arrayOfString = timeString).length;
        for(integer = 0; integer < templateInteger; integer++) {
            String string = arrayOfString[integer];
            if(string.contains("d") || string.contains("D")) {
                day = Integer.valueOf(string.replace("d", "").replace("D", "")).intValue();
            }
            if(string.contains("h") || string.contains("H")) {
                hour = Integer.valueOf(string.replace("h", "").replace("H", "")).intValue();
            }
            if(string.contains("m") || string.contains("M")) {
                minute = Integer.valueOf(string.replace("m", "").replace("M", "")).intValue();
            }
            if(string.contains("s") || string.contains("S")) {
                second = Integer.valueOf(string.replace("s", "").replace("S", "")).intValue();
            }
        }
        return convert(day, hour, minute, second);
    }

    public static String formatLong(long paramOfLong) {
        String message = "";
        long now = System.currentTimeMillis();
        long diff = paramOfLong - now;
        int seconds = (int) (diff / 1000L);
        if (seconds >= 86400) {
            int days = seconds / 86400;
            seconds %= 86400;
            if (days == 1) {
                message = String.valueOf(message) + days + " dia ";
            } else {
                message = String.valueOf(message) + days + " dias ";
            }
        }
        if (seconds >= 3600) {
            int hours = seconds / 3600;
            seconds %= 3600;
            if (hours == 1) {
                message = String.valueOf(message) + hours + " hora ";
            } else {
                message = String.valueOf(message) + hours + " horas ";
            }
        }
        if (seconds >= 60) {
            int min = seconds / 60;
            seconds %= 60;
            if (min == 1) {
                message = String.valueOf(message) + min + " minuto ";
            } else {
                message = String.valueOf(message) + min + " minutos ";
            }
        }
        if (seconds >= 0) {
            if (seconds == 1) {
                message = String.valueOf(message) + seconds + " segundo";
            } else {
                message = String.valueOf(message) + seconds + " segundos";
            }
        }
        if(seconds < 0) {
            message = "Expirado";
        }
        return message;
    }

    public static long convert(long days, long hours, long minutes, long seconds) {
        long x = 0L;
        long minute = minutes * 60L;
        long hour = hours * 3600L;
        long day = days * 86400L;
        x = minute + hour + day + seconds;
        long time = System.currentTimeMillis() + x * 1000L;
        return time;
    }



    static {
        mysql = new Mysql();
        mysql.connect();
        mysql.setup();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
    }

    public static String formatArray(Object[] array, String splitter) {
        String string = Arrays.toString(array);
        return string.replace("[", "").replace("]", "").replace(", ", splitter);
    }