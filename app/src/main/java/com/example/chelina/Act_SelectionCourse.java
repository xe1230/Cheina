package com.example.chelina;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.chelina.DataBase.CConfigulation;
import com.example.chelina.DataBase.CDataBaseSystem;

import java.util.ArrayList;

public class Act_SelectionCourse extends AppCompatActivity
{
    private ListView m_listView = null;
    private ArrayList<String> m_list_str = new ArrayList<>();
    private ArrayAdapter m_adapter = null;
    private CConfigulation m_clsConfigulation = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        int nSelectedIdx = 0;
        int nCnt = 0;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.frm_selection_course);
        getSupportActionBar().hide();

        m_listView = findViewById(R.id.list_view);
        m_listView.setChoiceMode(m_listView.CHOICE_MODE_SINGLE);

        m_adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, m_list_str);
        m_clsConfigulation = (CConfigulation) getApplication();
        m_listView.setAdapter(m_adapter);
        m_listView.setSelection(m_adapter.getCount() - 1);

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

        while (cursor.moveToNext())
        {
            String strTableName = cursor.getString(0);

            if (strTableName == CDataBaseSystem.Instance().GetTableName())
            {
                nSelectedIdx = nCnt;
            }

            strTableName = strTableName.replace("_tbl", "");

            m_list_str.add(strTableName);
            nCnt++;
        }

        m_listView.setItemChecked(nSelectedIdx, true);
        m_adapter.notifyDataSetChanged();
    }
    /* ----------------------------------------------------------------------------- */
    // Event
    /* ----------------------------------------------------------------------------- */
    private View.OnKeyListener on_KeyEvent = new View.OnKeyListener()
    {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event)
        {
            if (event.getAction() == KeyEvent.ACTION_DOWN)
            {
                if (keyCode == KeyEvent.KEYCODE_ENTER)
                {
                    return true;
                }
            }
            return false;
        }
    };

    /* ----------------------------------------------------------------------------- */
    // Private Function
    /* ----------------------------------------------------------------------------- */

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
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

        setResult(RESULT_OK, intent);
        finish();
    }

    public void btn_Add_onClick(View view)
    {
        final EditText editText = new EditText(this);
        AlertDialog.Builder dlg = new AlertDialog.Builder(this).setCancelable(false);
        editText.setOnKeyListener(on_KeyEvent);
        dlg.setTitle("Check Level");
        dlg.setView(editText);

        dlg.setNeutralButton("닫기", null);
        dlg.setPositiveButton("저장", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int pos)
            {
                String strRefer = editText.getText().toString();

                strRefer = strRefer.replace(" ", "_");

                CDataBaseSystem.Instance().CrateTable(strRefer);
                m_list_str.add(strRefer);
                m_adapter.notifyDataSetChanged();
            }
        });

        dlg.show();

    }


}
