package ua.marinovskiy.moviereviewsny.models.db;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Alex on 29.02.2016.
 */
public class Multimedia extends RealmObject {

    @PrimaryKey
    private int movieReviewId;

    private Resource resources;

    public int getMovieReviewId() {
        return movieReviewId;
    }

    public void setMovieReviewId(int movieReviewId) {
        this.movieReviewId = movieReviewId;
    }

    public Resource getResources() {
        return resources;
    }

    public void setResources(Resource resources) {
        this.resources = resources;
    }
}
