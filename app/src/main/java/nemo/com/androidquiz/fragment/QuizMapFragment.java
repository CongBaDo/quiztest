package nemo.com.androidquiz.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import nemo.com.androidquiz.R;
import nemo.com.androidquiz.adapter.InfoMapPagerAdapter;
import nemo.com.androidquiz.customizedview.TouchableWrapper;
import nemo.com.androidquiz.model.seach.SearchingReqObj;
import nemo.com.androidquiz.model.seach.SearchingResObj;
import nemo.com.androidquiz.restmanager.CommonInterface;
import nemo.com.androidquiz.restservice.SearchingService;
import nemo.com.androidquiz.utils.LocationController;

/**
 * Created by doba on 5/25/17.
 */

public class QuizMapFragment extends BaseFragment implements OnMapReadyCallback,
        GoogleMap.OnMapClickListener,
        GoogleMap.OnCameraMoveStartedListener,
        GoogleMap.OnCameraMoveCanceledListener,
        GoogleMap.OnInfoWindowClickListener,
        GoogleMap.OnCameraMoveListener,
        GoogleMap.OnMarkerClickListener{

    private static final String TAG = "QuizMapFragment";

    private MapView mMapView;
    private GoogleMap mGoogleMap;
    private LocationController locationController;
    private Marker mMyMarker;
    private List<Marker> mListMarker;
    private Location mCameraLocation, mPreviousCameraLocation;

    private InfoMapPagerAdapter infoMapPagerAdapter;
    @Override
    public int getResourceLayout() {
        return R.layout.fragment_maps;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        TouchableWrapper mTouchView = new TouchableWrapper(getActivity());
        mTouchView.setTouchListener(new TouchableWrapper.TouchListener() {
            @Override
            public void onUpListener() {

                if(mCameraLocation == null && mPreviousCameraLocation == null){
                    return;
                }

                float moveDistance = mPreviousCameraLocation.distanceTo(mCameraLocation)/1000;
                Log.e(TAG, "onUpListener "+moveDistance+" "+" "+mCameraLocation.getLatitude());
                mPreviousCameraLocation.setLatitude(mCameraLocation.getLatitude());
                mPreviousCameraLocation.setLongitude(mCameraLocation.getLongitude());

                    if(mCameraLocation != null && moveDistance > 1){
                        Log.e(TAG, "onUpListener NOT NULL "+moveDistance+" "+" "+mCameraLocation.getLatitude());
//                        resetData();
//                        loadAddress(mCameraLocation);
//                        ((MainActivity)getActivity()).filterLocation(0, locationIds, mRadius, mCameraLocation);
                    }
            }

            @Override
            public void onDownListener() {
            }
        });
        mTouchView.addView(super.onCreateView(inflater, container, savedInstanceState));
        return mTouchView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mListMarker = new ArrayList<Marker>();
        mMapView = (MapView) getView().findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);

        mCameraLocation = new Location("");

        locationController = new LocationController(getActivity());
        locationController.initController();
        locationController.setLocationListener(new LocationController.LocationControllerListener() {
            @Override
            public void onFail() {
                //Log.e(TAG, "Load location fail");
            }

            @Override
            public void onLocationChanged(Location location) {
                Log.e(TAG, "onLocationChanged " + location.getLatitude() + " " + location.getLongitude());

                mPreviousCameraLocation = new Location("");
                mPreviousCameraLocation.setLatitude(location.getLatitude());
                mPreviousCameraLocation.setLongitude(location.getLongitude());

                moveCamera(location, 15);
            }
        });

    }

    @Override
    public void initViews(View view) {
        super.initViews(view);
    }

    @Override
    public void onStart() {
        locationController.connect();
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();

        showDialogOpenLocationService();

        if (mGoogleMap == null) {
            return;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onStop() {
        super.onStop();
        locationController.disconnect();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();

        mMapView.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap map) {

        Log.e(TAG, "onMapReady ");
        mGoogleMap = map;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
        mGoogleMap.getUiSettings().setCompassEnabled(true);
        mGoogleMap.setOnMapClickListener(this);
        mGoogleMap.setOnMarkerClickListener(this);

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mGoogleMap.setMyLocationEnabled(true);
        }

        mGoogleMap.setOnCameraMoveStartedListener(this);
        mGoogleMap.setOnCameraMoveListener(this);
        mGoogleMap.setOnCameraMoveCanceledListener(this);
        mGoogleMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {

            }
        });
    }

    @Override
    public void onCameraMove() {
        if(mCameraLocation != null){

            mCameraLocation.setLatitude(mGoogleMap.getCameraPosition().target.latitude);
            mCameraLocation.setLongitude(mGoogleMap.getCameraPosition().target.longitude);
        }
    }

    public void moveCamera(final Location location, float levelZoom) {
        // Updates the location and zoom of the MapView

        if (mGoogleMap == null) {
            return;
        }
        if (mMyMarker == null) {
            //Set logo to market
        } else {
            mMyMarker.setPosition(new LatLng(location.getLatitude(), location.getLongitude()));
        }

        final CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(location.getLatitude(), location.getLongitude()))
                .zoom(levelZoom)
                .build();

        mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), new GoogleMap.CancelableCallback() {
            @Override
            public void onFinish() {

                Log.v(TAG, "onFinish ");

                searchData("restaurant", mCameraLocation);
            }

            @Override
            public void onCancel() {

            }
        });
    }

    public void showDialogOpenLocationService() {

        Dialog dialog;

        LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        boolean gpsEnabled = false;
        boolean networkEnabled = false;
        try {
            gpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
            networkEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
            Log.d(TAG, "MapLocationManager", ex);
        }

        if (!gpsEnabled && !networkEnabled) {
            // notify user
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());
            alertBuilder.setCancelable(false);
            alertBuilder.setTitle(getString(R.string.you_location_serivce_not_enable_title));
            alertBuilder.setMessage(getString(R.string.you_location_serivce_not_enable_message));
            alertBuilder.setPositiveButton("Enable", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(myIntent);
                }
            });

            dialog = alertBuilder.create();
            dialog.show();

        }
    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    /**
     * Draw user 's current position to map
     */
    private void addUserMarker(Location location) {
        //Add user marker
//        mMyMarker = mGoogleMap.addMarker(new MarkerOptions()
//                .zIndex(1.0f)
//                .position(new LatLng(location.getLatitude(), location.getLongitude()))
//                .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons(mLogo, AppConstant.NUMBER_ICON_SIZE, AppConstant.NUMBER_ICON_SIZE))));
    }

    @Override
    public void onCameraMoveCanceled() {

    }

    @Override
    public void onCameraMoveStarted(int i) {

    }

    @Override
    public void onInfoWindowClick(Marker marker) {

    }

    private void searchData(String query, Location location){
        SearchingReqObj searchingReqObj = new SearchingReqObj();
        searchingReqObj.setTerm(query);
        searchingReqObj.setLatitude(location.getLatitude());
        searchingReqObj.setLongitude(location.getLongitude());
        searchingReqObj.setRadius(30);

        SearchingService searchingService = new SearchingService();
        searchingService.setRequestObject(searchingReqObj);
        searchingService.request(getActivity(), new CommonInterface.ModelResponse<SearchingResObj>() {
            @Override
            public void onSuccess(SearchingResObj result) {

            }

            @Override
            public void onFail() {

            }
        });
    }

}
