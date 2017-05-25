package nemo.com.androidquiz.activity;

import android.app.ProgressDialog;
import android.content.ComponentCallbacks2;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import nemo.com.androidquiz.R;
import nemo.com.androidquiz.utils.GeneralUtils;
import nemo.com.androidquiz.utils.MViewUtils;


/**
 * Created by bado on 5/25/17.
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener, ComponentCallbacks2 {

    private static final String TAG = "BaseActivity";

    public abstract int getResourceLayout();

    protected Toolbar mToolBar;
    protected ProgressDialog progress;
    private boolean isUserIcon;

    public void initActionBar() {

        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        mToolBar.setTitleTextColor(Color.BLACK);
        mToolBar.setBackgroundColor(MViewUtils.getColor(this, R.color.colorPrimary));
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    protected void displayUserIcon(boolean isShow) {
        this.isUserIcon = isShow;
    }

    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    public void setTitle(int resource) {
        TextView tvTitle = (TextView) findViewById(R.id.tv_toolbar_title);
        tvTitle.setText(getText(resource));
        tvTitle.setTextColor(Color.WHITE);
    }

    public void setTitle(String title) {
        TextView tvTitle = (TextView) findViewById(R.id.tv_toolbar_title);
        tvTitle.setText(title);
        tvTitle.setTextColor(Color.WHITE);
    }

    public void showBackButton(boolean show) {
        if (show) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        } else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
        }
    }

    public void attachListeners() {

    }

    public void initViews() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getResourceLayout());
        initialFistLoad();
        initActionBar();
        initViews();
        doSetupAfterRenderedView();
        attachListeners();

        progress = new ProgressDialog(this);
        progress.setCancelable(false);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);

    }


    /**
     * The configuration for activity when activity start
     */
    public void initialFistLoad() {

    }

    /**
     * Setup view, after init
     */
    public void doSetupAfterRenderedView() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_user:

                break;
        }
    }
    protected void setLoadingMesasge(int id) {
        progress.setMessage(getString(id));
    }

    protected void showLoading() {
        if (!progress.isShowing() && GeneralUtils.isNetworkOnline(this)) {
            progress.show();
        }
    }

    protected void hideLoading() {
        if (progress.isShowing()) {
            progress.dismiss();
        }
    }

    public void loadFragment(Fragment fragment, int resId) {
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(resId, fragment).commit();
        }
    }

    public void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    /**
     * Release memory when the UI becomes hidden or when system resources become low.
     * @param level the memory-related event that was raised.
     */
    public void onTrimMemory(int level) {

        // Determine which lifecycle or system event was raised.
        Log.e(TAG, "onTrimMemory "+level);
        switch (level) {

            case ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN:

                /*
                   Release any UI objects that currently hold memory.

                   The user interface has moved to the background.
                */

                break;

            case ComponentCallbacks2.TRIM_MEMORY_RUNNING_MODERATE:
            case ComponentCallbacks2.TRIM_MEMORY_RUNNING_LOW:
            case ComponentCallbacks2.TRIM_MEMORY_RUNNING_CRITICAL:

                /*
                   Release any memory that your app doesn't need to run.

                   The device is running low on memory while the app is running.
                   The event raised indicates the severity of the memory-related event.
                   If the event is TRIM_MEMORY_RUNNING_CRITICAL, then the system will
                   begin killing background processes.
                */

                break;

            case ComponentCallbacks2.TRIM_MEMORY_BACKGROUND:
            case ComponentCallbacks2.TRIM_MEMORY_MODERATE:
            case ComponentCallbacks2.TRIM_MEMORY_COMPLETE:

                /*
                   Release as much memory as the process can.

                   The app is on the LRU list and the system is running low on memory.
                   The event raised indicates where the app sits within the LRU list.
                   If the event is TRIM_MEMORY_COMPLETE, the process will be one of
                   the first to be terminated.
                */

                break;

            default:
                /*
                  Release any non-critical data structures.

                  The app received an unrecognized memory level value
                  from the system. Treat this as a generic low-memory message.
                */
                break;
        }
    }
}
