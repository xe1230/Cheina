package com.example.chelina;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.chelina.Recipe.CRecordData;

import java.util.ArrayList;
import java.util.List;


public class Act_execute extends AppCompatActivity
{

    public static final String KEY_SIMPLE_DATA = "data";

    /* ----------------------------------------------------------------------------- */
    // Variable
    /* ----------------------------------------------------------------------------- */

    private Thread              m_Thrtime       = null;
    private Button              m_BtnStart      = null;
    private TextView            mTimeTextView   = null;
    private TextView            m_editRecord    = null;
    private EditText            m_editMemo      = null;

    private Boolean             m_bRunning      = true;
    private Boolean             m_bStarted      = false;
    private List<String>        m_strRecord     = new ArrayList<>();
    private InputMethodManager  m_MngInput      = null;
    private boolean             m_bMemoClicked  = false;
    CRecordData clsRecordData;
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

        ConstraintLayout myLayout = (ConstraintLayout)findViewById(R.id.Clo_Main);

        m_BtnStart      = (Button) findViewById(R.id.btn_Start);
        mTimeTextView   = (TextView) findViewById(R.id.timeView);
        m_editRecord    = (TextView) findViewById(R.id.recordView);
        m_editMemo      = (EditText) findViewById(R.id.edit_Memo);
        m_MngInput      = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);

//        clsRecordData = (CRecordData) getIntent().getSerializableExtra("clsRecordData");
//        String resultData = getIntent().getStringExtra("dataKey");


        myLayout.setOnTouchListener(new ConstraintLayout.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                HideKeyboard();
                return false;
            }
        });
        m_editRecord.setMovementMethod(new ScrollingMovementMethod());


        Intent intent = getIntent();
        procesIntent(intent);
    }

    @Override public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);

        Log.d("onConfigurationChanged" , "onConfigurationChanged");

        if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
        {//세로 전환시
             Log.d("onConfigurationChanged" , "Configuration.ORIENTATION_PORTRAIT");

            RecordDataRefresh();
        }
        else if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
        { //가로전환시
             Log.d("onConfigurationChanged", "Configuration.ORIENTATION_LANDSCAPE");
        }
        else
        {

        }
    }

    private void procesIntent(Intent intent)
    {
        if (intent != null)
        {
            Bundle bundle       = intent.getExtras();
            CRecordData data    = bundle.getParcelable(KEY_SIMPLE_DATA);

            if (intent != null)
            {
                m_editMemo.setText(data.message);

            }
        }
    }

    /* ----------------------------------------------------------------------------- */
    // Event
    /* ----------------------------------------------------------------------------- */
    private void HideKeyboard()
    {
//        InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (m_bMemoClicked)
        {
            m_MngInput.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }

    }


    /* ----------------------------------------------------------------------------- */
    // Private Function
    /* ----------------------------------------------------------------------------- */
    private void RemoveData(int nMaxIdx)
    {
        String strRecord = "";


        m_strRecord.remove(nMaxIdx - 1);

        for (int nCnt = 0 ; nCnt < m_strRecord.size(); nCnt++)
        {
            strRecord += m_strRecord.get(nCnt)+ "\n";
        }

        m_editRecord.setText(strRecord);
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
            int hour = (msg.arg1 / 100) / 360;
            //1000이 1초 1000*60 은 1분 1000*60*10은 10분 1000*60*60은 한시간

            @SuppressLint("DefaultLocale") String result = String.format("%02d:%02d:%02d", min, sec, mSec);

            if (result.equals("00:01:15:00"))
            {
                Toast.makeText(Act_execute.this, "1분 15초가 지났습니다.", Toast.LENGTH_SHORT).show();
            }

            mTimeTextView.setText(result);
        }
    };
    /* ----------------------------------------------------------------------------- */
    // Protected Function
    /* ----------------------------------------------------------------------------- */

    /* ----------------------------------------------------------------------------- */
    // Public Function
    /* ----------------------------------------------------------------------------- */

    /* ----------------------------------------------------------------------------- */
    // Controller Event
    /* ----------------------------------------------------------------------------- */
    private void RecordDataRefresh()
    {
        String strRecord = "";

        for (int nCnt = 0 ; nCnt < m_strRecord.size(); nCnt++)
        {
            strRecord += m_strRecord.get(nCnt)+ "\n";
        }

        m_editRecord.setText(strRecord);
    }

    public void btn_Back_onClick(View v)
    {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);

        CRecordData data = new CRecordData(100, m_editMemo.getText().toString());
        intent.putExtra("data", data);
//        startActivity(intent);
        startActivityForResult(intent, 101);
    }

    public void edit_Memo_onClick(View v)
    {
        m_MngInput.showSoftInput(m_editMemo, InputMethodManager.SHOW_IMPLICIT);
        m_bMemoClicked = true;
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


            m_BtnStart.setText("START");
            m_bStarted = false;
            m_strRecord.add(mTimeTextView.getText().toString());

            RecordDataRefresh();
            m_Thrtime.interrupt();
        }
    }

    public void btn_Cancel_onClick(View v)
    {
        int nMaxIdx = m_strRecord.size();


        if (nMaxIdx != 0)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(Act_execute.this);
            builder.setTitle("Cancel");
            builder.setMessage("Do you want to remove last data?");

            builder.setPositiveButton("yes",new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int pos)
                {
                    RemoveData(nMaxIdx);
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
