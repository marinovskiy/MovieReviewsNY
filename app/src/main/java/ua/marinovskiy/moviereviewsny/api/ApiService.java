package ua.marinovskiy.moviereviewsny.api;

import retrofit2.http.GET;
import rx.Observable;
import ua.marinovskiy.moviereviewsny.models.network.NetworkReviewsResponse;

/**
 * Created by Alex on 26.02.2016.
 */
public interface ApiService {

    @GET(C.ALL)
    Observable<NetworkReviewsResponse> allResponses();

}
