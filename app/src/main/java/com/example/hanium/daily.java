package com.example.hanium;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

public class daily extends Fragment {
    private TextView now;
    private TextView scheduled;
    private Button leftday,rightday,sedit;
    public daily(){

    }
    public void onStart() {
        super.onStart();
        scheduled = getView().findViewById(R.id.scheduleD);
        now = getView().findViewById(R.id.today);
        leftday = getView().findViewById(R.id.leftday);
        rightday = getView().findViewById(R.id.rightday);
        leftday.setOnClickListener(leftgo);
        rightday.setOnClickListener(rightgo);
        sedit = getView().findViewById(R.id.setschedule);
        sedit.setOnClickListener(Sedit);
        setdaily();


    }
    public void setdaily(){
        Calendar cal=((MainActivity)getActivity()).calreturn();
        now.setText(cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.DATE));
        SQLiteDatabase DDB =((MainActivity)getActivity()).dbget();
        Date ddate =new Date(cal.getTimeInMillis()-(cal.getTimeInMillis()%100000000));
        long date = ddate.getTime();
        String sql = "select * from Info where day = "+ date;
        Cursor cursor = DDB.rawQuery(sql,null);
        String Info = "";
        while(cursor.moveToNext()){
            Info = cursor.getString(1);
        }
        if(Info.equals("")){scheduled.setText("");}
        else{
            scheduled.setText(Info);
        }


    }
    final View.OnClickListener Sedit = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ((MainActivity)getActivity()).Tabset(4);
            ((MainActivity)getActivity()).recentsave(3);
            setdaily();
        }
    };
    final View.OnClickListener leftgo = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ((MainActivity)getActivity()).daymove(2,-1);
            Calendar cal=((MainActivity)getActivity()).calreturn();
            now.setText(cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.DATE));
            setdaily();
        }
    };
    final View.OnClickListener rightgo = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ((MainActivity)getActivity()).daymove(2,+1);
            Calendar cal=((MainActivity)getActivity()).calreturn();
            now.setText(cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.DATE));
            setdaily();
        }
    };
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_daily, container, false);
    }
}
