package com.example.contact;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class SecondActivity extends AppCompatActivity {
    private TextView sName,sNumber;
    private ImageView sImage;
    private Contact mContact;;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);
        sName=(TextView) findViewById(R.id.sname);
        sNumber=(TextView) findViewById(R.id.snumber);
        sImage=(ImageView) findViewById(R.id.simage);
        mContact=(Contact) getIntent().getSerializableExtra("serial");
        sName.setText(mContact.getName());
        sNumber.setText(mContact.getNumber());
        Glide.with(getApplicationContext()).load(mContact.getImage()).apply(RequestOptions.circleCropTransform()).into(sImage);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Intent i=new Intent(SecondActivity.this,MainActivity.class);
    }
}
