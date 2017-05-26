package nemo.com.androidquiz.adapter;

/**
 * Created by doba on 5/25/17.
 */

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import nemo.com.androidquiz.R;
import nemo.com.androidquiz.model.BussinessItem;

/**
 * View pager adapter
 */
public class InfoMapPagerAdapter extends PagerAdapter {

    private static final String TAG = "InfoMapPagerAdapter";
    private Context mContext;
    private List<BussinessItem> arrayImage = new ArrayList<>();

    public interface InfoClickListener{
        public void onItemClickListener(int pos);
    }

    private InfoClickListener infoClickListener;

    public InfoMapPagerAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<BussinessItem> propertyItems){
        Log.e(TAG, "setData "+propertyItems.size());
        this.arrayImage.clear();
        this.arrayImage.addAll(propertyItems);
        notifyDataSetChanged();
    }

    public void resetData(){
        this.arrayImage.clear();
        notifyDataSetChanged();
    }

    public void setListener(InfoClickListener listener){
        this.infoClickListener = listener;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View viewSlide = (View) LayoutInflater.from(mContext).inflate(R.layout.layout_info, null);

        container.addView(viewSlide);
        return viewSlide;
    }

    @Override
    public int getCount() {
        if(arrayImage == null){
            return 0;
        }
        return arrayImage.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}