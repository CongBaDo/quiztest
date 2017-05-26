package nemo.com.androidquiz.restservice;

import android.content.Context;

import nemo.com.androidquiz.model.token.TokenReqObj;
import nemo.com.androidquiz.model.token.TokenResObj;
import nemo.com.androidquiz.restmanager.CommonInterface;
import nemo.com.androidquiz.restmanager.RetrofitManager;

/**
 * Created by doba on 5/26/17.
 */

public class TokenService extends BaseService<TokenReqObj, TokenResObj> {

    private TokenReqObj tokenReqObj;

    @Override
    public void request(Context context, CommonInterface.ModelResponse<TokenResObj> callBack) {
        RetrofitManager.getInstance().sendApiRequest(RetrofitManager.getInstance().getTokenRetrofitInterface().getToken(tokenReqObj.getGrantType(), tokenReqObj.getClientId(), tokenReqObj.getClientSecret()), callBack);
    }

    @Override
    public void setRequestObject(TokenReqObj r) {
        this.tokenReqObj = r;
    }

    @Override
    public void cancelRequest() {

    }
}
