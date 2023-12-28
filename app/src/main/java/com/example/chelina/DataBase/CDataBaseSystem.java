package com.example.chelina.DataBase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.chelina.Recipe.ListView_Item;


public class CDataBaseSystem
{
    public static final String  DEF_DATABATE_NAME = "Chelina_DB";
    private static final String DEF_STR_CreatedQuery = "(_id integer PRIMARY KEY AUTOINCREMENT, record_time time, reference_txt text, ImageIdx_n int, Titel_text text, reg_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";

    private static CDataBaseSystem myScissor = null;

    public static CDataBaseSystem Instance()
    {
        if(myScissor == null)
        {
            myScissor = new CDataBaseSystem();
        }

        return myScissor;
    }

    private String          m_strTableName = "Chelina_tbl";
    private SQLiteDatabase  m_db            = null;
    private CDBHelper       m_clsDBHelper   = null;
    private boolean         m_bInstance     = false;

    private CDataBaseSystem()
    {

    }

    public void Initialize(Context context)
    {
        if (!m_bInstance)
        {
            m_clsDBHelper = new CDBHelper(context);

            m_db = m_clsDBHelper.getWritableDatabase();
            m_db.execSQL("CREATE TABLE IF NOT EXISTS Chelina_tbl" + DEF_STR_CreatedQuery);

            m_bInstance = true;
        }
    }

    public String GetTableName()
    {
        return m_strTableName;
    }

    public void UseTable(String strTableName)
    {
        m_strTableName = strTableName + "_tbl";
    }

    public boolean CrateTable(String strName)
    {
        boolean bReturn = false;


        if (m_bInstance)
        {
            m_db.execSQL("CREATE TABLE IF NOT EXISTS "+ strName + "_tbl" + DEF_STR_CreatedQuery );
            bReturn = true;
        }

        return bReturn;
    }

    public Cursor Select(String strQuery)
    {
       return  m_db.rawQuery(strQuery,null);
    }


    public boolean ExcuteQuery(String strQuery)
    {
        boolean bReturn = false;

        try
        {
            m_db.execSQL(strQuery);
            bReturn = true;
        }
        catch (RuntimeException ex)
        {

        }

       return bReturn;
    }

}

// TODO why SQLiteDatabase needs the Context class