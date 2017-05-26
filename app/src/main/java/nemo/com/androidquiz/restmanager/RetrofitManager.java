package nemo.com.androidquiz.restmanager;

import android.content.Context;
import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import nemo.com.androidquiz.BuildConfig;
import nemo.com.androidquiz.utils.APIConstant;
import nemo.com.androidquiz.utils.TokenManager;
import okhttp3.Authenticator;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Route;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by doba on 5/25/17.
 */

public class RetrofitManager {

    private static final String TAG = "RetrofitManager";
    private static RetrofitManager sRetrofitManager;
    public static RestAPIEndPointInterface retrofitInterface;
    private OkHttpClient okHttp;
    private OkHttpClient.Builder builder;

    public static RetrofitManager getInstance() {
        if (sRetrofitManager == null) {
            sRetrofitManager = new RetrofitManager();
        }

        return sRetrofitManager;
    }

    public void config(Context context){
        builder = null;
        try {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            builder = new OkHttpClient.Builder();
            builder.addInterceptor(interceptor);
            okHttp = builder.build();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void authenticate(){
        Log.e(TAG, "Authorization "+TokenManager.sBearer+" "+TokenManager.sAccessToken);

        builder = null;
        try {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            builder = new OkHttpClient.Builder();
            builder.addInterceptor(interceptor);
            okHttp = builder.authenticator(new Authenticator() {
                @Override
                public Request authenticate(Route route, okhttp3.Response response) throws IOException {
                    return response.request().newBuilder()
                            .header("Authorization", TokenManager.sBearer+" "+TokenManager.sAccessToken)
                            .build();
                }
            }).addInterceptor(interceptor).build();

        }catch (Exception e){
            e.printStackTrace();
        }

        initialRetrofit(APIConstant.TEST_BASE_URL);
    }

    public RestAPIEndPointInterface getRetrofitInterface() {
        if (retrofitInterface == null) {
            retrofitInterface = initialRetrofit(APIConstant.TEST_BASE_URL);
        }

        return retrofitInterface;
    }

    public RestAPIEndPointInterface getTokenRetrofitInterface() {
        retrofitInterface = initialRetrofit(APIConstant.API_GET_TOKEN);

        return retrofitInterface;
    }

    public void resetManager(){
        retrofitInterface = null;
    }

    public RestAPIEndPointInterface initialRetrofit(String customRootUrl) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(customRootUrl)
                .addConverterFactory(GsonConverterFactory.create(gson)).client(okHttp)
                .build();

        return retrofit.create(RestAPIEndPointInterface.class);
    }

    public <T> void sendApiRequest(Call<T> call, final CommonInterface.ModelResponse<T> callBack) {
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, final Response<T> response) {
                callBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                callBack.onFail();
            }
        });
    }


}