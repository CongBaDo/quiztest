package nemo.com.androidquiz.model;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by doba on 10/1/16.
 */

public class PropertyItem {

    @SerializedName("userId")
    public String userId;

    @SerializedName("uid")
    public String id;

    @SerializedName("categoryId")
    public String categoryId;

    @SerializedName("name")
    public String name;

    @SerializedName("listingHeadline")
    public String listingHeadline;

    @SerializedName("description")
    public String description;

    @SerializedName("typeCode")
    public int typeCode;

    @SerializedName("thumbnail")
    public String thumbnail;

    @SerializedName("roomType")
    public int roomType;//"roomType": 1, //1: MASTER 2: COMMON 3: SINGLE

    @SerializedName("state")
    public int state;

    @SerializedName("monthlyRent")
    public int monthlyRent;

    @SerializedName("securityDeposit")
    public int securityDeposit;

    @SerializedName("transactionId")
    public String transactionId;

    @SerializedName("latitude")
    public double latitude;

    @SerializedName("longitude")
    public double longitude;

    @SerializedName("address")
    public String address;

    @SerializedName("postCode")
    public String postCode;

    @SerializedName("locationId")
    public int locationId;

    @SerializedName("regionId")
    public int regionId;

    @SerializedName("countryId")
    public int countryId;

    @SerializedName("createdAt")
    public String createdAt;

    @SerializedName("username")
    public String username;

    public String displayName;

    @SerializedName("avatarUrl")
    public  String avatarUrl;

    public String getAvatar(){
        if(TextUtils.isEmpty(avatarUrl)){
            avatarUrl = "Nemo";
        }
        return avatarUrl;
    }

    @SerializedName("utilityFee")
    public int utilityFee;

    @SerializedName("status")
    public int status;

    @SerializedName("phoneNumber")
    public String phoneNumber;

    @SerializedName("matched")
    public float matched;

    public int deactivation;

//    TransactionStatus: Status:
// <0: Cancel
// 0: Default  1: Tenant request
// 2: Lanlord accept
// 3: Tenant accept
// 4: Payment (Booked)
    @SerializedName("transactionStatus")
    public int transactionStatus;

    public String getPostedName(){
        if(TextUtils.isEmpty(displayName)){
            return username;
        }
        return displayName;
    }

    public String getThumbnail(){
        if(TextUtils.isEmpty(thumbnail)){
            return "Nemo";
        }
        return thumbnail;
    }


    public String getMatchValue(){
        return String.valueOf(matched);
    }
}
