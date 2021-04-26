package com.example.booklibraryapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewholder> {
   private Context context;
   private Activity activity;
    private ArrayList<String> book_id,book_title,book_author,book_pages;
    Animation translet_anim;
    public CustomAdapter(Activity activity,Context context,ArrayList book_id, ArrayList book_title, ArrayList book_author,
                         ArrayList book_pages){
        this.activity=activity;
        this.context=context;
        this.book_id=book_id;
        this.book_title=book_title;
        this.book_author=book_author;
        this.book_pages=book_pages;

    }
    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.myrow,parent,false);
        MyViewholder myvh=new MyViewholder(v);
        return myvh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewholder holder, final int position) {

        holder.id_txt.setText(book_id.get(position));
        holder.title_txt.setText(book_title.get(position));
        holder.author_txt.setText(book_author.get(position));
        holder.pages_txt.setText(book_pages.get(position));
        //animate recyclerview
        translet_anim= AnimationUtils.loadAnimation(context,R.anim.translete_anim);
        holder.ly.setAnimation(translet_anim);
        holder.ly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,UpgradeActivity.class);
                intent.putExtra("id",book_id.get(position));
                intent.putExtra("title",book_title.get(position));
                intent.putExtra("author",book_author.get(position));
                intent.putExtra("pages",book_pages.get(position));
                activity.startActivityForResult(intent,1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return book_id.size();
    }

    public class MyViewholder extends RecyclerView.ViewHolder {
TextView id_txt,title_txt,author_txt,pages_txt;
LinearLayout ly;
        public MyViewholder(@NonNull View iV) {
            super(iV);
            id_txt=iV.findViewById(R.id.book_id);
            title_txt=iV.findViewById(R.id.book_title);
            author_txt=iV.findViewById(R.id.book_author);
            pages_txt=iV.findViewById(R.id.book_pages);
            ly=iV.findViewById(R.id.mainlayout);

        }
    }
}
