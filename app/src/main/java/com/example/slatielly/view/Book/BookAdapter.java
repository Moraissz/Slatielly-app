package com.example.slatielly.view.Book;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.slatielly.Model.Book;
import com.example.slatielly.R;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookViewHolder> {

    private ArrayList<Book> bookArrayList;
    private BookListener listener;

    public BookAdapter(ArrayList<Book> bookArrayList, BookListener listener) {
        this.bookArrayList = bookArrayList;
        this.listener = listener;
    }

    public interface BookListener{
        void onClickBookListener(Book book);
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.book_card_viewholder,viewGroup,false);
        BookViewHolder bookViewHolder = new BookViewHolder(view,this.listener);
        return bookViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder bookViewHolder, int i) {
        bookViewHolder.bind(bookArrayList.get(i));
    }

    @Override
    public int getItemCount() {
        return bookArrayList.size();
    }
}
