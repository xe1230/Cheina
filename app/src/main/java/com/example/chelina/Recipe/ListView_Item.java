package com.example.chelina.Recipe;

public class ListView_Item
{
    private String  m_strTime;
    private String  m_strReference;
    private int     m_nImgID;
    private int     m_nImgIdx;
    private String  m_strTile;

    public ListView_Item(String strTime, String strTitle, int nImgID, int nImgIdx, String strTile)
    {
        this.m_strTime          = strTime;
        this.m_strReference     = strTitle;
        this.m_nImgID           = nImgID;
        this.m_nImgIdx          = nImgIdx;
        this.m_strTile          = strTile;
    }


    public String getTime()
    {
        return m_strTime;
    }

    public String getReference()
    {
        return m_strReference;
    }

    public String GetTitle()
    {
        return  m_strTile;
    }

    public int getImageID()
    {
        return m_nImgID;
    }

    public int getImageIdx()
    {
        return m_nImgIdx;
    }
}