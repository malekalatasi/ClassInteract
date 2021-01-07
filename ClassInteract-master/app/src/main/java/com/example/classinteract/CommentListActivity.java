package com.example.classinteract;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.ListView;

import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.List;

public class CommentListActivity extends AppCompatActivity {

    ListView listView;
    DatabaseReference databaseQC;
    List<CommentObj> commentsList = new ArrayList<>();
    SharedPreferences sharedPref;
    DataModel questionModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        listView = findViewById(R.id.listviewC);

        databaseQC = FirebaseDatabase.getInstance().getReference("questions");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        CommentAdapter adapter = new CommentAdapter(CommentListActivity.this, commentsList);
        listView.setAdapter(adapter);

        questionModel = new DataModel("FAKE ID", "FALSE QUESTION.", null, null, 0);

    }
    @Override
    protected void onStart() {
        super.onStart();
        sharedPref = this.getSharedPreferences("yayo", Context.MODE_PRIVATE);
        databaseQC.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                commentsList = databaseQC.child(sharedPref.getString("QUESTION_ID", "ERROR")).;
                for(DataSnapshot qSnapshot : dataSnapshot.getChildren()){
                    DataModel dataModel = qSnapshot.getValue(DataModel.class);
                    Log.d("ERROR", "LOOKING FOR: "
                            + sharedPref.getString("QUESTION_ID", "ERROR")
                            + " vs "
                            + dataModel.getQuestion());
                    if(dataModel.getQuestionId().equals(sharedPref.getString("QUESTION_ID", "ERROR"))) {
                        questionModel = dataModel;
                        Log.d("ERROR", "FOUND QUESTION: " + dataModel.getQuestion());
                        commentsList = dataModel.getComments();
                    }
                }
                if(commentsList == null) {
                    commentsList = new ArrayList<>();
                }
                CommentAdapter adapter = new CommentAdapter(CommentListActivity.this, commentsList);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CommentListActivity.this, AddComment.class);
                Log.d("ERROR", "Question Model: " + questionModel.getQuestion());
                intent.putExtra("question", questionModel);
                startActivity(intent);
            }
        });
    }

}

