package com.example.HealthTrack;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import com.example.project.R;

public class AboutActivity extends AppCompatActivity {

    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        text=findViewById(R.id.text_view);
        text.setMovementMethod(LinkMovementMethod.getInstance());
        text.setHighlightColor(Color.TRANSPARENT);
        text.setLinkTextColor(getResources().getColor(R.color.design_default_color_primary_dark));
    }
}