package com.example.chelina.DataBase;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class CConfigulation extends Application
{
    public String strCurrentDBTbl   = "Chelina_tbl";

    public void onCreate()
    {
        super.onCreate();
        LoadData();
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

            strCurrentDBTbl    = jsonArray.getString("tbl");

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

    public void SaveData()
    {
        String fileTitle  = "Configulation.json";
        JSONObject object = CreateJson();

        try
        {
            FileOutputStream FOSout = openFileOutput(fileTitle, Context.MODE_PRIVATE);

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

            jsonObject.put("tbl", strCurrentDBTbl);
//            jsonObject.put("nombre", getNombre());
//            jsonObject.put("precio", getPrecio());


        } catch (JSONException e)
        {
            e.printStackTrace();
        }

        return jsonObject;
    }
}
