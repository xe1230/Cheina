package com.example.chelina.Recipe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.chelina.R;

import java.util.List;

public class CustomAdpter  extends BaseAdapter
{
    private Context             context = null;

    public CustomAdpter(Context context)
    {
        this.context = context;
    }
    @Override
    public int getCount()
    {


        return     0 ;
    }

    @Override
    public Object getItem(int i)
    {
        return null;
    }

    @Override
    public long getItemId(int i)
    {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup)
    {
        LayoutInflater layoutInflater  = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View            view            = layoutInflater.inflate(R.layout.listview_item, viewGroup, false);
//        view.textc
//        view.setfor
        return view;
    }
}
