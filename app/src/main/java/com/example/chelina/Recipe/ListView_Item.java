package com.example.chelina.Recipe;

public class ListView_Item
{
    private String m_strTitle;
    private int m_nImgIdx;

    public ListView_Item(String strTitle, int nImgIdx)
    {
        this.m_strTitle = strTitle;
        this.m_nImgIdx = nImgIdx;
    }

    public String getTitle() {
        return m_strTitle;
    }

    public int getImage() {
        return m_nImgIdx;
    }
}
