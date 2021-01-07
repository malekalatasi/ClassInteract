package com.example.classinteract;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

//implements View.OnClickListener

public class CustomAdapter extends ArrayAdapter<DataModel>{
    private List<DataModel> dataSet;
//    Context mContext;

    private Activity context;
    DatabaseReference databaseQC;

    public CustomAdapter(Activity context, List<DataModel> dataSet){
        super(context, R.layout.row_item, dataSet);
        this.context = context;
        this.dataSet = dataSet;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();

        final View listViewItem = inflater.inflate(R.layout.row_item, null, true);
        final Button upVote = listViewItem.findViewById(R.id.upvoteButton);

        TextView textQ = listViewItem.findViewById(R.id.questionText);
        databaseQC = FirebaseDatabase.getInstance().getReference("questions");

        upVote.setText("Upvote");

        final DataModel dataModel = dataSet.get(position);
        upVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPref = context.getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                if(sharedPref.contains(dataModel.getQuestionId())) {
//                    dataModel.downVote();
//                    editor.remove(dataModel.getQuestionId()).commit();
//                    upVote.setText("Upvote");
                }
                else {
                    dataModel.upVote();
                    editor.putString(dataModel.getQuestionId(), "").commit();
//                    upVote.setText("Remove vote");
                }
                if(dataModel.getQuestionId() == null) {
                    Log.d("ERROR", dataModel.getQuestion());
                    databaseQC.child(dataModel.getQuestion()).removeValue();
                    String id = databaseQC.child("questions").push().getKey();
                    DataModel newDataModel = new DataModel(
                            id,
                            dataModel.getQuestion(),
                            dataModel.getCommentId(),
                            dataModel.getComment(),
                            dataModel.getVotes());
                    databaseQC.child(id).setValue(newDataModel);
                } else {
                    databaseQC.child(dataModel.getQuestionId()).setValue(dataModel);
                }
                Log.d("ERROR", "NEW VOTE: " + dataModel.getVotes());
            }
        });

        textQ.setText("Votes: " + dataModel.getVotes() + "   " + dataModel.getQuestion());
        //textQ.setText(dataModel.getComment());
        //textC.setText(dataModel.getComment());

        TextView question = listViewItem.findViewById(R.id.questionText);
        question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPref = context.getSharedPreferences("yayo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                Log.d("ERROR", dataModel.getQuestionId());
                editor.putString("QUESTION_ID", dataModel.getQuestionId()).commit();
                Intent intent = new Intent(context, CommentListActivity.class);
                context.startActivity(intent);
            }
        });

        return listViewItem;
    }
}
