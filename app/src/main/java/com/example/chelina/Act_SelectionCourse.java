package com.example.chelina;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import com.example.chelina.DataBase.CDataBaseSystem;

import java.util.ArrayList;

public class Act_SelectionCourse extends AppCompatActivity
{
    private ListView            m_listView;
    private ArrayList<String>   m_list_str = new ArrayList<>();
    private ArrayAdapter        m_adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frm_selection_course);
        getSupportActionBar().hide();

        m_listView = findViewById(R.id.list_view);

        m_listView.setChoiceMode(m_listView.CHOICE_MODE_SINGLE);

        m_adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, m_list_str);

        m_listView.setAdapter(m_adapter);
        m_listView.setSelection(m_adapter.getCount() - 1);
        m_listView.setItemChecked(3,true);


        m_listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int nRowIdx, long lID)
            {
                CDataBaseSystem.Instance().UseTable(m_list_str.get(nRowIdx));
            }
        });

        String sql = "SELECT name FROM sqlite_master WHERE type IN ('table', 'view') AND name LIKE '%_tbl' UNION ALL SELECT name FROM sqlite_temp_master WHERE type IN ('table', 'view') ORDER BY 1;";

        Cursor cursor = CDataBaseSystem.Instance().Select(sql);

        while(cursor.moveToNext())
        {
            String strTableName = cursor.getString(0);
            strTableName = strTableName.replace("_tbl","");

            m_list_str.add(strTableName);
        }

        m_adapter.notifyDataSetChanged();
    }

    /* ----------------------------------------------------------------------------- */
    // Private Function
    /* ----------------------------------------------------------------------------- */
//    private void CrateTable(String strName)
//    {
//        m_db.execSQL("CREATE TABLE IF NOT EXISTS "+ strName +"(_id integer PRIMARY KEY AUTOINCREMENT, record_time time, reference_txt text, ImageIdx_n int, Titel_text text)");
//    }

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

    public void btn_Add_onClick(View view)
    {
        final EditText edittext = new EditText(this);
        AlertDialog.Builder dlg = new AlertDialog.Builder(this).setCancelable(false);
        dlg.setTitle("Check Level");
        dlg.setView(edittext);

        dlg.setNeutralButton("닫기",null);
        dlg.setPositiveButton("저장",new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int pos)
            {
                String          strRefer        = edittext.getText().toString();

                CDataBaseSystem.Instance().CrateTable(strRefer);
                m_list_str.add(strRefer);
                m_adapter.notifyDataSetChanged();
            }
        });

        dlg.show();

    }
}