package com.blazingapps.asus.radiomanger;

public class Song {
    String id;
    String title;
    String moreinfo;
    String key;

    public Song(String id, String title, String moreinfo, String key) {
        this.id = id;
        this.title = title;
        this.moreinfo = moreinfo;
        this.key=key;
    }

    public String getKey() {
        return key;
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
