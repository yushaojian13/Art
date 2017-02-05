package com.ysj.art;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.ysj.log.L;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA = "extra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        String extra = String.valueOf(getIntent().getExtras().getInt(EXTRA));
        L.d(extra);

        TextView idTV = (TextView) findViewById(R.id.idTV);
        idTV.setText(extra);
    }
}
