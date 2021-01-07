package com.example.classinteract;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddQuestion extends AppCompatActivity {

    DatabaseReference databaseQC;
    Button addQ;
    EditText questionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);

        addQ = findViewById(R.id.addQButton);
        questionText = findViewById(R.id.editQuestion);
        databaseQC = FirebaseDatabase.getInstance().getReference("questions");

        addQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addQuestion();
            }
        });
    }


    public void addQuestion(){
        String question = questionText.getText().toString();

        if(!TextUtils.isEmpty(question)){
            String id = databaseQC.child("comments").push().getKey();
            //String id = databaseQC.push().getKey();
            Log.d(id, "ID: ");

            DataModel dataModel = new DataModel(id, question, null, null);
            databaseQC.child(id).setValue(dataModel);

            Toast.makeText(AddQuestion.this, "question added", Toast.LENGTH_LONG).show();
            Log.d(question, "addQuestion: ");
        }
        else{
            Toast.makeText(AddQuestion.this, "Add a question", Toast.LENGTH_LONG).show();
        }

        finish();
    }
}
