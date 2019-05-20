package com.example.hanium;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import java.util.Calendar;
import java.util.Date;

public class scheduler extends  Fragment {

    Button save;
    Button cancel;
    EditText infos;
    TextView nowdates;
    public scheduler(){
    }
    public void onStart(){
        super.onStart();
        infos = getView().findViewById(R.id.scheduleinfo);
        save = getView().findViewById(R.id.save);
        cancel = getView().findViewById(R.id.cancel);
        nowdates = getView().findViewById(R.id.nowdate);
        Calendar cal = ((MainActivity)getActivity()).calreturn();
        nowdates.setText(cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.DATE));
        save.setOnClickListener(saving);
        cancel.setOnClickListener(canceling);
        database DBS = new database(getActivity(),"Info.db",null,1);
        SQLiteDatabase DDB = DBS.getReadableDatabase();
        Date ddate =new Date(cal.getTimeInMillis());
        long date = ddate.getTime();
        String sql = "select * from Info where day = "+ date;
        Cursor cursor = DDB.rawQuery(sql,null);
        String Day = "";
        String Info = "";
        while(cursor.moveToNext()){
            Day = cursor.getString(0);
            Info = cursor.getString(1);
        }
        if(Day.equals("")){}
        else{
            infos.setText(Info);
        }
        DDB.close();


    }
    final View.OnClickListener saving  = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            SQLiteDatabase DDB =((MainActivity)getActivity()).dbget();
            Calendar cal=((MainActivity)getActivity()).calreturn();
            Date ddate =new Date((((MainActivity)getActivity()).calreturn().getTimeInMillis())-(((MainActivity)getActivity()).calreturn().getTimeInMillis())%100000000);
            long date = ddate.getTime();
            String info = String.valueOf(infos.getText());
            String sql = "replace into Info values('"+date+"','"+info+"')";
            ((MainActivity)getActivity()).dbset(sql);
            ((MainActivity)getActivity()).cancel();
        }
    };
    final View.OnClickListener canceling  = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ((MainActivity)getActivity()).cancel();
        }
    };
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_schedule, container, false);
    }
}
