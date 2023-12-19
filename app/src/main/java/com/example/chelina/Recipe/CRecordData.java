package com.example.chelina.Recipe;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

public class CRecordData implements Parcelable
{
    public int number;          //number, message 2가지의 변수를 지정해주었음
    public String message;     // number = 정수 message = 문자열

    public String name;
    public List<String> m_strRecord     = new ArrayList<>();

    public CRecordData(int num, String msg)
    {
        number = num;
        message = msg;
    }
    protected CRecordData(Parcel in)
    {
        number = in.readInt();
        message = in.readString();
    }

    public static final Creator<CRecordData> CREATOR = new Creator<CRecordData>() {
        @Override
        public CRecordData createFromParcel(Parcel in) {
            return new CRecordData(in); // -- SimpleData 생성자를 호출해 Parcel 객체에서 읽기
        }
        @Override
        public CRecordData[] newArray(int size) {
            return new CRecordData[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) { //-- Parcel 객체로 쓰기
        // writeToParcel = SimpleData 객체안에 들어있는 데이터를 Parcel 객체로 만드는 역할을 해줌
        // 이 메서드 안에서는 writeInt(), writeString() 2가지의 메서드를 사용함
        dest.writeInt(number);
        dest.writeString(message);
    }
}

