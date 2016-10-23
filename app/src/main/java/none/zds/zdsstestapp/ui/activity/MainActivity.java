package none.zds.zdsstestapp.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import none.zds.zdsstestapp.R;
import none.zds.zdsstestapp.databinding.ActivityMainBinding;
import none.zds.zdsstestapp.entity.GankDataItem;
import none.zds.zdsstestapp.ui.adapter.FuliListAdapter;
import none.zds.zdsstestapp.ui.base.SwipeRefreshBaseActivity;
import none.zds.zdsstestapp.ui.presenter.MainPresenter;
import none.zds.zdsstestapp.ui.viewinterface.MainView;

public class MainActivity extends SwipeRefreshBaseActivity<MainView, MainPresenter> implements MainView {
    private static final int PRELOAD_SIZE = 6;

    private ActivityMainBinding binding;
    private FuliListAdapter mFuliListAdapter;
    private List<GankDataItem> mFuliList = new ArrayList<>();
    private int mPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupRecyclerView();
        mFuliList.clear();
        loadFiliData();
    }

    @Override
    protected void provideContentView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    }

    @NonNull
    @Override
    public MainPresenter createPresenter() {
        return new MainPresenter();
    }

    @Override
    public void requestDataRefresh() {
        super.requestDataRefresh();
        mPage = 1;
        loadFiliData();
    }

    @Override
    public void onFuliDataList(List<GankDataItem> fuliDataList) {
        mFuliList.addAll(fuliDataList);
        mFuliListAdapter.notifyDataSetChanged();
        setRefresh(false);
    }

    @Override
    public void onNewFuliData(GankDataItem gankFuLiItem) {
        mFuliList.add(gankFuLiItem);
        mFuliListAdapter.notifyDataSetChanged();
        setRefresh(false);
    }

    @Override
    public void onLoadError(Throwable throwable) {
        throwable.printStackTrace();
        Snackbar.make(binding.rvFuli, R.string.snap_load_fail, Snackbar.LENGTH_LONG)
                .setAction(R.string.action_retry, v -> {
                    requestDataRefresh();
                })
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_about:
                startActivity(new Intent(this, AboutActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupRecyclerView() {
        mFuliListAdapter = new FuliListAdapter(this, mFuliList);
        mFuliListAdapter.setOnFuliClickListener(gankFuLiItem -> {
            startGankActivity(gankFuLiItem);
        });
        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        binding.rvFuli.setLayoutManager(layoutManager);
        binding.rvFuli.setAdapter(mFuliListAdapter);
        binding.rvFuli.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView rv, int dx, int dy) {
                boolean isBottom = layoutManager.findLastCompletelyVisibleItemPositions(new int[2])[1] >=
                        mFuliListAdapter.getItemCount() - PRELOAD_SIZE;
                if (!mSwipeRefreshLayout.isRefreshing() && isBottom) {
                    mPage += 1;
                    loadFiliData();
                }
            }
        });

//        new Once(this).show("tip_guide_6", () -> {
//            Snackbar.make(mRecyclerView, getString(R.string.tip_guide), Snackbar.LENGTH_INDEFINITE)
//                    .setAction(R.string.i_know, v -> {
//                    })
//                    .show();
//        });
    }

    private void loadFiliData() {
        setRefresh(true);
        addSubscription(getPresenter().getFuLi(mPage));
    }

    private void startGankActivity(GankDataItem gankDataItem) {
        Intent intent = GankActivity.newIntent(this, gankDataItem.publishedAt, gankDataItem.url);
        startActivity(intent);
    }

}
