package com.example.cardmanagementx;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class BoardView extends AppCompatActivity
{

    private LinearLayout fabSubAddBoard;
    private LinearLayout fabSubAddCard;
    private FloatingActionButton fabAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_view);
        Toolbar boardViewToolbar = findViewById(R.id.board_toolbar);
        setSupportActionBar(boardViewToolbar);
        fabSubAddBoard=findViewById(R.id.add_fab_sub_board);
        fabSubAddCard=findViewById(R.id.add_fab_sub_card);
        fabAdd=findViewById(R.id.add_fab);
        fabSubAddBoard.setVisibility(View.GONE);
        fabSubAddCard.setVisibility(View.GONE);
        fabAdd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (fabSubAddBoard.getVisibility()==View.GONE&&fabSubAddCard.getVisibility()==View.GONE)
                {
                    fabSubAddBoard.setVisibility(View.VISIBLE);
                    fabSubAddCard.setVisibility(View.VISIBLE);
                    fabSubAddCard.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            Intent intent=new Intent(BoardView.this,AddCard.class);
                            startActivity(intent);
                        }
                    });
                    fabSubAddBoard.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            Intent intent=new Intent(BoardView.this,AddBoard.class);
                            startActivity(intent);
                        }
                    });
                }
                else
                {
                    fabSubAddBoard.setVisibility(View.GONE);
                    fabSubAddCard.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.boardviewtoolbar,menu);
        return super.onCreateOptionsMenu(menu);
    }
}
