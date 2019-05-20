package com.example.hanium;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private database DBS;
    private SQLiteDatabase DDB;

    public Calendar cal;
    private int recenttab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cal = Calendar.getInstance();


        /////
        DBS = new database(this ,"Info.db",null,1);
        DDB = DBS.getReadableDatabase();
        Cursor cursor = DDB.rawQuery("select * from Info where day = 0",null);
        int recenttab = 1;
        while(cursor.moveToNext()){
            recenttab = cursor.getInt(1);
        }
        Tabset(recenttab);
    }
    public SQLiteDatabase dbget(){
        DDB = DBS.getReadableDatabase();
        return DDB;
    }
    public void dbset(String N){
        DDB = DBS.getWritableDatabase();
        DDB.execSQL(N);
    }
    public void recentsave(int N){
        recenttab = N;
        DDB = DBS.getWritableDatabase();
        DDB.execSQL("replace into Info values('"+0+"','"+N+"')");
    }
    public void cancel(){
        switch(recenttab){
            case 1: Tabset(1);
                break;
            case 2:Tabset(2);
                break;
            case 3:Tabset(3);
                break;

        }
    }
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.monthly :
                Tabset(1);
                break;

            case R.id.weekly :

                Tabset(2);
                break;
            case R.id.daily :

                Tabset(3);
                break;
        }
    }
    public void daymove(int N,int M){//날짜 변경
        if(N==1){
            cal.add(Calendar.MONTH,M);
        }
        else if(N ==2){
            cal.add(Calendar.DATE,M);
        }else if(N==3){
            cal.set(Calendar.DAY_OF_MONTH,M);
        }
    }
    public Calendar calreturn(){return cal;}//현재 위치

    public void Tabset(int number){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (number){
            case 1:
                // monthly tab
                recentsave(1);
                monthly Monthly = new monthly();
                transaction.replace(R.id.fragment_container,Monthly);
                transaction.commit();
                break;

            case 2:

                //weekly tab
                recentsave(2);
                weekly Weekly = new weekly();
                transaction.replace(R.id.fragment_container, Weekly);
                transaction.commit();
                break;
            case 3:
                //daily tab
                recentsave(3);
                daily Daily = new daily();
                transaction.replace(R.id.fragment_container, Daily);
                transaction.commit();
                break;
            case 4:
                //schedule tab
                scheduler Scheduler = new scheduler();
                transaction.replace(R.id.fragment_container, Scheduler);
                transaction.commit();

        }
    }
}
