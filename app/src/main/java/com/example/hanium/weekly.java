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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
public class weekly extends  Fragment {
    protected Button weeks[];
    protected TextView todays;
    protected TextView nows;
    public weekly(){
    }
    public void onStart(){
        super.onStart();
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat ( "yyyy.MM.dd");
        todays = getView().findViewById(R.id.todayW);
        Date currentTime = new Date ();
        String mTime = mSimpleDateFormat.format ( currentTime );
        todays.setText("오늘 : " + mTime);

        nows = getView().findViewById(R.id.nowW);

        Button Left = getView().findViewById(R.id.leftweek);
        Left.setOnClickListener(leftgo);
        Button Right = getView().findViewById(R.id.rightweek);
        Right.setOnClickListener(rightgo);

        weeks = new Button[7];//1주의 버튼
        weeks[0] = getView().findViewById(R.id.sunday);
        weeks[1] = getView().findViewById(R.id.monday);
        weeks[2] = getView().findViewById(R.id.tuesday);
        weeks[3] = getView().findViewById(R.id.wednesday);
        weeks[4] = getView().findViewById(R.id.thursday);
        weeks[5] = getView().findViewById(R.id.friday);
        weeks[6] = getView().findViewById(R.id.saturday);
        for(int i=0;i<weeks.length;i++){
            weeks[i].setOnClickListener(scheduler);
        }

        setweek();


    }
    public void setweek(){
        Calendar cal=((MainActivity)getActivity()).calreturn();

        TextView now = getView().findViewById(R.id.nowW);
        int weekpoint = cal.get(Calendar.DAY_OF_WEEK);
        now.setText((cal.get(Calendar.YEAR)+"년 "+(cal.get(Calendar.MONTH)+1)+"월 "+ (cal.get(Calendar.WEEK_OF_MONTH)) + "째주"));
        ((MainActivity)getActivity()).daymove(2,(weekpoint-1)*(-1));
        SQLiteDatabase DDB =((MainActivity)getActivity()).dbget();

        for(int i=0;i<weeks.length;i++){

            Date ddate =new Date(cal.getTimeInMillis()-(cal.getTimeInMillis()%100000000));
            long date = ddate.getTime();
            String sql = "select * from Info where day = "+ date;
            Cursor cursor = DDB.rawQuery(sql,null);
            String Info = "";
            while(cursor.moveToNext()){
                Info = cursor.getString(1);
            }
            weeks[i].setText(cal.get(Calendar.DAY_OF_MONTH)+"");
            if(Info.equals("")){}
            else{weeks[i].setText(cal.get(Calendar.DAY_OF_MONTH)+":"+Info);
            }
            ((MainActivity)getActivity()).daymove(2,1);
        }


    }
    final View.OnClickListener leftgo = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ((MainActivity)getActivity()).daymove(2,-8);
            setweek();
        }
    };
    final View.OnClickListener rightgo = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ((MainActivity)getActivity()).daymove(2,+1);
            setweek();
        }
    };
    final View.OnClickListener scheduler = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Button pushed = getView().findViewById(view.getId());
            String K = String.valueOf(pushed.getText());
            if(K.equals("")){
            }else{
                String[] PA = K.split(":");
                int J =  Integer.parseInt(PA[0]);
                ((MainActivity)getActivity()).daymove(3,J);
                ((MainActivity)getActivity()).Tabset(4);
                ((MainActivity)getActivity()).recentsave(2);
            }

            TextView now = getView().findViewById(R.id.nowW);
            Calendar cal=((MainActivity)getActivity()).calreturn();
            now.setText((cal.get(Calendar.MONTH)+1)+"월 "+ (cal.get(Calendar.WEEK_OF_MONTH)) + "째주");
        }
    };
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_weekly, container, false);
    }
}
