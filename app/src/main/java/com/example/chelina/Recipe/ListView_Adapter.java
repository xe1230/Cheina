package com.example.chelina.Recipe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chelina.R;

import java.util.List;

public class ListView_Adapter extends BaseAdapter
{
    private List<ListView_Item> m_items = null;
    private Context             context = null;

    public ListView_Adapter(Context context,List<ListView_Item> items)
    {
       this.m_items = items;
       this.context = context;
    }

    @Override
    public int getCount()
    {
        return m_items.size();
    }

    @Override
    public Object getItem(int position)
    {
        return m_items.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater  layoutInflater  = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View            view            = layoutInflater.inflate(R.layout.listview_item, parent, false);
        TextView        number          = view.findViewById(R.id.listitem_number);
        TextView        edit_Time       = view.findViewById(R.id.listitem_tiem);
        TextView        edit_Reference  = view.findViewById(R.id.listitem_title);
        ImageView       image           = view.findViewById(R.id.listitem_image);

        ListView_Item item = m_items.get(position);
        number.setText(String.valueOf(position+1));		// 해당위치 +1 설정, 배열순으로 0부터 시작
        edit_Time.setText(item.getTime());
        edit_Reference.setText(item.getReference());
        image.setImageResource(item.getImageID());		// item 객체 내용을 가져와 세팅

        return view;
    }

}
