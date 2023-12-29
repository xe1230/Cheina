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

import com.example.chelina.DataBase.CChelinaApp;
import com.example.chelina.DataBase.CDataBaseSystem;

import java.util.ArrayList;

public class Act_Course extends AppCompatActivity
{
    private ListView            m_listView              = null;
    private ArrayList<String>   m_list_strListViewItem  = new ArrayList<>();
    private ArrayAdapter        m_Adapter               = null;
    private CChelinaApp         m_clsConfigulation      = null;
    private int                 m_nListviewSelectedIdx  = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        int nCnt = 0;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.frm_course);
        getSupportActionBar().hide();

        m_listView          = findViewById(R.id.list_view);
        m_Adapter           = new ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, m_list_strListViewItem);
        m_clsConfigulation  = (CChelinaApp) getApplication();

        m_listView.setChoiceMode(m_listView.CHOICE_MODE_SINGLE);
        m_listView.setAdapter(m_Adapter);
        m_listView.setSelection(m_Adapter.getCount() - 1);

        m_listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int nRowIdx, long lID)
            {
                m_nListviewSelectedIdx = nRowIdx;

                SeleteTalbe(nRowIdx);
            }
        });

//        m_Adapter.getView()
        String sql = "SELECT name FROM sqlite_master WHERE type IN ('table', 'view') AND name LIKE '%_tbl' UNION ALL SELECT name FROM sqlite_temp_master WHERE type IN ('table', 'view') ORDER BY 1;";
        Cursor cursor = CDataBaseSystem.Instance().Select(sql);

        while (cursor.moveToNext())
        {
            String strTableName = cursor.getString(0);

            if (strTableName.equals(CDataBaseSystem.Instance().GetTableName()))
            {
                m_nListviewSelectedIdx = nCnt;
            }

            strTableName = strTableName.replace("_tbl", "");

            m_list_strListViewItem.add(strTableName);
            nCnt++;
        }

        m_listView.setItemChecked(m_nListviewSelectedIdx, true);
        m_Adapter.notifyDataSetChanged();
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
    private void SeleteTalbe(int nRowIdx)
    {
        String strCurrentTableName = m_list_strListViewItem.get(nRowIdx);
        m_listView.setItemChecked(nRowIdx, true);
        CDataBaseSystem.Instance().UseTable(strCurrentTableName);
        m_clsConfigulation.SaveData(strCurrentTableName);
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
                m_list_strListViewItem.add(strRefer);
                m_Adapter.notifyDataSetChanged();
            }
        });

        dlg.show();
    }

    public void btn_Delete_onClick(View view)
    {
        AlertDialog.Builder dlg = new AlertDialog.Builder(this).setCancelable(false);

        dlg.setTitle("Delte Table").setMessage("정말 삭제하시겠습니까?");
        dlg.setNeutralButton("닫기", null);
        dlg.setPositiveButton("삭제", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int pos)
            {
                String strQuery = "DROP TABLE " + m_list_strListViewItem.get(m_nListviewSelectedIdx) + "_tbl";


                if (CDataBaseSystem.Instance().ExcuteQuery(strQuery))
                {
                    m_list_strListViewItem.remove(m_nListviewSelectedIdx);

                    if (m_list_strListViewItem.size() != 0)
                    {
                        m_nListviewSelectedIdx -= 1;

                        SeleteTalbe(m_nListviewSelectedIdx);
                        m_Adapter.notifyDataSetChanged();
                    }
                }
            }
        });

        dlg.show();

    }
}
