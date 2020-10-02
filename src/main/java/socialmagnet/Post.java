package main.java.socialmagnet;

import java.util.*;

public class Post {
    private int postID;
    private String author;
    private String message;
    private Date timestamp;
    private String postedOn;
    private ArrayList<Member> taggedMembers;

    public Post (int postID, String author, String message, Date timestamp) {
        this.postID = postID;
        this.author = author;
        this.message = message;
        this.timestamp = timestamp;
    }

    public Post (String author, String message, Date timestamp, ArrayList<Member> tagged) {
        this.author = author;
        this.message = message;
        this.timestamp = timestamp;
        this.taggedMembers = tagged;
    }

    public Post (int postID, String author, String message, String postedOn, Date timestamp) {
        this.postID = postID;
        this.author = author;
        this.message = message;
        this.postedOn = postedOn;
        this.timestamp = timestamp;
    }

    public int getPostID() {
        return this.postID;
    }

    public String getAuthor() {
        return this.author;
    }

    public String getMessage() {
        return this.message;
    }

    public String getPostedOn() {
        return this.postedOn;
    }

    public Date timestamp() {
        return this.timestamp;
    }

    public ArrayList<Member> getTagged() {
        return this.taggedMembers;
    }
}