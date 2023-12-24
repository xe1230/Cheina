package com.example.chelina.Recipe;

public class ListView_Item
{
    private String m_strTime;
    private String m_strReference;
    private int m_nImgIdx;

    public ListView_Item(String strTime, String strTitle, int nImgIdx)
    {
        this.m_strTime          = strTime;
        this.m_strReference     = strTitle;
        this.m_nImgIdx          = nImgIdx;
    }


    public String getTime() {
        return m_strTime;
    }

    public String getReference()
    {
        return m_strReference;
    }

    public int getImage()
    {
        return m_nImgIdx;
    }
}
