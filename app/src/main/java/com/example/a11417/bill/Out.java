package com.example.a11417.bill;

import  java.util.Calendar;
import android.app.DatePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Date;

public class Out extends AppCompatActivity {
    private EditText outmoney;
    private EditText outtime;
    private Spinner outtype;
    private EditText outadd;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_out);
        db=SQLiteDatabase.openOrCreateDatabase(this.getFilesDir().toString()+"my.db3",null);
        if (db!=null&&db.isOpen())
            Toast.makeText(Out.this,"数据库创建完毕",Toast.LENGTH_LONG).show();
        outmoney=(EditText)findViewById(R.id.outcome);
        outtime=(EditText)findViewById(R.id.outtime);
        outtype=(Spinner)findViewById(R.id.outtype);
        outadd=(EditText)findViewById(R.id.outadd);
        Button outclear=(Button)findViewById(R.id.outclear);
        outclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                outmoney.setText("");
                outtime.setText("");
                outadd.setText("");
            }
        });
        outtime.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_DOWN){
                    showDatePickDlg();
                    return true;
                }
                return false;
            }
        });
        final Button outaddin=(Button)findViewById(R.id.outaddin);
        outaddin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String outcometxt=outmoney.getText().toString();
                String outtimetxt=outtime.getText().toString();
                String outtypetxt=outtype.getSelectedItem().toString();
                String outaddtxt=outadd.getText().toString();
                try{
                    String insertsql="insert into outcome(omoney,otime,otype,oadd) values('"+outcometxt+"','"+outtimetxt+"','"+outtypetxt+"','"+outaddtxt+"')";
                    db.execSQL(insertsql);
                    Toast.makeText(Out.this,"支出存储成功",Toast.LENGTH_LONG).show();
                }catch (Exception ex)
                {
                    String createsql="create table outcome(_id integer primary key autoincrement,omoney text,otime text,otype text,oadd text)";
                    db.execSQL(createsql);
                    String insertsql="insert into outcome(omoney,otime,otype,oadd) values('"+outcometxt+"','"+outtimetxt+"','"+outtypetxt+"','"+outaddtxt+"')";
                    db.execSQL(insertsql);
                    Toast.makeText(Out.this,"第一次创建存储成功",Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    protected void showDatePickDlg(){
        java.util.Calendar calendar= java.util.Calendar.getInstance();
        DatePickerDialog datePickerDialog=new DatePickerDialog(Out.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                Out.this.outtime.setText(year+"-"+month+"-"+dayOfMonth);
            }
        },calendar.get(java.util.Calendar.YEAR),calendar.get(java.util.Calendar.MONTH),calendar.get(java.util.Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
}
