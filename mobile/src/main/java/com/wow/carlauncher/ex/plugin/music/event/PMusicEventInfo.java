package com.wow.carlauncher.ex.plugin.music.event;

/**
 * Created by 10124 on 2018/4/22.
 */

public class PMusicEventInfo {
    private String title;
    private String artist;
    private boolean haveLrc = false;

    public boolean isHaveLrc() {
        return haveLrc;
    }

    public PMusicEventInfo setHaveLrc(boolean haveLrc) {
        this.haveLrc = haveLrc;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public PMusicEventInfo setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getArtist() {
        return artist;
    }

    public PMusicEventInfo setArtist(String artist) {
        this.artist = artist;
        return this;
    }

    public PMusicEventInfo() {
    }

    public PMusicEventInfo(String title, String artist) {
        this.title = title;
        this.artist = artist;
    }

    @Override
    public String toString() {
        return "PMusicEventInfo{" +
                "title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                '}';
    }
}
