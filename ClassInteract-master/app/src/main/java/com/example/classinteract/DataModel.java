package com.example.classinteract;

import android.provider.ContactsContract;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DataModel implements Serializable {
    String questionId;
    String question;
    String commentId;
    String comment;
    int votes;
    List<CommentObj> comments;

    //String comment;

    public DataModel()
    {

    }

    public DataModel(String questionId, String question, String commentId, String comment){
        this.question = question;
        this.questionId = questionId;
        this.comment = comment;
        this.commentId = commentId;
        votes = 0;
        comments = new ArrayList<>();
    }

    public DataModel(String questionId, String question, String commentId, String comment, int votes){
        this.question = question;
        this.questionId = questionId;
        this.comment = comment;
        this.commentId = commentId;
        this.votes = votes;
        comments = new ArrayList<>();
    }

    public String getQuestion(){
        return question;
    }

    public String getQuestionId() { return questionId; }

    public String getComment(){
        return comment;
    }

    public List<CommentObj> getComments() { return comments; }

    public String getCommentId(){
        return commentId;
    }

    public void upVote() { ++votes; }

    public void downVote() { --votes; }

    public int getVotes() { return votes; }

}