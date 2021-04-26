package com.example.booklibraryapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
         RecyclerView recyclerView;
         FloatingActionButton add_button;
    ImageView im;
         MyDatabaseHelper mydb;
         ArrayList<String> book_id,book_title,book_author,book_pages;
        CustomAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        im=findViewById(R.id.empty);
        recyclerView =findViewById(R.id.myRecycler);
        add_button=findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,AddActivity.class);
             startActivityForResult(i,2);
            }
        });

        mydb=new MyDatabaseHelper(MainActivity.this);
        book_id=new ArrayList<>();
        book_author=new ArrayList<>();
        book_title=new ArrayList<>();
        book_pages=new ArrayList<>();
        StoredatainArrays();

         adapter=new CustomAdapter(MainActivity.this,MainActivity.this,book_id,book_title,book_author,book_pages);
         recyclerView.setAdapter(adapter);
         recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1)
            recreate();
        if(requestCode==2)
            recreate();
    }

    void StoredatainArrays(){
        Cursor cursor=mydb.ReadAlldata();
        if(cursor.getCount()==0) {
        im.setVisibility(View.VISIBLE);
        }
        else{
            while(cursor.moveToNext()) {
                book_id.add(cursor.getString(0));
                book_title.add(cursor.getString(1));
                book_author.add(cursor.getString(2));
                book_pages.add(cursor.getString(3));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.mymenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.delete_all){
           ConfirmdeleteAll();
            }
        return super.onOptionsItemSelected(item);

    }
    void ConfirmdeleteAll(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Delete All ?") ;
        builder.setMessage("Are you sure you want to delete All ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyDatabaseHelper mydb=new MyDatabaseHelper(MainActivity.this);
                mydb.DeleteAll();
                Toast.makeText(MainActivity.this,"All Deleted Successfully",Toast.LENGTH_SHORT).show();
                //Refresh the activity
                Intent i=new Intent(MainActivity.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }
}
