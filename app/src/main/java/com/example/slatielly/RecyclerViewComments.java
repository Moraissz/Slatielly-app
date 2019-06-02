package com.example.slatielly;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.slatielly.view.comment.AdapterComment;

public class RecyclerViewComments extends AppCompatActivity
{
    private RecyclerView RecyclerView_recyclerview_comments;
    private AdapterComment adapterComment;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_comments);

        RecyclerView_recyclerview_comments= (RecyclerView) findViewById(R.id.RecyclerView_recyclerview_comments);
        adapterComment = new AdapterComment();
        RecyclerView_recyclerview_comments.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView_recyclerview_comments.setHasFixedSize(true);
        RecyclerView_recyclerview_comments.setAdapter(adapterComment);
    }

    public void Comment(View view)
    {

    }
}
