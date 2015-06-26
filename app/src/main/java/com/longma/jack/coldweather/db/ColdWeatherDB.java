package com.longma.jack.coldweather.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.longma.jack.coldweather.model.City;
import com.longma.jack.coldweather.model.County;
import com.longma.jack.coldweather.model.Province;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jack on 2015/6/17.
 */
public class ColdWeatherDB
{
    public static final String DB_NAME="cool_weather";
    public static final int VERSION=1;
    private static ColdWeatherDB coldWeatherDB;
    private SQLiteDatabase db;
    /**
     *将构造方法私有化
     */
    private ColdWeatherDB(Context context)
    {
        ColdWeatherOpendHelper dbHelper=new ColdWeatherOpendHelper(context,DB_NAME,null,VERSION);
        db=dbHelper.getWritableDatabase();
    }
    /**
     *获取ColdWeatherDB的实例
     */
    public synchronized static ColdWeatherDB getInstance(Context context)
    {
        if(coldWeatherDB==null)
        {
            coldWeatherDB=new ColdWeatherDB(context);
        }
        return coldWeatherDB;
    }
    /**
     *将Province实例存储到数据库
     */
    public void saveProvince(Province province)
    {
        if(province!=null)
        {
            ContentValues values=new ContentValues();
            values.put("province_name",province.getProvinceName());
            values.put("province_code",province.getProvinceCode());
            db.insert("Province",null,values);
        }
    }
    /**
     *从数据库读取全国所有的省份信息
     */
    public List<Province> loadProvinces()
    {
        List<Province> list=new ArrayList<>();
        Cursor cursor=db.query("Province",null,null,null,null,null,null);
        if(cursor.moveToFirst())
        {
            do
            {
               Province province=new Province();
                province.setId(cursor.getInt(cursor.getColumnIndex("id")));
                province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
                province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
                list.add(province);
            }
            while (cursor.moveToNext());
        }
        return list;
    }

    public void saveCity(City city)
    {
            if(city!=null)
            {
                ContentValues values=new ContentValues();
                values.put("city_name",city.getCityName());
                values.put("city_code",city.getCityCode());
                values.put("province_id",city.getProvinceId());
                db.insert("City",null,values);
            }
    }

    public List<City> loadCities(int provinceId)
    {
        List<City> list=new ArrayList<City>();
        System.out.println("hello");
        Cursor cursor = db.query("City", null, "province_id = ?",
                new String[] { String.valueOf(provinceId) }, null, null, null);
        System.out.println("1");
        if(cursor.moveToFirst())
        {
            do
            {
                City city=new City();
                System.out.println("faile");
                city.setId(cursor.getInt(cursor.getColumnIndex("id")));
                city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
                city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
                city.setProvinceId(provinceId);
                list.add(city);
                System.out.println("success");
            }
            while (cursor.moveToNext());
        }
        return list;
    }

    public void saveCounty(County county)
    {
        if(county!=null)
        {
            ContentValues values=new ContentValues();
            values.put("county_name",county.getCountyName());
            values.put("county_code",county.getCountyCode());
            values.put("city_id",county.getCityId());
            db.insert("County",null,values);
        }
    }

    public List<County> loadCounties(int cityId)
    {
        List<County> list=new ArrayList<County>();
        Cursor cursor=db.query("County",null,"city_id = ?",new String[]{String.valueOf(cityId)},null,null,null);
        if(cursor.moveToFirst())
        {
            do
            {
               County county=new County();
               county.setId(cursor.getInt(cursor.getColumnIndex("id")));
               county.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
               county.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
               county.setCityId(cityId);
               list.add(county);
            }
            while (cursor.moveToNext());
        }
        return list;
    }
}
