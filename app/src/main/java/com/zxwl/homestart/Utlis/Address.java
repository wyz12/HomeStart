package com.zxwl.homestart.Utlis;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

/**
 * Created by sks on 2018/4/19.
 */

public class Address {
    private LocationManager locationManager;
    private String locationProvider;

    public String hqaddress(Context context){
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        //获取所有可用的位置提供器
        List<String> providers = locationManager.getProviders(true);
        if(providers.contains(LocationManager.GPS_PROVIDER)){
            //如果是GPS
            locationProvider = LocationManager.GPS_PROVIDER;
        }else if(providers.contains(LocationManager.NETWORK_PROVIDER)){
            //如果是Network
            locationProvider = LocationManager.NETWORK_PROVIDER;
        }else{
            Toast.makeText(context, "没有可用的位置提供器", Toast.LENGTH_SHORT).show();
        }
        @SuppressLint("MissingPermission") Location location = locationManager.getLastKnownLocation(locationProvider);
        return location.getLatitude()+","+location.getLongitude();
    }


}
