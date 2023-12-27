package com.example.chelina;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chelina.DataBase.CDataBaseSystem;
import com.example.chelina.Recipe.CRecordData;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity
{
    /* ----------------------------------------------------------------------------- */
    // Variable
    /* ----------------------------------------------------------------------------- */
    private Button      m_btnCourse     = null;
    private Button      m_btnExcute     = null;

    private Button      m_btnFileWrite  = null;
    private TextView    m_editRecord    = null;
    private Button      m_btnBlog       = null;
    private boolean     m_fileWrite = true;

    public static final int REQUEST_CODE_MENU = 101;

    /* ----------------------------------------------------------------------------- */
    // InitInstance
    /* ----------------------------------------------------------------------------- */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        m_btnExcute = (Button) findViewById(R.id.btn_openExecute);
        m_btnFileWrite = (Button) findViewById(R.id.btn_FileWrite);
        m_editRecord = (TextView) findViewById(R.id.edit_moto);
        m_btnBlog = (Button) findViewById(R.id.btn_Blog);

        CDataBaseSystem.Instance().Initialize(this);
//        Intent intent = getIntent();
//        procesIntent(intent);

    }

    /* ----------------------------------------------------------------------------- */
    // Event
    /* ----------------------------------------------------------------------------- */
    @Override
    protected void onActivityResult(int nRequestCode, int nResultCode, Intent inteData)
    {
        super.onActivityResult(nRequestCode, nResultCode, inteData);

        if (nResultCode == RESULT_OK)
        {
            int nHap = inteData.getIntExtra("Hap",0);
//            Toast.makeText(getApplicationContext(),"" + nHap,Toast.LENGTH_SHORT).show();
        }

    }


    /* ----------------------------------------------------------------------------- */
    // Private Function
    /* ----------------------------------------------------------------------------- */
    private void procesIntent(Intent intent)
    {
        if (intent != null)
        {
            Bundle bundle       = intent.getExtras();

            if (bundle != null)
            {
                CRecordData data    = bundle.getParcelable("data");

                if (intent != null)
                {
                    m_editRecord.setText(data.message);
                }
            }
        }
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
    public void btn_openCourse_onClick(View view)
    {
        Intent intent = new Intent(getApplicationContext(),Act_SelectionCourse.class);
//        CRecordData data = new CRecordData(100, m_editRecord.getText().toString());
//        intent.putExtra("data", data);
//        startActivityForResult(intent, REQUEST_CODE_MENU);
        startActivityForResult(intent, 1);
    }




    public void btn_Excure_onClick(View view)
    {
        Intent intent = new Intent(getApplicationContext(),Act_execute.class);
//        CRecordData data = new CRecordData(100, m_editRecord.getText().toString());
//        intent.putExtra("data", data);
//        startActivityForResult(intent, REQUEST_CODE_MENU);

        intent.putExtra("Num1",5);
        intent.putExtra("Num2",7);
        startActivityForResult(intent, 0);
    }

    public void btn_Blog_onClick(View view)
    {
        Uri uri = Uri.parse("https://xe1230.tistory.com/");
        Intent inteBlog = new Intent(Intent.ACTION_VIEW,uri);

        android.util.Log.i("Dean 테스트", "btn_Blog_onClick()");
        startActivity(inteBlog);
        android.util.Log.i("Dean 테스트 End", "btn_Blog_onClick()");
    }

    public void btn_FileWrite_onClick(View view)
    {
        if (m_fileWrite)
        {
            try
            {
                FileOutputStream FOSout = openFileOutput("file.txt", Context.MODE_PRIVATE);
                String strValue = "쿡북 안드로이드";
                FOSout.write(strValue.getBytes());
                FOSout.close();
                Toast.makeText(getApplicationContext(), "file.tst가 생성됨",Toast.LENGTH_SHORT).show();

                m_btnFileWrite.setText("File Read");
                m_fileWrite = false;
            }
            catch (IOException ex)
            {

            }
        }
        else
        {
            try
            {
                FileInputStream FISout = openFileInput("file.txt");
                byte[] txt = new byte[30];
                FISout.read(txt);
                String strValue = new String(txt);
                Toast.makeText(getApplicationContext(), strValue,Toast.LENGTH_SHORT).show();

                FISout.close();
                m_btnFileWrite.setText("File write");

                m_fileWrite = true;
            }
            catch (IOException ex)
            {
                Toast.makeText(getApplicationContext(), "파일 없음",Toast.LENGTH_SHORT).show();

            }
        }
    }

}