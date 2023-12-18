package com.example.chelina;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
{
    Button btn_OpenNew;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_OpenNew = (Button) findViewById(R.id.btn_openExecute);

    }


    public void onClick(View view)
    {
        Intent intent = new Intent(getApplicationContext(),Act_execute.class);
        startActivity(intent);
        //Intent intent = new Intent(getApplicationContext(),Act_execute.class);
        //startActivity(intent);
        //AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        //builder.setTitle("Tilte");
        //builder.setMessage("내용입니다.");
        //builder.setPositiveButton("yes",null);
        //builder.setNegativeButton("아니요",null);
        //builder.setNeutralButton("취소",null);
        //builder.create().show();
    }
}