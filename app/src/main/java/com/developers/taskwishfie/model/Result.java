package com.developers.taskwishfie.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Amanjeet Singh on 3/9/17.
 */

public class Result {
    @SerializedName("posts")
    @Expose
    private List<Post> posts = null;
    @SerializedName("page")
    @Expose
    private Integer page;

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }
}
