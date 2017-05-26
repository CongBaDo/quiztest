package nemo.com.androidquiz.model.token;

import com.google.gson.annotations.SerializedName;

/**
 * Created by doba on 5/26/17.
 */

public class TokenResObj {

    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("token_type")
    private String tokenType;


    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }
}
