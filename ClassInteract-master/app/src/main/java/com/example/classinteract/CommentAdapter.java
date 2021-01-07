package com.example.classinteract;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class CommentAdapter extends ArrayAdapter<CommentObj> {
    private List<CommentObj> dataSet;
//    Context mContext;

    private Activity context;
    DatabaseReference databaseQC;

    public CommentAdapter(Activity context, List<CommentObj> dataSet){
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

        final CommentObj dataModel = dataSet.get(position);
        upVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPref = context.getSharedPreferences("yayo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                if(sharedPref.contains(dataModel.getCommentId())) {
//                    dataModel.downVote();
//                    editor.remove(dataModel.getCommentId()).commit();
//                    upVote.setText("Upvote");
                } else {
                    dataModel.upVote();
                    editor.putString(dataModel.getCommentId(), "").commit();
//                    upVote.setText("Remove vote");
                }
                databaseQC.child(sharedPref.getString("QUESTION_ID", "ERROR"))
                        .child("comments")
                        .child(Integer.toString(position))
                        .child("votes")
                        .setValue(dataModel.getVotes());
                Log.d("ERROR", "NEW VOTE: " + dataModel.getVotes());
            }
        });

        textQ.setText("Votes: " + dataModel.getVotes() + "   " + dataModel.getComment());
        //textQ.setText(dataModel.getComment());
        //textC.setText(dataModel.getComment());

        return listViewItem;
    }
}
