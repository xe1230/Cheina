package com.example.chelina.DataBase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class CDataBaseSystem
{
    public static final String DEF_DATABATE_NAME = "Chelina_DB";


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
            m_db.execSQL("CREATE TABLE IF NOT EXISTS Chelina_tbl(_id integer PRIMARY KEY AUTOINCREMENT, record_time time, reference_txt text, ImageIdx_n int, Titel_text text)");

            m_bInstance = true;
        }
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
            m_db.execSQL("CREATE TABLE IF NOT EXISTS "+ strName + "_tbl (_id integer PRIMARY KEY AUTOINCREMENT, record_time time, reference_txt text, ImageIdx_n int, Titel_text text)");
            bReturn = true;
        }

        return bReturn;
      }

    public Cursor Select(String strQuery)
    {
       return m_db.rawQuery(strQuery,null);
    }
}
