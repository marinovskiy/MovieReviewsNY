package ua.marinovskiy.moviereviewsny.utils;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import rx.Observable;
import ua.marinovskiy.moviereviewsny.models.db.Review;

/**
 * Created by Alex on 02.03.2016.
 */
public class RealmManager {

    public static void save(List<Review> reviewList) {
        Realm.getDefaultInstance().executeTransaction(realm -> {
            realm.copyToRealmOrUpdate(reviewList);
        });
    }

    public static Observable<RealmResults<Review>> allReviews() {
        return Realm.getDefaultInstance()
                .where(Review.class)
                .findAllAsync()
                .asObservable()
                .filter(RealmResults::isLoaded);
    }

    public static Observable<Review> getById(int id) {
        return Realm.getDefaultInstance()
                .where(Review.class)
                .equalTo(DbFields._MOVIE_ID, id)
                .findFirstAsync()
                .asObservable()
                .filter(RealmObject::isLoaded)
                .map(review -> (Review) review);
    }

}
