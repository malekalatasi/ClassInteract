package com.example.classinteract;

import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class QuestionsListActivity extends AppCompatActivity {

    ListView listView;
    DatabaseReference databaseQC;
    List<DataModel> questionsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions_list);

        listView = findViewById(R.id.listviewQ);

        databaseQC = FirebaseDatabase.getInstance().getReference("questions");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(QuestionsListActivity.this, AddQuestion.class);
                startActivity(intent);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        CustomAdapter adapter = new CustomAdapter(QuestionsListActivity.this, questionsList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                DataModel dataModel= questionsList.get(position);
                Intent intent = new Intent(QuestionsListActivity.this, CommentListActivity.class);

                startActivity(intent);
            }
        });



    }
    @Override
    protected void onStart() {
        super.onStart();

        databaseQC.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                questionsList.clear();
                for(DataSnapshot qSnapshot : dataSnapshot.getChildren()){
                    DataModel dataModel = qSnapshot.getValue(DataModel.class);

                    questionsList.add(dataModel);
                    Log.d(questionsList.toString(), "question list: ");
                }


                CustomAdapter adapter = new CustomAdapter(QuestionsListActivity.this, questionsList);
                listView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
