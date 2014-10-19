package com.example.joel.webapitest;

/**
 * Created by Joel on 10/15/2014.
 */
public class MediaObject {
    String Title;
    String Author;
    String ISBN10;
    String ThumbnailURL;

    public MediaObject(String Title, String Author, String ThumbnailUrl)
    {
        this.Title = Title;
        this.Author = Author;
        this.ThumbnailURL = ThumbnailUrl;
    }

    public void SetTitle(String Title)
    {
        this.Title =Title;
    }

    public void SetAuthor(String Author)
    {
        this.Author = Author;
    }
    public void  SetISBN10(String ISBN10)
    {
        this.ISBN10 = ISBN10;
    }

    public void SetThumbnailURL(String ThumbnailURL )
    {
        this.ThumbnailURL = ThumbnailURL;
    }
}

