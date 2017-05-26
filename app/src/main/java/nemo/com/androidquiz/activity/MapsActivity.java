package nemo.com.androidquiz.activity;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import nemo.com.androidquiz.R;
import nemo.com.androidquiz.fragment.QuizMapFragment;
import nemo.com.androidquiz.restmanager.RetrofitManager;

public class MapsActivity extends BaseActivity {


    @Override
    public int getResourceLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initViews() {
        super.initViews();

        RetrofitManager.getInstance().resetManager();

        loadFragment(new QuizMapFragment(), R.id.view_content);
    }
}
