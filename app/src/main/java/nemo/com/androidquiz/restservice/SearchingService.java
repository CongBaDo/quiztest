package nemo.com.androidquiz.restservice;

import android.content.Context;

import nemo.com.androidquiz.model.seach.SearchingReqObj;
import nemo.com.androidquiz.model.seach.SearchingResObj;
import nemo.com.androidquiz.restmanager.CommonInterface;
import nemo.com.androidquiz.restmanager.RetrofitManager;

/**
 * Created by doba on 5/26/17.
 */

public class SearchingService extends BaseService<SearchingReqObj, SearchingResObj> {

    private SearchingReqObj searchingReqObj;

    @Override
    public void request(Context context, CommonInterface.ModelResponse callBack) {
        RetrofitManager.getInstance().sendApiRequest(RetrofitManager.getInstance().getRetrofitInterface().search(searchingReqObj.getTerm(), searchingReqObj.getLatitude(), searchingReqObj.getLongitude(), searchingReqObj.getLimit(), searchingReqObj.getRadius()), callBack);
    }

    @Override
    public void setRequestObject(SearchingReqObj r) {
        this.searchingReqObj = r;
    }

    @Override
    public void cancelRequest() {
    }
}
