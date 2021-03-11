package br.com.evolutil.zeplayer.model;

import android.graphics.Bitmap;

/**
 * Created by Jez on 02/11/2016.
 * Class to contains data about music file.
 */

public class MusicMetaData {
    private Bitmap album;
    private String title;
    private String artist;
    private String textduration;
    private String filepath;

    public MusicMetaData() {
    }

    public MusicMetaData(Bitmap album, String title, String artist, String textduration, String filepath) {
        this.album = album;
        this.title = title;
        this.artist = artist;
        this.textduration = textduration;
        this.filepath = filepath;
    }

    public Bitmap getAlbum() {
        return album;
    }

    public void setAlbum(Bitmap album) {
        this.album = album;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getTextduration() {
        return textduration;
    }

    public void setTextduration(String textduration) {
        this.textduration = textduration;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }
}
