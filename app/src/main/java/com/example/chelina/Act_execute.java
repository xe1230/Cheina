package com.example.chelina;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.chelina.DataBase.CDataBaseSystem;
import com.example.chelina.Recipe.CRecordData;
import com.example.chelina.Recipe.ListView_Adapter;
import com.example.chelina.Recipe.ListView_Item;

import java.util.ArrayList;


public class Act_execute extends AppCompatActivity
{


    /* ----------------------------------------------------------------------------- */
    // Definitions
    /* ----------------------------------------------------------------------------- */
//    public static final String KEY_SIMPLE_DATA = "data";
//    private static final String DEF_DB_NAEM = "ChelinaDB";
//    private static final String DEF_DB_TABLE_NAME = "Interval_tbl";

    /* ----------------------------------------------------------------------------- */
    // Enum
    /* ----------------------------------------------------------------------------- */
    enum _eDifficult
    {
        difficult(0),
        nomal(1),
        easy(2);

     //   final private String day;
        private final int value;

         _eDifficult(int value)
        {
            this.value = value;
        }


//        public int getValue() { return value; }

        public static String[] getNames()
        {
            _eDifficult[] states = values();
            String[] names = new String[states.length];

            for (int i = 0; i < states.length; i++) {
                names[i] = states[i].name();
            }

            return names;
        }
    }

    /* ----------------------------------------------------------------------------- */
    // Variable
    /* ----------------------------------------------------------------------------- */
    private Thread                      m_Thrtime           = null;
    private Button                      m_BtnStart          = null;
    private TextView                    m_lbTableName       = null;
    private TextView                    mTimeTextView       = null;
    private EditText                    m_editTitle         = null;
    private ListView                    m_listView          = null;
    private InputMethodManager          m_MngInput          = null;

    private boolean                     m_bRunning          = true;
    private boolean                     m_bStarted          = false;

    private boolean                     m_bMemoClicked      = false;
    private CRecordData                 clsRecordData       = null;
    private int                         m_nHapValue         = 0;

    private ListView_Adapter            m_Adapter           = null;
    private ArrayList<ListView_Item>    m_ListViewitems     = null;
    private int                         m_nDifficultIdx     = 0;
    private int                         m_InitMemberId      = 0;
    private int                         m_nMemberID         = 0;
    /* ----------------------------------------------------------------------------- */
    // Properties Function
    /* ----------------------------------------------------------------------------- */


    /* ----------------------------------------------------------------------------- */
    // InitInstance
    /* ----------------------------------------------------------------------------- */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frm_execute);
        getSupportActionBar().hide();

        ConstraintLayout myLayout = (ConstraintLayout)findViewById(R.id.Clo_Main);

        m_BtnStart          = (Button) findViewById(R.id.btn_Start);
        m_lbTableName       = (TextView) findViewById(R.id.lb_TableName);
        mTimeTextView       = (TextView) findViewById(R.id.timeView);
        m_editTitle         = (EditText) findViewById(R.id.edit_Title);
        m_listView          = (ListView) findViewById(R.id.lv_Main);
        m_ListViewitems     = new ArrayList<ListView_Item>();
        m_MngInput          = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        m_Adapter           = new ListView_Adapter(this, m_ListViewitems);

        m_listView.setAdapter(m_Adapter);
        m_listView.setSelection(m_Adapter.getCount() - 1);


        myLayout.setOnTouchListener(new ConstraintLayout.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                HideKeyboard();
                return false;
            }
        });

        m_listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
            {
                ListView_Item item = (ListView_Item) adapterView.getItemAtPosition(position);
                String strRefer = item.getReference();
                int nDifIdx = item.getImageIdx();

                m_nMemberID = m_InitMemberId + position;
                AlertDialogShow(item.GetTitle(),strRefer, nDifIdx, false);
            }
        });

        m_editTitle.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus)
            {
                if (hasFocus)
                {
                    m_bMemoClicked = true;
                    m_MngInput.showSoftInput(m_editTitle, InputMethodManager.SHOW_IMPLICIT);
                }
                else
                {

                }
            }
        });

        m_BtnStart.setText("start");

        Intent intent = getIntent();
        m_nHapValue = intent.getIntExtra("Num1",0) + intent.getIntExtra("Num2",0);


        m_lbTableName.setText(CDataBaseSystem.Instance().GetTableName());
        String strLastDataSelete    = "SELECT * FROM "+ CDataBaseSystem.Instance().GetTableName() +" ORDER BY ROWID DESC LIMIT 1;";
        Cursor clsCursor            = CDataBaseSystem.Instance().Select(strLastDataSelete);

        if (clsCursor != null)
        {
            if(clsCursor.moveToNext())
            {
                m_InitMemberId  = clsCursor.getInt(0);
                String strTime  = clsCursor.getString(1);
                String strRefer = clsCursor.getString(2);
                m_nDifficultIdx = clsCursor.getInt(3);
                String strTitle = clsCursor.getString(4);

                _eDifficult[]   eDifficult      = _eDifficult.values();
                int             nImgID          = getResources().getIdentifier(eDifficult[m_nDifficultIdx].toString(), "drawable", getPackageName());
                ListView_Item clsListViewItem   = new ListView_Item(strTime, strRefer, nImgID, m_nDifficultIdx, strTitle);

                AddListViewItem(clsListViewItem);
            }
        }
    }

    /* ----------------------------------------------------------------------------- */
    // Event
    /* ----------------------------------e------------------------------------------- */
    @Override public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);

        Log.d("onConfigurationChanged" , "onConfigurationChanged");

        if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
        {//세로 전환시
            Log.d("onConfigurationChanged" , "Configuration.ORIENTATION_PORTRAIT");
//            RecordDataRefresh();
        }
        else if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
        { //가로전환시
            Log.d("onConfigurationChanged", "Configuration.ORIENTATION_LANDSCAPE");
        }
        else
        {

        }
    }


    @SuppressLint("HandlerLeak")
    Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            int mSec = msg.arg1 % 100;
            int sec = (msg.arg1 / 100) % 60;
            int min = (msg.arg1 / 100) / 60;

            @SuppressLint("DefaultLocale")
            String result = String.format("%02d:%02d.%02d", min, sec, mSec);

            if (result.equals("00:01:15:00"))
            {
//                Toast.makeText(Act_execute.this, "1분 15초가 지났습니다.", Toast.LENGTH_SHORT).show();
            }

            mTimeTextView.setText(result);
        }
    };

    /* ----------------------------------------------------------------------------- */
    // Private Function
    /* ----------------------------------------------------------------------------- */
    private void AddListViewItem(ListView_Item clsListViewItem)
    {
        m_ListViewitems.add(clsListViewItem);
        m_Adapter.notifyDataSetChanged();
    }


    private void UpdateListViewItem(int nListIdx, ListView_Item clsListViewItem)
    {
        m_ListViewitems.set(nListIdx,clsListViewItem);
        m_Adapter.notifyDataSetChanged();
    }


    private void HideKeyboard()
    {
        if (m_bMemoClicked)
        {
            m_MngInput.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            this.getWindow().getDecorView().clearFocus();
            m_bMemoClicked = false;
        }
    }


    public void InsertDB(ListView_Item clsListViewItem, String strTitle)
    {
        String strQuery = "";

        strQuery = "INSERT INTO " + CDataBaseSystem.Instance().GetTableName() + "(record_time, reference_txt, ImageIdx_n, Titel_text) " +
                "VAlUES('" + clsListViewItem.getTime() + "','" + clsListViewItem.getReference() + "', " + clsListViewItem.getImageIdx() + " , '" + strTitle + "')";
        CDataBaseSystem.Instance().ExcuteQuery(strQuery);
    }


    public void UpdateDB(ListView_Item clsListViewItem, String strTitle, int nDataID)
    {
        String strQuery = "";

        strQuery = "UPDATE " + CDataBaseSystem.Instance().GetTableName() + " SET " +
                "reference_txt = '"+ clsListViewItem.getReference()+"' , " +
                "ImageIdx_n = " + Integer.toString(clsListViewItem.getImageIdx()) +
                " WHERE _id = " +  Integer.toString(nDataID) ;
        CDataBaseSystem.Instance().ExcuteQuery(strQuery);
    }

    public void DeleteDB()
    {
        String strQuery = "";

        strQuery = "DELETE FROM " + CDataBaseSystem.Instance().GetTableName() + " WHERE ROWID IN (SELECT ROWID FROM Interval_tbl ORDER BY ROWID DESC LIMIT 1)";

        CDataBaseSystem.Instance().ExcuteQuery(strQuery);
    }

    private void AlertDialogShow(String strTitle, String strText, int nDifficultImgIdx, boolean bCreate)
    {
        final EditText      editText    = new EditText(this);
        final String[]      strItems    = _eDifficult.getNames();
        AlertDialog.Builder dlg         = new AlertDialog.Builder(this).setCancelable(false);

        editText.setText(strText);

        dlg.setTitle("Check Level");
        dlg.setView(editText);
        dlg.setSingleChoiceItems(strItems, nDifficultImgIdx,
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        m_nDifficultIdx = which;
//                            Toast.makeText(getApplicationContext(),"" + strItems[which],Toast.LENGTH_SHORT).show();
                    }
                });
        dlg.setNeutralButton("닫기",null);
        dlg.setPositiveButton("저장",new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int pos)
            {
                _eDifficult[]   eDifficult      = _eDifficult.values();
                String          strTime         = mTimeTextView.getText().toString();
                String          strRefer        = editText.getText().toString();
                int             nImgID          = getResources().getIdentifier(eDifficult[m_nDifficultIdx].toString(), "drawable", getPackageName());
                ListView_Item   clsListViewItem = new ListView_Item(strTime, strRefer, nImgID, m_nDifficultIdx, m_editTitle.getText().toString());

                if (bCreate)
                {
                    AddListViewItem(clsListViewItem);
                    InsertDB(clsListViewItem, m_editTitle.getText().toString());
                }
                else
                {
                    UpdateListViewItem( m_nMemberID - m_InitMemberId ,clsListViewItem);
                    UpdateDB(clsListViewItem,m_editTitle.getText().toString(),m_nMemberID);
                }

                HideKeyboard();
            }
        });

        dlg.show();
    }
    /* ----------------------------------------------------------------------------- */
    // Protected Function
    /* ----------------------------------------------------------------------------- */

    /* ----------------------------------------------------------------------------- */
    // Public Function
    /* ----------------------------------------------------------------------------- */

    /* ----------------------------------------------------------------------------- */
    // Controller Event
    /* ----------------------------------------------------------------------------- */
    public void btn_Back_onClick(View v)
    {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);

        intent.putExtra("Hap", m_nHapValue);
        setResult(RESULT_OK,intent);
        finish();
    }

    public void btn_Start_onClick(View v)
    {
        if (!m_bStarted)
        {
            m_BtnStart.setText("STOP");
            m_bStarted = true;
            m_Thrtime = new Thread(new timeThread());
            m_Thrtime.start();
        }
        else
        {
            m_Thrtime.interrupt();

            m_BtnStart.setText("START");
            m_bStarted = false;


            AlertDialogShow("Check Level",null, m_nDifficultIdx,true);
        }
    }


    public void btn_Cancel_onClick(View v)
    {
        int nMaxIdx = m_ListViewitems.size();

        if (nMaxIdx != 0)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(Act_execute.this);
            builder.setTitle("Cancel");
            builder.setMessage("Do you want to remove last data?");

            builder.setPositiveButton("yes",new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int pos)
                {
                    m_ListViewitems.remove(nMaxIdx - 1);

                    m_Adapter.notifyDataSetChanged();
                    DeleteDB();
                }
            });

            builder.setNegativeButton("No",null);
            builder.setNeutralButton("취소",null);
            builder.create().show();
        }
    }

    public class timeThread implements Runnable
    {
        @Override
        public void run()
        {
            int i = 0;

            while (true)
            {
                while (m_bRunning)
                { //일시정지를 누르면 멈춤
                    Message msg = new Message();
                    msg.arg1 = i++;
                    handler.sendMessage(msg);

                    try
                    {
                        Thread.sleep(10);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();

                        runOnUiThread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
//                                mTimeTextView.setText("");
//                                mTimeTextView.setText("00:00:00:00");
                            }
                        });
                        return; // 인터럽트 받을 경우 return
                    }
                }
            }
        }
    }

}
