package nemo.com.androidquiz.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.NestedScrollView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import nemo.com.androidquiz.R;
import nemo.com.androidquiz.customizedview.ObservableNestedScrollview;
import nemo.com.androidquiz.model.BussinessItem;
import nemo.com.androidquiz.utils.AppConstant;
import nemo.com.androidquiz.utils.MViewUtils;

/**
 * Created by doba on 5/26/17.
 */

public class BussinessDetailActivity extends BaseActivity {

    private ObservableScrollViewCallbacks mObservableCallback;
    private ObservableNestedScrollview mMainScrollView;
    private int mParallaxImageHeight;
    private BussinessItem bussinessItem;

    @Override
    public int getResourceLayout() {
        return R.layout.activity_detail;
    }

    @Override
    public void initViews() {
        super.initViews();

        mParallaxImageHeight = getResources().getDimensionPixelSize(R.dimen.base_144);
        setHeaderBackgroundColor(Color.TRANSPARENT);

        mObservableCallback = new ObservableScrollViewCallbacks() {
            @Override
            public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
                int baseColor = MViewUtils.getColor(BussinessDetailActivity.this, R.color.colorPrimary);
                float alpha = Math.min(1, (float) scrollY / mParallaxImageHeight);
                mToolBar.setBackgroundColor(ScrollUtils.getColorWithAlpha(alpha, baseColor));
            }

            @Override
            public void onDownMotionEvent() {

            }

            @Override
            public void onUpOrCancelMotionEvent(ScrollState scrollState) {

            }
        };
        mMainScrollView = (ObservableNestedScrollview) findViewById(R.id.nested_scroll);
        mMainScrollView.setScrollViewCallbacks(mObservableCallback);

        bussinessItem = (BussinessItem)getIntent().getSerializableExtra(AppConstant.INTENT_BUSSINESS_DETAIL);

        TextView tvName = (TextView)findViewById(R.id.tv_title);
        TextView tvAddress = (TextView)findViewById(R.id.tv_addess);
        TextView tvPhone = (TextView)findViewById(R.id.tv_phonenumber);
        TextView tvReview = (TextView)findViewById(R.id.tv_review);
        TextView tvCategory = (TextView)findViewById(R.id.tv_category);
        RatingBar ratingBar = (RatingBar)findViewById(R.id.rating_bar);
        ImageView imgCover = (ImageView)findViewById(R.id.img_cover);

        tvName.setText(bussinessItem.getName());
        tvAddress.setText(String.format(getString(R.string.title_address), bussinessItem.getLocation().getDisplayAdresses()[0], bussinessItem.getLocation().getCity()));
        tvPhone.setText(String.format(getString(R.string.title_phone), bussinessItem.getDisplayPhone()));
        tvReview.setText(String.format(getString(R.string.title_review), bussinessItem.getReviewCount()));
        tvCategory.setText(String.format(getString(R.string.title_category), bussinessItem.getCategoryItems().get(0).getTitle(), bussinessItem.getCategoryItems().get(1).getTitle()));
        ratingBar.setRating(bussinessItem.getRating());

        Picasso.with(this).load(bussinessItem.getImageUrl()).into(imgCover);
    }
}
