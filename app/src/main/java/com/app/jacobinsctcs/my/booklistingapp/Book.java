package com.app.jacobinsctcs.my.booklistingapp;

/*
 * Created by Admin on 24-Jul-17.
 */

import java.util.ArrayList;

public class Book
{
    private String imageUrl;
    private  String previewLink;
    private String title;
    private String author;

    public Book()
    {

    }


    public void setAuthor(String  author) {
        this.author = author;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPreviewLink(String previewLink) {
        this.previewLink = previewLink;
    }

    public  Book(String imageUrl,String title,String  author)
    {
        this.imageUrl=imageUrl;
        this.title=title;
        this.author=author;
        this.previewLink="";
    }

    public Book(String mImageUrl, String mTitle, String mAuthor, String mPreviewLink)
    {
        imageUrl=mImageUrl;
        previewLink=mPreviewLink;
        title=mTitle;
        author=mAuthor;
    }

    public String  getAuthor()
    {
        return author;
    }

    public String getImageUrl() {

        return imageUrl;
    }

    public String getPreviewLink() {

        return previewLink;
    }

    public String getTitle()
    {

        return title;
    }


}
