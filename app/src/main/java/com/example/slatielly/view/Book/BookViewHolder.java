package com.example.slatielly.view.Book;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.slatielly.Model.Book;
import com.example.slatielly.Model.Dress;
import com.example.slatielly.R;

public class BookViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView description;
    private TextView hirerName;
    private TextView status;
    private Book book;
    private BookAdapter.BookListener listener;

    public BookViewHolder(@NonNull View itemView, BookAdapter.BookListener listener) {
        super(itemView);
        this.description = (TextView) itemView.findViewById(R.id.bookDressDescription);
        this.hirerName = (TextView) itemView.findViewById(R.id.hirerName);
        this.status = (TextView) itemView.findViewById(R.id.bookStatus);
        itemView.setOnClickListener(this);
        this.listener = listener;
    }


    public void bind(Book book){
        this.description.setText(book.getDress().getDescription());
        this.hirerName.setText(book.getPerson().getName());
        this.status.setText(book.getStatus());
        if(book.getStatus().equals("Pendente"))
            this.status.setTextColor(Color.parseColor("#f4d742"));
        else
            this.status.setTextColor(Color.parseColor("#1fe056"));
        this.book = book;
    }

    @Override
    public void onClick(View v) {
        listener.onClickBookListener(this.book);

    }
}
