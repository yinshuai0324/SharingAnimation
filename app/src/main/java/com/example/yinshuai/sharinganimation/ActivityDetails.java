package com.example.yinshuai.sharinganimation;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class ActivityDetails extends Activity {
    ImageView imageView;
    TextView name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_details);
        initView();
    }

    public void initView(){
        imageView= (ImageView) findViewById(R.id.image);
        name= (TextView) findViewById(R.id.name);

        Intent intent=getIntent();
        Glide.with(this).load(intent.getStringExtra("url")).into(imageView);
        name.setText(intent.getStringExtra("name"));
    }
}
