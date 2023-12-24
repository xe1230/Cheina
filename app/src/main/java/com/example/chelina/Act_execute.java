package com.example.chelina;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.chelina.Recipe.CRecordData;
import com.example.chelina.Recipe.ListView_Adapter;
import com.example.chelina.Recipe.ListView_Item;

import java.util.ArrayList;
import java.util.List;


public class Act_execute extends AppCompatActivity
{
    //TODO https://jaejong.tistory.com/22 list view 작업
    public static final String KEY_SIMPLE_DATA = "data";

    /* ----------------------------------------------------------------------------- */
    // Variable
    /* ----------------------------------------------------------------------------- */

    private Thread              m_Thrtime       = null;
    private Button              m_BtnStart      = null;
    private TextView            mTimeTextView   = null;
    private TextView            m_editRecord    = null;
    private EditText            m_editMemo      = null;
    private ListView            listView = null;

    private Boolean             m_bRunning      = true;
    private Boolean             m_bStarted      = false;


    private InputMethodManager  m_MngInput      = null;
    private boolean             m_bMemoClicked  = false;
    private CRecordData         clsRecordData;
    private int                 m_nHapValue     = 0;
    private List<String>        m_strRecord     = new ArrayList<>();
    /* ----------------------------------------------------------------------------- */
    // Properties Function
    /* ----------------------------------------------------------------------------- */

    ArrayList<ListView_Item> items = null;
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

        listView        = (ListView) findViewById(R.id.lv_Main);
        items           = new ArrayList<ListView_Item>();
        m_MngInput      = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);

//        ArrayAdapter<String> adapter = new ArrayAdapter<String>();
        init_ArrayList(1);

        ListView_Adapter mAdapter = new ListView_Adapter(this, items);
        listView.setAdapter(mAdapter);

//        clsRecordData = (CRecordData) getIntent().getSerializableExtra("clsRecordData");
//        String resultData = getIntent().getStringExtra("dataKey");
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // AdapterView - 리스트뷰에 연결한 Adapter, getItemAtPosition(),
                // Adapter의 메소드 getItem()과 동일한 메소드
                ListView_Item item = (ListView_Item) adapterView.getItemAtPosition(position);

                // 클릭한 위치의 Item의 title 문자열 반환
                String title = item.getTitle();
                // 클릭한 위치의 Item Title 문자열 토스트로 보여주기
//                Toast.makeText(context, title, Toast.LENGTH_SHORT).show();
            }
        });

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
        m_BtnStart.setText("start");

        Intent intent = getIntent();
        m_nHapValue = intent.getIntExtra("Num1",0) + intent.getIntExtra("Num2",0);
//        procesIntent(intent);
    }




    /* ----------------------------------------------------------------------------- */
    // Event
    /* ----------------------------------------------------------------------------- */


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
    // Private Function
    /* ----------------------------------------------------------------------------- */
    private void init_ArrayList(int count) {
        // item을 저장할 List 생성


        // Drawable 이미지 리소스 ID 값을 가져오기 위해 Resource객체 생성
        Resources res = getResources();

        // 함수의 인자로 넘겨준 count 아이템 개수만큼 반복, 아이템 추가
        for (int i = 0; i < count; i++) {
            // 이미지리소스 id값을 가져옴, res.getIdentifier("이미지 이름", "리소스 폴더 이름", 현재패키지 이름)
//            int img_ID = res.getIdentifier("listview_item" + (i % 3), "drawable", getPackageName());
            int img_ID = res.getIdentifier("dif", "drawable", getPackageName());
            // item 객체 생성하여 리스트에 추가
            items.add(new ListView_Item((i + 1) + "번째 아이템", img_ID));
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

    private void HideKeyboard()
    {
//        InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (m_bMemoClicked)
        {
            m_MngInput.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }

    }

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



    private void RecordDataRefresh()
    {
        String strRecord = "";

        for (int nCnt = 0 ; nCnt < m_strRecord.size(); nCnt++)
        {
            strRecord += m_strRecord.get(nCnt)+ "\n";
        }

        m_editRecord.setText(strRecord);
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

//        CRecordData data = new CRecordData(100, m_editMemo.getText().toString());
//        intent.putExtra("data", data);
//        startActivityForResult(intent, 101);
        intent.putExtra("Hap", m_nHapValue);
        setResult(RESULT_OK,intent);
        finish();
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
            m_Thrtime.interrupt();

            m_BtnStart.setText("START");
            m_bStarted = false;
            m_strRecord.add(mTimeTextView.getText().toString());
            final EditText edittext = new EditText(this);
            final String[] strItems = new String[] { "Difficult", "Nomal", "Easy"};

            AlertDialog.Builder dlg = new AlertDialog.Builder(Act_execute.this).setCancelable(false);
            dlg.setTitle("Check Level");
            dlg.setView(edittext);
            dlg.setSingleChoiceItems( strItems,0,
                    new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int which)
                        {
                            Toast.makeText(getApplicationContext(),"" + strItems[which],Toast.LENGTH_SHORT).show();
                        }
                    });


            dlg.setNegativeButton("닫기",null);
            dlg.setPositiveButton("저장",new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int pos)
                {
//                    RemoveData(nMaxIdx);
                }
            });
            dlg.show();

            RecordDataRefresh();
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
