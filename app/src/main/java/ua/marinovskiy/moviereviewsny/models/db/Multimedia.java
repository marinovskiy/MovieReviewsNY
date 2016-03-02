package ua.marinovskiy.moviereviewsny.models.db;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Alex on 29.02.2016.
 */
public class Multimedia extends RealmObject {

    @PrimaryKey
    private int movieReviewId;

    private String src;

    public int getMovieReviewId() {
        return movieReviewId;
    }

    public void setMovieReviewId(int movieReviewId) {
        this.movieReviewId = movieReviewId;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }
}
