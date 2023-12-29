package com.example.chelina.DataBase;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class CChelinaApp extends Application
{
    private String strLastDBTbl = "Chelina_tbl";

    public void onCreate()
    {
        super.onCreate();
        LoadData();

        CDataBaseSystem.Instance().UseTable(strLastDBTbl);
    }

    public String GetLastTbl()
    {
        return  strLastDBTbl;
    }

    public void LoadData()
    {
        try
        {
            FileInputStream clsFileInputStream  = openFileInput("Configulation.json");
            JSONObject      jsonArray           = null;
            String          strJsonData         = "";
            int             nChar               = 0;

            while((nChar = clsFileInputStream.read()) != -1)
            {
                strJsonData +=  (char)nChar;
            }

            jsonArray       = new JSONObject(strJsonData);

            strLastDBTbl = jsonArray.getString("tbl");

            clsFileInputStream.close();
        }
        catch (IOException e)
        {
            Log.i("Dean IO exception)", e.getMessage());
            e.printStackTrace();
        }
        catch (JSONException e)
        {
            Log.i("Dean IO exception)", e.getMessage());
            e.printStackTrace();
        }
    }

    public void SaveData(String strLastTbl)
    {
        String      strFileTitle    = "Configulation.json";
        JSONObject  object          = null;

        strLastDBTbl = strLastTbl;

        try
        {
            object = CreateJson();
            FileOutputStream FOSout = openFileOutput(strFileTitle, Context.MODE_PRIVATE);

            FOSout.write(object.toString().getBytes());
            FOSout.close();
        }
        catch (IOException e)
        {
            Log.i("Dean IO exception)",e.getMessage());
            e.printStackTrace();
        }
    }

    private JSONObject CreateJson()
    {
        JSONObject jsonObject = new JSONObject();


        try
        {
            jsonObject.put("tbl", strLastDBTbl);

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        return jsonObject;
    }
}
