package com.example.money_management.activities;

import android.util.Log;

public class String2Currency {
    public String2Currency(){

    }
    public String convertString2Currency(String input){
        String[] ip = input.split("\\.");
        String result = "";
        int cnt = 3;
        int p = 1;
        if(String.valueOf(ip[0].charAt(0)).equals("-"))
            p++;
        for(int i = p; i < ip[0].length() - 1; i++){
            Log.i("Index", String.valueOf(i));
            if(cnt == 3) {
                result = result + ",";
                cnt = 0;
            }
            result = result + ip[0].charAt(i);
            cnt++;
        }

        String prefix = "";
        if(String.valueOf(ip[0].charAt(0)).equals("-"))
            prefix = "-";
        return prefix + ip[0].charAt(p-1) + result;
    }
}
