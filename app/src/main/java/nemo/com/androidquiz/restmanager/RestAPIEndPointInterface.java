package nemo.com.androidquiz.restmanager;

import nemo.com.androidquiz.model.seach.SearchingReqObj;
import nemo.com.androidquiz.model.seach.SearchingResObj;
import nemo.com.androidquiz.model.token.TokenReqObj;
import nemo.com.androidquiz.model.token.TokenResObj;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by bado on 5/25/17.
 */

public interface RestAPIEndPointInterface {


    @GET("businesses/search")
    Call<SearchingResObj> search(@Query("term")String term, @Query("latitude")double lat, @Query("longitude")double lng,
                                 @Query("limit")int limit, @Query("radius")int radius,
                                 @Query("location")String location);

    @FormUrlEncoded
    @POST("token")
    Call<TokenResObj> getToken(
            @Field("grant_type") String grantType,
            @Field("client_id") String clientId,
            @Field("client_secret") String clientSecret
    );
}
