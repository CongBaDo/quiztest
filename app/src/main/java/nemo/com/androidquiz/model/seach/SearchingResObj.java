package nemo.com.androidquiz.model.seach;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import nemo.com.androidquiz.model.BaseResponse;
import nemo.com.androidquiz.model.BussinessItem;

/**
 * Created by doba on 5/26/17.
 */

public class SearchingResObj extends BaseResponse{

    @SerializedName("businesses")
    private List<BussinessItem> bussinessItems;

    public List<BussinessItem> getBussinessItems() {
        return bussinessItems;
    }
}
