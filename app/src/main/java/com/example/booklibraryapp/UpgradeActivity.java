package com.example.booklibraryapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class UpgradeActivity extends AppCompatActivity {
    private EditText title_input,author_input,pages_input;
    private Button update_button,delete_button;
    private String id,title,author,pages;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrade);
        title_input=findViewById(R.id.title2);
        author_input=findViewById(R.id.author2);
        pages_input=findViewById(R.id.numberofpages2);
        GetandSetintentdata();

                    ActionBar ab=getSupportActionBar();
                        if(ab!=null)
                         ab.setTitle(title);
        delete_button=findViewById(R.id.delete);
        update_button=findViewById(R.id.update);
       update_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
            MyDatabaseHelper mydb=new MyDatabaseHelper(UpgradeActivity.this);
            mydb.Updatedata(id,title_input.getText().toString(),author_input.getText().toString(),
                    Integer.valueOf(pages_input.getText().toString()));
                }
                });

       delete_button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
            Confirmdelete();
           }
       });



    }

    public void GetandSetintentdata()
    {//Geting data
     id=getIntent().getStringExtra("id");
     title=getIntent().getStringExtra("title");
     author=getIntent().getStringExtra("author");
     pages=getIntent().getStringExtra("pages");
     //Setting data
        title_input.setText(title);
        author_input.setText(author);
        pages_input.setText(pages);
    }
    void Confirmdelete(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Delete "+title+" ?") ;
        builder.setMessage("Are you sure you want to delete "+title+" ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyDatabaseHelper mydb=new MyDatabaseHelper(UpgradeActivity.this);
                mydb.deleteOnerow(id);
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