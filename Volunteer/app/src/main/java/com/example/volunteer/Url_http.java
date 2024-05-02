package com.example.volunteer;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class Url_http {

    public static String URL = "http://192.168.100.27/";


    public static String getURL() {
        return URL;
    }


}
