package com.example.classinteract;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddComment extends AppCompatActivity {

    DatabaseReference databaseQC;
    Button addC;
    EditText commentText;
    SharedPreferences sharedPref;
    DataModel questionModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comment);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        questionModel = (DataModel) extras.getSerializable("question");
        addC = findViewById(R.id.addCommentButton);
        commentText = findViewById(R.id.editComment);
        databaseQC = FirebaseDatabase.getInstance().getReference("questions");

        addC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addComment();
            }
        });
    }


    public void addComment(){
        String question = commentText.getText().toString();
        sharedPref = this.getSharedPreferences("yayo", Context.MODE_PRIVATE);
        if(!TextUtils.isEmpty(question)){
            DatabaseReference taskRef = databaseQC.child(sharedPref.getString("QUESTION_ID", "ERROR"));
            List<CommentObj> commentlist = questionModel.getComments();
            String id = taskRef.child("comments").push().getKey();
            if(commentlist == null) {
                commentlist = new ArrayList<>();
            }
            commentlist.add(new CommentObj(id, question, 0));
            Log.d("ERROR", commentlist.get(0).getComment());
            taskRef.child("comments").setValue(commentlist);
            Toast.makeText(AddComment.this, "comment added", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(AddComment.this, "Add a comment", Toast.LENGTH_LONG).show();
        }

        finish();
    }
}

