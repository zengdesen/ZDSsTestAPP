package none.zds.zdsstestapp.ui.presenter;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import none.zds.zdsstestapp.api.GankApi;
import none.zds.zdsstestapp.ui.viewinterface.MainView;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainPresenter extends MvpBasePresenter<MainView> {
    public Subscription getFuLi(int page) {
        return GankApi.getGankApi().getFuLiData(page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .map(gankFuLi -> gankFuLi.results)
                .subscribe(getView()::onFuliDataList,
                        getView()::onLoadError);
//                .doOnNext(getView()::onFuliDataList)
//                .flatMap(rx.Observable::from)
//                .subscribe(getView()::onNewFuliData,
//                        getView()::onLoadError);
    }
}
