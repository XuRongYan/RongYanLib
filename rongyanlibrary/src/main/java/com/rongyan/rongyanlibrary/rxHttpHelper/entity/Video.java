package com.rongyan.rongyanlibrary.rxHttpHelper.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.rongyan.rongyanlibrary.base.BaseEntity;

/**
 * Created by XRY on 2017/4/24.
 */

public class Video extends BaseEntity implements Parcelable {
    private int videoid;
    private String filename;
    private String videodirectory;
    private String title;
    private String titlenew;
    private String description;
    private String type;
    private String catagory;
    private String labels;
    private int playtimes;
    private long timelength;
    private String videoDate;
    private int isFirstPage;
    private String videoURL;
    private String imageURL;
    private String numberOfVideoplay;
    private int belongtovideoid;
    private int TVplayindex;

    public Video() {
    }

    public Video(int videoid, String title, String titlenew, long timelength, String videoURL, String imageURL) {
        this.videoid = videoid;
        this.title = title;
        this.titlenew = titlenew;
        this.timelength = timelength;
        this.videoURL = videoURL;
        this.imageURL = imageURL;
    }

    public Video(int videoid, String filename, String videodirectory, String title, String titlenew, String description, String type, String catagory, String labels, int playtimes, long timelength, String videoDate, int isFirstPage, String videoURL, String imageURL, String numberOfVideoplay, int belongtovideoid, int TVplayindex) {
        this.videoid = videoid;
        this.filename = filename;
        this.videodirectory = videodirectory;
        this.title = title;
        this.titlenew = titlenew;
        this.description = description;
        this.type = type;
        this.catagory = catagory;
        this.labels = labels;
        this.playtimes = playtimes;
        this.timelength = timelength;
        this.videoDate = videoDate;
        this.isFirstPage = isFirstPage;
        this.videoURL = videoURL;
        this.imageURL = imageURL;
        this.numberOfVideoplay = numberOfVideoplay;
        this.belongtovideoid = belongtovideoid;
        this.TVplayindex = TVplayindex;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getVideodirectory() {
        return videodirectory;
    }

    public void setVideodirectory(String videodirectory) {
        this.videodirectory = videodirectory;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitlenew() {
        return titlenew;
    }

    public void setTitlenew(String titlenew) {
        this.titlenew = titlenew;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCatagory() {
        return catagory;
    }

    public void setCatagory(String catagory) {
        this.catagory = catagory;
    }

    public String getLabels() {
        return labels;
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }

    public int getPlaytimes() {
        return playtimes;
    }

    public void setPlaytimes(int playtimes) {
        this.playtimes = playtimes;
    }

    public long getTimelength() {
        return timelength;
    }

    public void setTimelength(long timelength) {
        this.timelength = timelength;
    }

    public String getVideoDate() {
        return videoDate;
    }

    public void setVideoDate(String videoDate) {
        this.videoDate = videoDate;
    }

    public int getIsFirstPage() {
        return isFirstPage;
    }

    public void setIsFirstPage(int isFirstPage) {
        this.isFirstPage = isFirstPage;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public int getVideoid() {
        return videoid;
    }

    public void setVideoid(int videoid) {
        this.videoid = videoid;
    }

    public String getNumberOfVideoplay() {
        return numberOfVideoplay;
    }

    public void setNumberOfVideoplay(String numberOfVideoplay) {
        this.numberOfVideoplay = numberOfVideoplay;
    }

    public int getBelongtovideoid() {
        return belongtovideoid;
    }

    public void setBelongtovideoid(int belongtovideoid) {
        this.belongtovideoid = belongtovideoid;
    }

    public int getTVplayindex() {
        return TVplayindex;
    }

    public void setTVplayindex(int TVplayindex) {
        this.TVplayindex = TVplayindex;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(videoid);
        dest.writeString(filename);
        dest.writeString(videodirectory);
        dest.writeString(title);
        dest.writeString(titlenew);
        dest.writeString(description);
        dest.writeString(type);
        dest.writeString(catagory);
        dest.writeString(labels);
        dest.writeInt(playtimes);
        dest.writeLong(timelength);
        dest.writeString(videoDate);
        dest.writeInt(isFirstPage);
        dest.writeString(videoURL);
        dest.writeString(imageURL);
        dest.writeString(numberOfVideoplay);
        dest.writeInt(belongtovideoid);
        dest.writeInt(TVplayindex);
    }

    public static final Parcelable.Creator<Video> CREATOR = new Creator<Video>() {
        @Override
        public Video[] newArray(int size) {
            return new Video[size];
        }

        @Override
        public Video createFromParcel(Parcel in) {
            return new Video(in);
        }
    };

    public Video(Parcel in) {
        videoid = in.readInt();
        filename = in.readString();
        videodirectory = in.readString();
        title = in.readString();
        titlenew = in.readString();
        description = in.readString();
        type = in.readString();
        catagory = in.readString();
        labels = in.readString();
        playtimes = in.readInt();
        timelength = in.readLong();
        videoDate = in.readString();
        isFirstPage = in.readInt();
        videoURL = in.readString();
        imageURL = in.readString();
        numberOfVideoplay = in.readString();
        belongtovideoid = in.readInt();
        TVplayindex = in.readInt();
    }
}
