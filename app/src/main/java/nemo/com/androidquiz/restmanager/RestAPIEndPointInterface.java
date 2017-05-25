package nemo.com.androidquiz.restmanager;

import nemo.com.androidquiz.model.seach.SearchingReqObj;
import nemo.com.androidquiz.model.seach.SearchingResObj;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by bado on 5/25/17.
 */

public interface RestAPIEndPointInterface {

    @POST("businesses/search")
    Call<SearchingResObj> search(@Body SearchingReqObj loginRequest);
//
