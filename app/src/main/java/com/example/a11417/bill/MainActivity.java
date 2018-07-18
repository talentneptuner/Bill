package com.example.a11417.bill;

import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView in=(TextView)findViewById(R.id.In);
        TextView out=(TextView)findViewById(R.id.Out);
        TextView read=(TextView)findViewById(R.id.Read);
        TextView see=(TextView)findViewById(R.id.See) ;
        in.getBackground().setAlpha(100);
        in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ComponentName comp=new ComponentName(MainActivity.this,In.class);
                Intent intent=new Intent();
                intent.setComponent(comp);
                startActivity(intent);
            }
        });
        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ComponentName comp=new ComponentName(MainActivity.this,Out.class);
                Intent intent=new Intent();
                intent.setComponent(comp);
                startActivity(intent);
            }
        });
        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ComponentName comp=new ComponentName(MainActivity.this,Datasort.class);
                Intent intent=new Intent();
                intent.setComponent(comp);
                startActivity(intent);
            }
        });

    }
}
