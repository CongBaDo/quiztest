package nemo.com.androidquiz.restmanager;

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

//    @POST("login")
//    Call<LoginResponse> login(@Body LoginRequest loginRequest);
//
//    @POST("logout")
//    Call<LogoutResponse> logout(@Body LogoutRequest logoutRequest);
//
//    @POST("acc_data")
//    Call<AccDataResponse> getAccData();
//
//    @POST("acc_submit")
//    Call<AccDataSubmitResponse> submitAccData(@Body AccDataSubmitRequest accDataSubmitRequest);
//
//    @POST("activity_list")
//    Call<ActivityListResponse> getActivityList(@Body ActivityListRequest activityListRequest);
//
//    @POST("checkin_list")
//    Call<CheckinListResponse> getCheckinList(@Body CheckinListRequest checkingListRequest);
//
//    @POST("comp_detail")
//    Call<CompDetailResponse> getCompDetail(@Body CompDetailRequest compDetailRequest);
//
//    @POST("comp_list")
//    Call<CompListResponse> getCompList();
//
//    @POST("exchange_submit")
//    Call<ExchangeSubmitResponse> getExchangeSubmit(@Body ExchangeSubmitRequest exchangeSubmitRequest);
//
//    @POST("follow_del")
//    Call<FollowDelResponse> followDel(@Body FollowDelRequest followDelRequest);
//
//    @POST("follow_list")
//    Call<FollowListResponse> followList(@Body FollowListRequest followListRequest);
//
//    @POST("follow_put")
//    Call<FollowPutResponse> followPut(@Body FollowPutRequest followPutRequest);
//
//    @POST("getsp_checkin")
//    Call<GetSPCheckinResponse> getSPCheckin(@Body GetSPCheckinRequest getSPCheckinRequest);
//
//    @POST("getsp_{compcode}_{brand}_code")
//    Call<GetSpCodeResponse> getSPCode(@Path("compcode") String compcode, @Path("brand") String brand, @Body GetSPCodeRequest getSPCodeRequest);
//
//    @POST("getsp_onboading")
//    Call<GetSPOnboardingResponse> getSPOnboarding(@Body GetSPOnboardingRequest getSPOnboardingRequest);
//
//    @POST("getsp_{compcode}_{brand}_qr")
//    Call<GetSPQRResponse> getSPQR(@Path("compcode") String compcode, @Path("brand") String brand, @Body GetSPQRRequest getSPQRRequest);
//
//    @POST("mission_list")
//    Call<MissionListResponse> getMissionList(@Body MissionListRequest missionListRequest);
//
//    @POST("mkt_change_odr")
//    Call<MktChangeOdrResponse> getMktChangeOdr(@Body MktChangeOdrRequest mktChangeOdrRequest);
//
//    @POST("mkt_del_odr")
//    Call<MktDelOdrResponse> getMktDelOdr(@Body MktDelOdrRequest mktDelOdrRequest);
//
//    @POST("mkt_get_odr")
//    Call<MktGetOdrResponse> getMktGetOdr(@Body MktGetOdrRequest mktGetOdrRequest);
//
//    @POST("mkt_odr_list")
//    Call<MktOdrListResponse> getMktOdrList(@Body MktOdrListRequest mktOdrListRequest);
//
//    @POST("mkt_put_odr")
//    Call<MktPutOdrResponse> getMktPutOdr(@Body MktPutOdrRequest mktPutOdrRequest);
//
//    @POST("onboading_data")
//    Call<OnboardingDataResponse> getOnboardingData(@Body OnboardingDataRequest onboardingDataRequest);
//
//    @POST("password_reset")
//    Call<PasswordResetResponse> resetPassword(@Body PasswordResetRequest passwordResetRequest);
//
//    @POST("stock_detail")
//    Call<StockDetailResponse> getStockDetail(@Body StockDetailRequest stockDetailRequest);
//
//    @POST("stock_list")
//    Call<StockListResponse> getStockList(@Body StockListRequest stockListRequest);
//
//    @POST("badge_list")
//    Call<BadgeListResponse> getBadgeList(@Body BadgeListRequest badgeListRequest);
//
//    @POST("ex_item_list")
//    Call<ExItemListResponse> getExItemList(@Body ExItemListRequest exItemListRequest);
//
//    @POST("user_info")
//    Call<UserInfoResponse> getUserInfo(@Body UserInfoRequest userInfoRequest);
//
//    @POST("user_update")
//    Call<UserUpdateResponse> updateUser(@Body UserUpdateRequest userUpdateRequest);
//
//    @POST("watch_stock_del")
//    Call<WatchStockDelResponse> watchStockDel(@Body WatchStockDelRequest watchStockDelRequest);
//
//    @POST("watch_stock_list")
//    Call<WatchStockListResponse> watchStockList(@Body WatchStockListRequest watchStockListRequest);
//
//    @POST("watch_stock_put")
//    Call<WatchStockPutResponse> watchStockPut(@Body WatchStockPutRequest watchStockPutRequest);
//
//    @POST("favorite_list")
//    Call<FavoriteListRespone> getFavoriteList(@Body FavoriteListRequest favoriteListRequest);
//
//    @POST("favorite_add")
//    Call<FavoriteAddRespone> favoriteAdd(@Body FavoriteAddRequest favoriteAddRequest);
//
//    @POST("favorite_delete")
//    Call<FavoriteAddRespone> favoriteDelete(@Body FavoriteAddRequest favoriteAddRequest);
//
//    @POST("message_list")
//    Call<MessageListResponse> getMessageList(@Body MessageListRequest messageListRequest);
//
//    @GET("search")
//    Call<ZipcodeResponse> getZipcode(@Query("zipcode") String zipcode);
//
//    @POST("sp_item_list")
//    Call<SpItemListResponse> getSPItemList(@Body SpItemListRequest spItemListRequest);
//
//    @POST("sp_item_history_add")
//    Call<HistoryAddResponse> addItemHistory(@Body HistoryAddRequest historyAddRequest);
//
//    @POST("sp_item_history_list")
//    Call<ItemHistoryListResponse> getSpItemHistoryList(@Body ItemHistoryListRequest historyListRequest);
//
//    @POST("address_list")
//    Call<CompanyAddressRespone> getCompanyAddress(@Body CompanyAddressRequest companyAddressRequest);
//
//    @POST("message_read")
//    Call<MessageReadResponse> readMessage(@Body MessageReadRequest messageReadRequest);
//
//    @POST("campaign_list")
//    Call<CampaignListResponse> getCampaignList(@Body CampaignListRequest campaignListRequest);
//
//    @POST("news_list")
//    Call<NewListResponse> getNewList(@Body NewListRequest newListRequest);
//
//    @POST("address_add")
//    Call<AddressRespone> addNewAddress(@Body AddressRequest addressRequest);
//
//    @POST("address_del")
//    Call<AddressDelResponse> deleteAddress(@Body AddressDelRequest addressDelRequest);
//
//    @POST("address_update")
//    Call<AddressUpdateRespone> updateAddress(@Body AddressUpdateRequest addressUpdateRequest);
//
//    @POST("resend_mail")
//    Call<ResendEmailResponse> resendEmail(@Body ResendEmailRequest resendEmailRequest);
//
//    @POST("chart_data")
//    Call<ChartDataResponse> getChartData(@Body ChartDataResquest chartDataRequest);
//
//    @POST("address_list")
//    Call<AddressListResponse> getAddressList(@Body AddressListRequest addressListRequest);
}
