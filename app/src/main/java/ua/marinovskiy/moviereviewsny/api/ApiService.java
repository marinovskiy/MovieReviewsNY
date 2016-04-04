package ua.marinovskiy.moviereviewsny.api;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import ua.marinovskiy.moviereviewsny.models.network.NetworkReviewsResponse;

public interface ApiService {

    @GET(C.ALL)
    Observable<NetworkReviewsResponse> allResponses(@Query("offset") int offset);

}
