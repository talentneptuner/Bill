package com.example.a11417.bill;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
//import java.util.Calendar;

public class In extends AppCompatActivity {
    private EditText time;
    private EditText money;
    private Spinner type;
    private EditText add;
    SQLiteDatabase db;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in);
        time=(EditText)findViewById(R.id.editText3);
        //创建或者打开数据库
        db=SQLiteDatabase.openOrCreateDatabase(this.getFilesDir().toString()+"my.db3",null);
        if (db!=null&&db.isOpen())
            Toast.makeText(In.this,"数据库创建完毕",Toast.LENGTH_LONG).show();
        money=(EditText)findViewById(R.id.income);
        type=(Spinner)findViewById(R.id.intype);
        add=(EditText)findViewById(R.id.add);
        Button clear=(Button)findViewById(R.id.clear);
        money.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                money.setText("");
                return false;
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                money.setText("");
                time.setText("");
                add.setText("");
            }
        });
        time.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_DOWN){
                    showDatePickDlg();
                    return true;
                }
                return false;
            }
        });
        Button addin=(Button)findViewById(R.id.addin);
        addin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (money.getText().toString().equals("请输入收入金额")||money.getText().toString().equals("")||time.getText().toString().equals("选择日期")||time.getText().toString().equals(""))
                {
                    Toast.makeText(In.this,"金额或时间输入错误",Toast.LENGTH_SHORT).show();
                    return;
                }
                try{
                String moneytxt=money.getText().toString();
                String timetxt=time.getText().toString();
                String typetxt=type.getSelectedItem().toString();
                    String addtxt=add.getText().toString();

                    try{

                        String insertsql="insert into income(imoney,itime,itype,iadd) values('"+moneytxt+"','"+timetxt+"','"+typetxt+"','"+addtxt+"')";
                        db.execSQL(insertsql);
                        Toast.makeText(In.this,"添加完毕",Toast.LENGTH_LONG).show();
                        /*String sql="select count(*) as c from Sqlite_master where type='table'and name='income'";
                        Cursor cursor=db.rawQuery(sql,null);
                        int count=cursor.getInt(0);
                        add.setText(count);*/

                    }
                    catch(SQLiteException se){
                        Toast.makeText(In.this,"新建表",Toast.LENGTH_LONG).show();
                        String createsql="create table income(_id integer primary key autoincrement,imoney text,itime text,itype text,iadd text)";
                        db.execSQL(createsql);
                        String insertsql="insert into income(imoney,itime,itype,iadd) values('"+moneytxt+"','"+timetxt+"','"+typetxt+"','"+addtxt+"')";
                        db.execSQL(insertsql);
                        Toast.makeText(In.this,"建表成功添加完毕",Toast.LENGTH_LONG).show();
                    }

                }
                catch (Exception ex){
                    Toast.makeText(In.this,"某些必要数据缺失",Toast.LENGTH_LONG).show();
                    return;
                }



            }
        });

    }

    protected void showDatePickDlg(){
        java.util.Calendar calendar= java.util.Calendar.getInstance();
        DatePickerDialog datePickerDialog=new DatePickerDialog(In.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                In.this.time.setText(year+"-"+month+"-"+dayOfMonth);
            }
        },calendar.get(java.util.Calendar.YEAR),calendar.get(java.util.Calendar.MONTH),calendar.get(java.util.Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void insertdata(SQLiteDatabase db,String money,String time,String type,String addin)
    {
        db.execSQL("insert into income values(null,?,?,?,?)",new String[]{money,time,type,addin});
        Toast.makeText(In.this,"加入完毕",Toast.LENGTH_LONG).show();
    }

        public void onDestory()
    {
        super.onDestroy();
        if (db!=null&&db.isOpen())
        {
            db.close();
        }
    }


}
