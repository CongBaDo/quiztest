package nemo.com.androidquiz.restservice;

import android.content.Context;

import nemo.com.androidquiz.restmanager.CommonInterface;


/**
 * Created by doba on 5/25/17.
 */

public abstract class BaseService<Req, Res> {
    public abstract void request(Context context, CommonInterface.ModelResponse<Res> callBack);
    public abstract void setRequestObject(Req r);
    public abstract void cancelRequest();
}
