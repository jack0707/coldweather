package com.longma.jack.coldweather.util;

import android.text.TextUtils;

import com.longma.jack.coldweather.db.ColdWeatherDB;
import com.longma.jack.coldweather.model.City;
import com.longma.jack.coldweather.model.County;
import com.longma.jack.coldweather.model.Province;

/**
 * Created by jack on 2015/6/18.
 */
public class Utility
{
    public synchronized static boolean handleProvincesResponse(ColdWeatherDB coldWeatherDB,String response)
    {
        if(!TextUtils.isEmpty(response))
        {
            String[] allProvinces=response.split(",");
            if(allProvinces!=null&&allProvinces.length>0)
            {
                for(String p:allProvinces)
                {
                    String[] array=p.split("\\|");
                    Province province=new Province();
                    province.setProvinceCode(array[0]);
                    province.setProvinceName(array[1]);
                    coldWeatherDB.saveProvince(province);
                }
                return true;
            }
        }
        return false;
    }
    /**
     * 解析和处理服务器返回的市级数据
     */
    public static boolean handleCitiesResponse(ColdWeatherDB coldWeatherDB,String response,int provinceId)
    {
        if(!TextUtils.isEmpty(response))
        {
            String[] allCities=response.split(",");
            if(allCities!=null&&allCities.length>0)
            {
                for(String c:allCities)
                {
                    String[] array=c.split("\\|");
                    City city=new City();
                    city.setCityCode(array[0]);
                    city.setCityName(array[1]);
                    city.setProvinceId(provinceId);
                    coldWeatherDB.saveCity(city);
                    System.out.println("pull cities");
                }
                return true;
            }
        }
        return false;
    }
    /**
     * 解析和处理服务器返回的数据
     */
    public static boolean handleCountiesResponse(ColdWeatherDB coldWeatherDB,String response,int cityId)
    {
        if(!TextUtils.isEmpty(response))
        {
            String[] allCounties=response.split(",");
            if(allCounties!=null&&allCounties.length>0)
            {
                for(String c:allCounties)
                {
                    String[] array=c.split("\\|");
                    County county=new County();
                    county.setCountyCode(array[0]);
                    county.setCountyName(array[1]);
                    county.setCityId(cityId);
                    coldWeatherDB.saveCounty(county);
                }
                return true;
            }
        }
        return false;
    }
}
