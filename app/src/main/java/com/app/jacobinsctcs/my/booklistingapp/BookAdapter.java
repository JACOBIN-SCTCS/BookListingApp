package com.app.jacobinsctcs.my.booklistingapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/*
 * Created by Admin on 25-Jul-17.
 */



public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder>
{

    private ArrayList<Book> bookList;
    private Context context;
    private OnItemClick click;



    public interface OnItemClick
    {
        void itemClick(Intent url_intent);
    }


    public BookAdapter(ArrayList<Book> bookList,Context context,OnItemClick click)
    {
        this.bookList=bookList;
        this.context=context;
        this.click=click;

    }


    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.book_list_item,parent,false);
        BookViewHolder bookViewHolder=new BookViewHolder(view);
        return bookViewHolder;

    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, int position)
    {

        holder.author.setText(bookList.get(position).getAuthor());
        holder.title.setText(bookList.get(position).getTitle());
        if(bookList.get(position).getImageUrl().equals("NULL"))
        {
            Picasso.with(context).load(R.drawable.no_image).into(holder.imageView);
        }
        else
        {
            Picasso.with(context).load(bookList.get(position).getImageUrl())
                    .into(holder.imageView);
        }



    }

    @Override
    public int getItemCount() {

        if(bookList==null)
        {
            return 0;
        }
        else
        {
            return bookList.size();
        }

    }




    public class BookViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        private ImageView imageView;
        private TextView title;
        private TextView author;

        public BookViewHolder(View itemView) {

            super(itemView);


            imageView = (ImageView) itemView.findViewById(R.id.book_image);
            title = (TextView) itemView.findViewById(R.id.book_title);
            author = (TextView) itemView.findViewById(R.id.book_author);
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View view)
        {
            int position=getAdapterPosition();
            Book current_book=bookList.get(position);
            Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(current_book.getPreviewLink()));
            click.itemClick(intent);

        }
    }


    public void clear()
    {
        bookList.clear();
        notifyDataSetChanged();
    }

    public void add_new(ArrayList<Book> data)
    {
        bookList.addAll(data);
        notifyDataSetChanged();
    }



}
