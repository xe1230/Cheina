package com.example.chelina;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;


public class Act_execute extends Activity
{
    private Thread      m_Thrtime       = null;
    private Button      m_BtnStart      = null;
    private TextView    mTimeTextView   = null;
    private TextView    m_editRecord = null;

    private Boolean     m_bRunning      = true;
    private Boolean     m_bStarted      = false;
    private List<String> m_strRecord     = new ArrayList<>();



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frm_execute);

        m_BtnStart = (Button) findViewById(R.id.btn_Start);
        mTimeTextView = (TextView) findViewById(R.id.timeView);
        m_editRecord = (TextView) findViewById(R.id.recordView);
        m_editRecord.setMovementMethod(new ScrollingMovementMethod());
    }


    public void btn_Back_onClick(View v)
    {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
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
            String strRecord = "";


            m_BtnStart.setText("START");
            m_bStarted = false;
            m_strRecord.add(mTimeTextView.getText().toString());

            for (int nCnt = 0 ; nCnt < m_strRecord.size(); nCnt++)
            {
                strRecord += m_strRecord.get(nCnt)+ "\n";
            }

            m_editRecord.setText(strRecord);
            m_Thrtime.interrupt();
        }
    }

    public void btn_Cancel_onClick(View v)
    {
        String strRecord = "";
        int nMaxIdx = m_strRecord.size();

        if (nMaxIdx != 0)
        {
            m_strRecord.remove(nMaxIdx - 1);

            for (int nCnt = 0 ; nCnt < m_strRecord.size(); nCnt++)
            {
                strRecord += m_strRecord.get(nCnt)+ "\n";
            }

            m_editRecord.setText(strRecord);
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
