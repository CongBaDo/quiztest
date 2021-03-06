package nemo.com.androidquiz.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.util.Base64;
import android.util.Log;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import nemo.com.androidquiz.R;
import nemo.com.androidquiz.model.token.TokenReqObj;
import nemo.com.androidquiz.model.token.TokenResObj;
import nemo.com.androidquiz.restmanager.CommonInterface;
import nemo.com.androidquiz.restmanager.RetrofitManager;
import nemo.com.androidquiz.restservice.TokenService;
import nemo.com.androidquiz.utils.TokenManager;

/**
 * Created by bado on 5/25/17.
 */


public class SplashActivity extends BaseActivity {

    private static final String TAG = "SplashActivity";

    @Override
    public int getResourceLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "onCreate " + SplashActivity.class.getName());
        onNewIntent(getIntent());
        RetrofitManager.getInstance().config();
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    getApplicationContext().getPackageName(),
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        checkPermission();
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    private void checkPermission(){
        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION};

        if(!hasPermissions(this, PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }else{
            Log.e(TAG, "checkPermission "+1);
            loadMainView();
        }
    }

    private void loadMainView(){
        getToken();
    }

    private void getToken(){

        TokenReqObj tokenReqObj = new TokenReqObj();
        tokenReqObj.setClientId(TokenManager.CLIENT_ID);
        tokenReqObj.setClientSecret(TokenManager.SECRECT_KEY);
        tokenReqObj.setGrantType("code");

        TokenService tokenService = new TokenService();
        tokenService.setRequestObject(tokenReqObj);
        tokenService.request(this, new CommonInterface.ModelResponse<TokenResObj>() {
            @Override
            public void onSuccess(TokenResObj result) {

                Log.e(TAG, "onSucess "+result.getAccessToken());
                TokenManager.sAccessToken = result.getAccessToken();
                TokenManager.sBearer = result.getTokenType();
                RetrofitManager.getInstance().resetManager();
                RetrofitManager.getInstance().authenticate();
                startActivity(new Intent(SplashActivity.this, MapsActivity.class));
                finish();
            }

            @Override
            public void onFail() {

            }
        });
    }

}