package com.example.chelina;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.chelina.Recipe.CRecordData;

public class MainActivity extends AppCompatActivity
{
    Button btn_OpenNew;
    private TextView m_editRecord    = null;


    public static final int REQUEST_CODE_MENU = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_OpenNew = (Button) findViewById(R.id.btn_openExecute);
        m_editRecord = (TextView) findViewById(R.id.edit_moto);

        Intent intent = getIntent();
        procesIntent(intent);

    }

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


    public void onClick(View view)
    {
        Intent intent = new Intent(getApplicationContext(),Act_execute.class);
        CRecordData data = new CRecordData(100, m_editRecord.getText().toString());
        intent.putExtra("data", data);
//        startActivity(intent);
        startActivityForResult(intent, REQUEST_CODE_MENU);
        //AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        //builder.setTitle("Tilte");
        //builder.setMessage("내용입니다.");
        //builder.setPositiveButton("yes",null);
        //builder.setNegativeButton("아니요",null);
        //builder.setNeutralButton("취소",null);
        //builder.create().show();
    }
}