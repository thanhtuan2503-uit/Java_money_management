package com.example.money_management.activities;

import android.util.Log;

import java.text.NumberFormat;

public class String2Currency {
    public String2Currency(){

    }
    public String convertString2Currency(String input){
        String[] ip = input.split("\\.");
        if(ip[0].length()<4)
            return input;
        String result = "";
        int cnt = 0;
        boolean neg =  false;
        if(String.valueOf(ip[0].charAt(0)).equals("-"))
            neg = true;
        if(neg)
            ip[0] = ip[0].substring(1, ip[0].length());
        for(int i = ip[0].length() - 1; i > 0 ; i--){
            cnt++;
            result = ip[0].charAt(i) + result;
            if(cnt == 3 ) {
                result = "," + result;
                cnt = 0;
            }
        }

        result =  ip[0].charAt(0) + result;

        if(neg)
            result = "-" + result;
        return result + "." + ip[1] + "đ";
    }

}
