package com.blazingapps.asus.radiomanger;

public class Song {
    String id;
    String title;
    String moreinfo;

    public Song(String id, String title, String moreinfo) {
        this.id = id;
        this.title = title;
        this.moreinfo = moreinfo;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getMoreinfo() {
        return moreinfo;
    }
}
