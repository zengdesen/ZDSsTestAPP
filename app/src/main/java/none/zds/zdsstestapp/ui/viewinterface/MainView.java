package none.zds.zdsstestapp.ui.viewinterface;

import com.hannesdorfmann.mosby.mvp.MvpView;

import java.util.List;

import none.zds.zdsstestapp.entity.GankDataItem;

public interface MainView extends MvpView {
    void onFuliDataList(List<GankDataItem> fuliDataList);
    void onNewFuliData(GankDataItem gankFuLiItem);
    void onLoadError(Throwable throwable);
}
