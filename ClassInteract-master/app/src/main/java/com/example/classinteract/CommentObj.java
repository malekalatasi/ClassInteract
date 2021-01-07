package com.example.classinteract;

import org.w3c.dom.Comment;

import java.io.Serializable;

public class CommentObj implements Serializable {
    String commentId;
    String comment;
    int votes;

    public CommentObj() {

    }

    public CommentObj(String commentId, String comment, int votes){
        this.commentId = commentId;
        this.comment = comment;
        this.votes = votes;
    }

    public String getCommentId(){
        return commentId;
    }

    public String getComment(){
        return comment;
    }

    public int getVotes(){
        return votes;
    }

    public void upVote(){
        ++votes;
    }

    public void downVote(){
        --votes;
    }
}
