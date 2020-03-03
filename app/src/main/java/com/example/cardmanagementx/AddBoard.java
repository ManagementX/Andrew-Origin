package com.example.cardmanagementx;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AddBoard extends AppCompatActivity
{

    private TextView cancel;
    private TextView finish;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_board);
        cancel=findViewById(R.id.add_board_cancel);
        finish=findViewById(R.id.add_board_finish);
        cancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
    }

}
