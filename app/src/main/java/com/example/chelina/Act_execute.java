package com.example.chelina;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class Act_execute extends Activity
{
    private Button mStartBtn, mStopBtn;
    private TextView mTimeTextView, mRecordTextView;
    private Thread timeThread = null;
    private Boolean isRunning = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frm_execute);

        mStartBtn = (Button) findViewById(R.id.btn_Start);
        mStopBtn = (Button) findViewById(R.id.btn_Stop);
//        mRecordBtn = (Button) findViewById(R.id.btn_record);
//        mPauseBtn = (Button) findViewById(R.id.btn_pause);
        mTimeTextView = (TextView) findViewById(R.id.timeView);
        mRecordTextView = (TextView) findViewById(R.id.recordView);

    }


    public void btn_Back_onClick(View v)
    {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }


    public void btn_Start_onClick(View v)
    {
        v.setVisibility(View.GONE);
        mStopBtn.setVisibility(View.VISIBLE);

        timeThread = new Thread(new timeThread());
        timeThread.start();
    }

    public void btn_Stop_onClick(View v)
    {
        mRecordTextView.setText(mRecordTextView.getText() + mTimeTextView.getText().toString() + "\n");
        // test
        String strw3evalue =  mRecordTextView.getText().toString();
        String stre = mTimeTextView.getText().toString();
        v.setVisibility(View.GONE);
//        mRecordBtn.setVisibility(View.GONE);
        mStartBtn.setVisibility(View.VISIBLE);
//        mPauseBtn.setVisibility(View.GONE);dsdf
        mRecordTextView.setText(stre);
        timeThread.interrupt();
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

            @SuppressLint("DefaultLocale") String result = String.format("%02d:%02d:%02d:%02d", hour, min, sec, mSec);
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
                while (isRunning)
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
                                mTimeTextView.setText("");
                                mTimeTextView.setText("00:00:00:00");
                            }
                        });
                        return; // 인터럽트 받을 경우 return
                    }
                }
            }
        }
    }

}
