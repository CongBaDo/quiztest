package nemo.com.androidquiz.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import nemo.com.androidquiz.R;
import nemo.com.androidquiz.utils.GeneralUtils;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created by bado on 5/25/17.
 */

public abstract  class BaseFragment extends Fragment implements View.OnClickListener{

    private static final String TAG = BaseFragment.class.getSimpleName();

    protected ProgressDialog progress;

    public abstract int getResourceLayout();

    public void initViews(View view) {

    }

    public void attachListeners() {
    }

    public void loadData() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getResourceLayout(), container, false);
        progress = new ProgressDialog(getActivity(), R.style.MyAlertDialogStyle);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setCancelable(false);
        initViews(view);
        initActionBar(view);


        return view;
    }
    protected void setLoadingMesasge(int id){
        progress.setMessage(getString(id));
    }


    protected void showLoading(){
        if(!progress.isShowing() && GeneralUtils.isNetworkOnline(getContext())){
            progress.show();
        }
    }

    protected void hideLoading(){
        if(progress.isShowing()){
            progress.dismiss();
        }
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        attachListeners();
    }

    public void initActionBar(View root) {

    }

    @Override
    public void onClick(View view) {

    }

    public void loadFragment(Fragment fragment, int resId){
        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(resId, fragment).commit();
        }
    }

    public void hideSoftKeyboard() {
        if(getActivity().getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
    }
}