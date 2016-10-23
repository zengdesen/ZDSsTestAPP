package none.zds.zdsstestapp.ui.viewinterface;

import com.hannesdorfmann.mosby.mvp.MvpView;

import none.zds.zdsstestapp.entity.GankData;

public interface GankView extends MvpView {
    void onAllResults(GankData.Result results);
    void onLoadError(Throwable throwable);
}
