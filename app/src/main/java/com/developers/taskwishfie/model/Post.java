package com.developers.taskwishfie.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Amanjeet Singh on 3/9/17.
 */

public class Post {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("thumbnail_image")
    @Expose
    private String thumbnailImage;
    @SerializedName("event_name")
    @Expose
    private String eventName;
    @SerializedName("event_timestamp")
    @Expose
    private Integer eventTimestamp;
    @SerializedName("views")
    @Expose
    private Integer views;
    @SerializedName("likes")
    @Expose
    private Integer likes;
    @SerializedName("shares")
    @Expose
    private Integer shares;
    @SerializedName("event_date")
    @Expose
    private Integer eventDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getThumbnailImage() {
        return thumbnailImage;
    }

    public void setThumbnailImage(String thumbnailImage) {
        this.thumbnailImage = thumbnailImage;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Integer getEventTimestamp() {
        return eventTimestamp;
    }

    public void setEventTimestamp(Integer eventTimestamp) {
        this.eventTimestamp = eventTimestamp;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getShares() {
        return shares;
    }

    public void setShares(Integer shares) {
        this.shares = shares;
    }

    public Integer getEventDate() {
        return eventDate;
    }

    public void setEventDate(Integer eventDate) {
        this.eventDate = eventDate;
    }
}
