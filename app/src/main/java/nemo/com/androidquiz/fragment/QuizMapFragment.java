package nemo.com.androidquiz.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
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
import android.widget.ImageView;
import android.widget.TextView;

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
import java.util.HashMap;
import java.util.List;

import nemo.com.androidquiz.R;
import nemo.com.androidquiz.activity.BussinessDetailActivity;
import nemo.com.androidquiz.customizedview.TouchableWrapper;
import nemo.com.androidquiz.model.BussinessItem;
import nemo.com.androidquiz.model.seach.SearchingReqObj;
import nemo.com.androidquiz.model.seach.SearchingResObj;
import nemo.com.androidquiz.restmanager.CommonInterface;
import nemo.com.androidquiz.restservice.SearchingService;
import nemo.com.androidquiz.utils.AppConstant;
import nemo.com.androidquiz.utils.BitmapUtility;
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
        GoogleMap.OnMarkerClickListener,
        GoogleMap.InfoWindowAdapter{

    private static final String TAG = "QuizMapFragment";

    private MapView mMapView;
    private GoogleMap mGoogleMap;
    private LocationController locationController;
    private Marker mMyMarker;
    private List<Marker> mListMarker;
    private List<BussinessItem> bussinessItems;
    private Location mCameraLocation, mPreviousCameraLocation;

    private HashMap<Marker, BussinessItem> maps = new HashMap();

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
                        resetData();
                        searchData("restaurant", mCameraLocation);
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

        bussinessItems = new ArrayList<>();

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

//                searchingReqObj.setLatitude(37.786882);
//                searchingReqObj.setLongitude(-122.399972);
                Location fakeLocation = new Location("");
                fakeLocation.setLatitude(37.786882);
                fakeLocation.setLongitude(-122.399972);
                moveCamera(fakeLocation, 15);
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

        mGoogleMap.setInfoWindowAdapter(new CustomInfoWindowAdapter());
        mGoogleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                mMyMarker = marker;

                BussinessItem bussinessItem = maps.get(marker);
                for(int i = 0; i < bussinessItems.size(); i++){
                    if(bussinessItem.getId() == bussinessItems.get(i).getId()){

                        Log.e(TAG, "info "+bussinessItem.getName());
                        Intent intent = new Intent(getActivity(), BussinessDetailActivity.class);
                        intent.putExtra(AppConstant.INTENT_BUSSINESS_DETAIL, bussinessItem);
                        startActivity(intent);
                        break;
                    }
                }
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
//        searchingReqObj.setLatitude(location.getLatitude());
//        searchingReqObj.setLongitude(location.getLongitude());
//        searchingReqObj.setTerm("delis");
        searchingReqObj.setLatitude(37.786882);
        searchingReqObj.setLongitude(-122.399972);
        searchingReqObj.setRadius(30);

        SearchingService searchingService = new SearchingService();
        searchingService.setRequestObject(searchingReqObj);
        searchingService.request(getActivity(), new CommonInterface.ModelResponse<SearchingResObj>() {
            @Override
            public void onSuccess(SearchingResObj result) {

                if(result.getCode() == null){
                    bussinessItems = result.getBussinessItems();
                    loadMarkers(bussinessItems, 0);
                    Log.e(TAG, "onSuccess "+result.getBussinessItems().size()+" "+result.getBussinessItems().get(0).getLocation().getDisplayAdresses()[0]);
                }else{
                    Log.e(TAG, "onSuccess code "+result.getCode().getDescription());
                }
            }

            @Override
            public void onFail() {

            }
        });
    }

    private void loadMarkers(List<BussinessItem> itemList, int selectedPosition){

        if(mGoogleMap == null){
            return;
        }
        maps.clear();
        this.mGoogleMap.clear();
        if(getActivity() == null || itemList == null || itemList.size() == 0){
            return;
        }
        Log.e(TAG, "loadMarkers "+itemList.size()+" "+selectedPosition);

        for(int i = 0; i < itemList.size(); i++){
            BussinessItem item = itemList.get(i);

            int icon =  R.drawable.ic_maker_other;

            MarkerOptions markerOption = new MarkerOptions().position(new LatLng(item.getCoordinateBussiness().getLatitude(), item.getCoordinateBussiness().getLongitude()));
            Marker marker = mGoogleMap.addMarker(markerOption);

            View viewMarker = getActivity().getLayoutInflater().inflate(R.layout.item_marker, null);
            ImageView img = (ImageView)viewMarker.findViewById(R.id.img_marker);
            img.setImageResource(icon);
            Bitmap pinCustom = BitmapUtility.createDrawableFromView(getActivity(), viewMarker);
            marker.setIcon(BitmapDescriptorFactory.fromBitmap(pinCustom));
            maps.put(marker, item);

        }

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    private class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

        private View view;

        public CustomInfoWindowAdapter() {
            view = getActivity().getLayoutInflater().inflate(R.layout.layout_info,
                    null);
        }

        @Override
        public View getInfoContents(Marker marker) {
            return null;
        }

        @Override
        public View getInfoWindow(final Marker marker) {
            mMyMarker = marker;
            TextView tvAddress = (TextView)view.findViewById(R.id.tv_addess);
            TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);

            BussinessItem bussinessItem = maps.get(marker);
            for(int i = 0; i < bussinessItems.size(); i++){
                if(bussinessItem.getId() == bussinessItems.get(i).getId()){
                    tvTitle.setText(bussinessItem.getName());
                    tvAddress.setText(bussinessItem.getLocation().getDisplayAdresses()[0]);
                    break;
                }
            }

            return view;
        }
    }

    public void resetData(){
        if(mGoogleMap != null){
            this.mGoogleMap.clear();
        }
        if(bussinessItems != null){
            bussinessItems.clear();
        }
    }
}
