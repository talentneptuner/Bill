package com.example.a11417.bill;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;

public class Datasort2 extends AppCompatActivity {
    private PieChart mChart;
    private SQLiteDatabase db;
    private ArrayList<String> types=new ArrayList<String>();
    private ArrayList<Entry> sums=new ArrayList<Entry>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datasort2);
        types.add("父母生活费");
        types.add("兼职工资");
        types.add("奖学金");
        types.add("理财所得");
        types.add("杂项收入");
        String year;
        String month;
        db= SQLiteDatabase.openOrCreateDatabase(this.getFilesDir().toString()+"my.db3",null);
        if (db!=null&&db.isOpen())
            Toast.makeText(Datasort2.this,"数据库创建完毕",Toast.LENGTH_LONG).show();
        mChart=(PieChart)findViewById(R.id.pirchart2) ;
        Calendar calendar=Calendar.getInstance();
        String now_year=(calendar.get(Calendar.YEAR))+"";
        final String now_month=(calendar.get(Calendar.MONTH)+1)+"";
        getdata(now_year,now_month,mChart);
    }

    private void getdata(String years,String months,PieChart mChart){
        double a,b,c,d,e;
        a=b=c=d=e=0;
        String m;
        String key=years+"-"+months;
        try {
            String sql = "select * from income";
            Cursor cursor = db.rawQuery(sql, new String[0]);
            while (cursor.moveToNext()) {
                String outtype = cursor.getString(cursor.getColumnIndex("itype"));
                String outtime = cursor.getString(cursor.getColumnIndex("itime"));
                Double outmoney = Double.parseDouble(cursor.getString(cursor.getColumnIndex("imoney")));
                if (outtime.contains(key)) {
                    if (outtype.equals("父母生活费")) {
                        a = a + outmoney;
                    } else if (outtype.equals("兼职工资")) {
                        b = b + outmoney;
                    } else if (outtype.equals("奖学金")) {
                        c = c + outmoney;
                    } else if (outtype.equals("理财所得")) {
                        d = d + outmoney;
                    } else if (outtype.equals("杂项收入")) {
                         e= e+ outmoney;
                    }
                }
            }
            sums.add(new Entry((float)a,0));
            sums.add(new Entry((float)b,1));
            sums.add(new Entry((float)c,2));
            sums.add(new Entry((float)d,3));
            sums.add(new Entry((float)e,4));
            PieDataSet pieDataSet=new PieDataSet(sums,"本月花费图");
            pieDataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
            PieData data=new PieData(types,pieDataSet);
            mChart.setData(data);
            mChart.animateXY(1000,1000);
            mChart.highlightValue(null);
            mChart.setCenterText("本月收入图");
            mChart.setCenterTextColor(this.getResources().getColor(R.color.lightcoral));
            mChart.setCenterTextSize(25);
            mChart.invalidate();
        }
        catch (Exception ex)
        {
            Toast.makeText(Datasort2.this,"查询出错，稍后再试",Toast.LENGTH_LONG);
        }
    }
}


