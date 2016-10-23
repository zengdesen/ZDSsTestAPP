package none.zds.zdsstestapp.ui.presenter;

import android.content.Context;
import android.content.Intent;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import none.zds.zdsstestapp.ui.activity.WebViewActivity;
import none.zds.zdsstestapp.ui.viewinterface.WVView;

public class WVPresenter extends MvpBasePresenter<WVView> {

    private static final String EXTRA_URL = "extra_url";
    private static final String EXTRA_TITLE = "extra_title";

    private String mUrl = "";
    private String mTitle = "";

    /**
     * Using newIntent trick, return WebViewActivity Intent, to avoid `public static`
     * constant
     * variable everywhere
     *
     * @return Intent to start WebViewActivity
     */
    public static Intent newIntent(Context context, String extraURL, String extraTitle) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(EXTRA_URL, extraURL);
        intent.putExtra(EXTRA_TITLE, extraTitle);
        return intent;
    }

    public void parseIntent(Intent intent) {
        if (intent == null) {
            return;
        }

        mUrl = intent.getStringExtra(EXTRA_URL);
        mTitle = intent.getStringExtra(EXTRA_TITLE);
    }

    public String getUrl() {
        return mUrl;
    }

    public String getTitle() {
        return mTitle;
    }
}
