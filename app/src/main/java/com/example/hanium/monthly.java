package com.example.hanium;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
public class monthly extends  Fragment {
    public monthly(){

    }
    private Button[] btn;
    public void onStart() {
        super.onStart();
        LinearLayout linear1 =  getView().findViewById(R.id.linear1);
        LinearLayout linear2 = getView().findViewById(R.id.linear2);
        LinearLayout linear3 =  getView().findViewById(R.id.linear3);
        LinearLayout linear4 =  getView().findViewById(R.id.linear4);
        LinearLayout linear5 = getView().findViewById(R.id.linear5);
        LinearLayout linear6 = getView().findViewById(R.id.linear6);


        Button Left = getView().findViewById(R.id.leftmonth);
        Left.setOnClickListener(leftgo);
        Button Right = getView().findViewById(R.id.rightmonth);
        Right.setOnClickListener(rightgo);



        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat ( "yyyy.MM.dd");
        Date currentTime = new Date ();
        String mTime = mSimpleDateFormat.format ( currentTime );
        TextView today = (TextView) getView().findViewById(R.id.Todaym);
        today.setText("오늘 : " + mTime);
        TextView now = (TextView) getView().findViewById(R.id.nowM);
        Calendar cal=((MainActivity)getActivity()).calreturn();
        now.setText(cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1));
        btn = new Button[42];
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        params.weight = 1;
        for (int i = 0; i < btn.length; i++) {
            btn[i] = new Button(getActivity());
            btn[i].setText("" + (i + 1));
            btn[i].setTextSize(10);
            btn[i].setId(i);
            btn[i].setOnClickListener(scheduler);
            if (i < 7) {
                linear1.addView(btn[i]);
            } else if (i < 14) {
                linear2.addView(btn[i]);
            } else if (i < 21) {
                linear3.addView(btn[i]);
            } else if (i < 28) {
                linear4.addView(btn[i]);
            } else if (i < 35){
                linear5.addView(btn[i]);
            }else{
                linear6.addView(btn[i]);
            }
            btn[i].setLayoutParams(params);
            btn[i].setWidth(12);

        }
        setmonth();


    }
    final View.OnClickListener scheduler  = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Button pushed = getView().findViewById(view.getId());
            String K = String.valueOf(pushed.getText());
            if(K.equals("")){

            }else{
                int J =  Integer.parseInt(K);
                ((MainActivity)getActivity()).daymove(3,J);
                ((MainActivity)getActivity()).Tabset(4);
                ((MainActivity)getActivity()).recentsave(1);
            }

            TextView now = getView().findViewById(R.id.nowM);
            Calendar cal=((MainActivity)getActivity()).calreturn();
            now.setText(cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.DATE));
            }
    };
    final View.OnClickListener leftgo = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ((MainActivity)getActivity()).daymove(1,-1);
            TextView now =getView().findViewById(R.id.nowM);
            Calendar cal=((MainActivity)getActivity()).calreturn();
            now.setText(cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.DATE));
            setmonth();
        }
    };
    final View.OnClickListener rightgo = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ((MainActivity)getActivity()).daymove(1,+1);
            TextView now = (TextView) getView().findViewById(R.id.nowM);
            Calendar cal=((MainActivity)getActivity()).calreturn();
            now.setText(cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.DATE));
            setmonth();
        }
    };
    public void setmonth(){
        Calendar cal=((MainActivity)getActivity()).calreturn();
        Calendar cal2 = cal;
        cal2.set(Calendar.DAY_OF_MONTH,1);
        int startweek = cal2.get(Calendar.DAY_OF_WEEK);
        int endDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH)+1;

        SQLiteDatabase DDB =((MainActivity)getActivity()).dbget();




        for (int i = 0; i < btn.length; i++) {

            if(i>startweek-2&&i<endDay+startweek-2){
                cal2.set(Calendar.DAY_OF_MONTH,(i - startweek + 2));
                btn[i].setText("" + (i - startweek + 2));
                Date ddate =new Date(cal2.getTimeInMillis()-(cal2.getTimeInMillis()%100000000));
                long date = ddate.getTime();
                String sql = "select * from Info where day = "+ date;
                Cursor cursor = DDB.rawQuery(sql,null);
                String Info = "";
                while(cursor.moveToNext()){
                    Info = cursor.getString(1);
                }
                if(Info.equals("")){btn[i].setBackgroundColor(Color.rgb(200,200,200));}
                else{
                    btn[i].setBackgroundColor(Color.rgb(100,100,255));
                }
            }else{btn[i].setText("");}
        }



    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_monthly, container, false);
    }
}
