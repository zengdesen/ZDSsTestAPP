package none.zds.zdsstestapp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import none.zds.zdsstestapp.R;
import none.zds.zdsstestapp.databinding.ActivityGankBinding;
import none.zds.zdsstestapp.entity.GankData;
import none.zds.zdsstestapp.entity.GankDataItem;
import none.zds.zdsstestapp.ui.adapter.GankListAdapter;
import none.zds.zdsstestapp.ui.base.ToolbarActivity;
import none.zds.zdsstestapp.ui.presenter.GankPresenter;
import none.zds.zdsstestapp.ui.viewinterface.GankView;
import none.zds.zdsstestapp.util.Dates;

public class GankActivity extends ToolbarActivity<GankView, GankPresenter> implements GankView {
    private ActivityGankBinding binding;
    private List<GankDataItem> mGankList = new ArrayList<>();
    private GankListAdapter mAdapter;

    public static Intent newIntent(Context context, Date gankDate, String fuliUrl) {
        return GankPresenter.newIntent(context, gankDate, fuliUrl);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPresenter().parseIntent(getIntent());
        setTitle(Dates.toDate(getPresenter().getGankDate()));
        initRecyclerView();
        loadGankData();
    }

    @Override
    protected void provideContentView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_gank);
    }

    @NonNull
    @Override
    public GankPresenter createPresenter() {
        return new GankPresenter();
    }

    @Override
    public boolean canBack() {
        return true;
    }

    private void initRecyclerView() {
        mAdapter = new GankListAdapter(mGankList);
        mAdapter.setFiliUrl(getPresenter().getFuliUrl());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.rvGank.setLayoutManager(layoutManager);
        binding.rvGank.setAdapter(mAdapter);
        mAdapter.setOnHeaderFuliClickListener((imageView) -> {
            startPictureActivity(imageView);
        });
    }

    private void startPictureActivity(View transitView) {
        Intent intent = PictureActivity.newIntent(this, getPresenter().getFuliUrl(),
                Dates.toDate(getPresenter().getGankDate()));
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this, transitView, PictureActivity.TRANSIT_PIC);
        try {
            ActivityCompat.startActivity(this, intent, optionsCompat.toBundle());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            startActivity(intent);
        }
    }

    private void loadGankData() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getPresenter().getGankDate());
        addSubscription(getPresenter().getGankData(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH)));
    }

    @Override
    public void onAllResults(GankData.Result results) {
        if (results.androidList != null) mGankList.addAll(results.androidList);
        if (results.iOSList != null) mGankList.addAll(results.iOSList);
        if (results.appList != null) mGankList.addAll(results.appList);
        if (results.exList != null) mGankList.addAll(results.exList);
        if (results.recomList != null) mGankList.addAll(results.recomList);
        if (results.vedioList != null) mGankList.addAll(0, results.vedioList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadError(Throwable throwable) {
        throwable.printStackTrace();
        Snackbar.make(binding.getRoot(), R.string.snap_load_fail, Snackbar.LENGTH_LONG)
                .setAction(R.string.action_retry, v -> {
                    loadGankData();
                })
                .show();
    }

}
