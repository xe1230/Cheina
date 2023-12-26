package com.example.chelina;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Act_SelectionCourse extends AppCompatActivity
{
    ListView m_listView;
    ArrayList<String> m_list_str;
    ArrayAdapter        m_adapter;
    String[]            m_strversions = {"Aestro","Blender","CupCake","Donut","Eclair","Froyo","GingerBread","HoneyComb"};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frm_selection_course);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        m_listView = findViewById(R.id.list_view);

        m_list_str = new ArrayList<>();

        for (int nCnt = 0; nCnt < m_strversions.length; nCnt++)
        {
            m_list_str.add(m_strversions[nCnt]);
        }

        m_listView.setChoiceMode(m_listView.CHOICE_MODE_SINGLE);


        m_adapter = new ArrayAdapter(Act_SelectionCourse.this,android.R.layout.simple_list_item_single_choice, m_list_str);

        m_listView.setAdapter(m_adapter);
        m_listView.setSelection(m_adapter.getCount() - 1);
        m_listView.setItemChecked(3,true);

        m_listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                Toast.makeText(Act_SelectionCourse.this, "Selected -> " + m_strversions[i], Toast.LENGTH_SHORT).show();
            }
        });
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

        setResult(RESULT_OK, intent);
        finish();
    }


}