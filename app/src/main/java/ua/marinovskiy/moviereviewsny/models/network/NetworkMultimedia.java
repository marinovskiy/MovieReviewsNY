package ua.marinovskiy.moviereviewsny.models.network;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Alex on 26.02.2016.
 */
public class NetworkMultimedia {

    private int movieReviewId;

    @SerializedName("resource")
    private NetworkResource resources;

    public NetworkResource getResources() {
        return resources;
    }

    public void setResources(NetworkResource resources) {
        this.resources = resources;
    }

    public int getMovieReviewId() {
        return movieReviewId;
    }

    public void setMovieReviewId(int movieReviewId) {
        this.movieReviewId = movieReviewId;
    }
}
