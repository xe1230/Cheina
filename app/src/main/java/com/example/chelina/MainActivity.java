package com.example.chelina;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.chelina.DataBase.CDataBaseSystem;
import com.example.chelina.Recipe.CRecordData;

public class MainActivity extends AppCompatActivity
{
    /* ----------------------------------------------------------------------------- */
    // Variable
    /* ----------------------------------------------------------------------------- */
    private Button      m_btnCourse     = null;
    private Button      m_btnExcute     = null;

    private TextView    m_editRecord    = null;
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
        m_editRecord = (TextView) findViewById(R.id.edit_moto);

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
        Intent intent = new Intent(getApplicationContext(), Act_Course.class);
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
}