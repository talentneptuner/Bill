package com.example.a11417.bill;

import java.util.ArrayList;
import  java.util.Calendar;

import android.content.ComponentName;
import android.content.Intent;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;
import  com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

public class Datasort extends AppCompatActivity {
    //private Spinner year;
    //private  Spinner month;
    private PieChart mChart;
    private SQLiteDatabase db;
    private ArrayList<String> types=new ArrayList<String>();
    private ArrayList<Entry> sums=new ArrayList<Entry>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datasort);
        types.add("三餐消费");
        types.add("零食消费");
        types.add("衣服消费");
        types.add("游玩消费");
        types.add("学习消费");
        types.add("通信消费");
        types.add("杂项消费");
        String year;
        String month;
        db= SQLiteDatabase.openOrCreateDatabase(this.getFilesDir().toString()+"my.db3",null);
        if (db!=null&&db.isOpen())
            Toast.makeText(Datasort.this,"数据库创建完毕",Toast.LENGTH_LONG).show();
        mChart=(PieChart)findViewById(R.id.pirchart) ;
        Calendar calendar=Calendar.getInstance();
        String now_year=(calendar.get(Calendar.YEAR))+"";
        final String now_month=(calendar.get(Calendar.MONTH)+1)+"";
        getdata(now_year,now_month,mChart);
        Button bu=(Button)findViewById(R.id.turn);
        bu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ComponentName comp=new ComponentName(Datasort.this,Datasort2.class);
                Intent intent=new Intent();
                intent.setComponent(comp);
                startActivity(intent);
            }
        });

    }

    private void getdata(String years,String months,PieChart mChart){
        double a,b,c,d,e,f,g;
        a=b=c=d=e=f=g=0;
        String m;
        String key=years+"-"+months;
        try {
            String sql = "select * from outcome";
            Cursor cursor = db.rawQuery(sql, new String[0]);
            while (cursor.moveToNext()) {
                String outtype = cursor.getString(cursor.getColumnIndex("otype"));
                String outtime = cursor.getString(cursor.getColumnIndex("otime"));
                Double outmoney = Double.parseDouble(cursor.getString(cursor.getColumnIndex("omoney")));
                if (outtime.contains(key)) {
                    if (outtype.equals("三餐消费")) {
                        a = a + outmoney;
                    } else if (outtype.equals("零食消费")) {
                        b = b + outmoney;
                    } else if (outtype.equals("衣服消费")) {
                        c = c + outmoney;
                    } else if (outtype.equals("游玩消费")) {
                        d = d + outmoney;
                    } else if (outtype.equals("学习花费")) {
                        e = e + outmoney;
                    } else if (outtype.equals("通信花费")) {
                        f = f + outmoney;
                    } else if (outtype.equals("杂项消费")) {
                        g = g + outmoney;
                    }
                }
            }
            sums.add(new Entry((float)a,0));
            sums.add(new Entry((float)b,1));
            sums.add(new Entry((float)c,2));
            sums.add(new Entry((float)d,3));
            sums.add(new Entry((float)e,4));
            sums.add(new Entry((float)f,5));
            sums.add(new Entry((float)g,6));
            PieDataSet pieDataSet=new PieDataSet(sums,"本月花费图");
            pieDataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
            PieData data=new PieData(types,pieDataSet);
            mChart.setData(data);
            mChart.animateXY(1000,1000);
            mChart.highlightValue(null);
            mChart.setCenterText("本月花费图");
            mChart.setCenterTextColor(this.getResources().getColor(R.color.lightcoral));
            mChart.setCenterTextSize(25);
            mChart.invalidate();
        }
        catch (Exception ex)
        {
            Toast.makeText(Datasort.this,"查询出错，稍后再试",Toast.LENGTH_LONG);
        }
    }
}
