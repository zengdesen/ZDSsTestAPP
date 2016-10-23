package none.zds.zdsstestapp.ui.presenter;

import android.content.Context;
import android.content.Intent;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;

import none.zds.zdsstestapp.ui.activity.PictureActivity;

public class PicturePresenter extends MvpBasePresenter<MvpView> {
    public static final String EXTRA_IMAGE_URL = "image_url";
    public static final String EXTRA_IMAGE_TITLE = "image_title";

    private String mImageUrl;
    private String mImageTitle;

    public static Intent newIntent(Context context, String url, String desc) {
        Intent intent = new Intent(context, PictureActivity.class);
        intent.putExtra(EXTRA_IMAGE_URL, url);
        intent.putExtra(EXTRA_IMAGE_TITLE, desc);
        return intent;
    }

    public void parseIntent(Intent intent) {
        if (intent == null) {
            return;
        }
        mImageUrl = intent.getStringExtra(EXTRA_IMAGE_URL);
        mImageTitle = intent.getStringExtra(EXTRA_IMAGE_TITLE);
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public String getImageTitle() {
        return mImageTitle;
    }

}
