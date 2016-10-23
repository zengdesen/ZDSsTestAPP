package none.zds.zdsstestapp.ui.presenter;

import android.content.Context;
import android.content.Intent;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import java.util.Date;

import none.zds.zdsstestapp.api.GankApi;
import none.zds.zdsstestapp.ui.activity.GankActivity;
import none.zds.zdsstestapp.ui.viewinterface.GankView;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class GankPresenter extends MvpBasePresenter<GankView> {
    public static final String EXTRA_GANK_DATE = "gank_date";
    public static final String EXTRA_FULI_URL = "fuli_url";

    private Date mGankDate = new Date();
    private String mFuliUrl = "";

    public static Intent newIntent(Context context, Date gankDate, String fuliUrl) {
        Intent intent = new Intent(context, GankActivity.class);
        intent.putExtra(EXTRA_GANK_DATE, gankDate);
        intent.putExtra(EXTRA_FULI_URL, fuliUrl);
        return intent;
    }

    public void parseIntent(Intent intent) {
        if (intent == null) {
            return;
        }

        mGankDate = (Date) intent.getSerializableExtra(EXTRA_GANK_DATE);
        mFuliUrl = intent.getStringExtra(EXTRA_FULI_URL);
    }

    public Date getGankDate() {
        return mGankDate;
    }

    public String getFuliUrl() {
        return mFuliUrl;
    }

    public Subscription getGankData(int year, int month, int day) {
        return GankApi.getGankApi().getGankData(year, month, day)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .map(GankData -> GankData.results)
                .subscribe(getView()::onAllResults,
                        getView()::onLoadError);
    }
}
