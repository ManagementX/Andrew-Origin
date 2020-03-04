package com.example.cardmanagementx;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class GuideView extends AppCompatActivity {
    private Button btn_login_view ;
    private Button btn_register_view ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guideview);

        btn_login_view = findViewById(R.id.btn_to_login_view);
        btn_register_view = findViewById(R.id.btn_to_register_view);

        btn_login_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GuideView.this,LoginActivity.class));
            }
        });
        btn_register_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GuideView.this,RegisterActivity.class));
            }
        });
    }
}
