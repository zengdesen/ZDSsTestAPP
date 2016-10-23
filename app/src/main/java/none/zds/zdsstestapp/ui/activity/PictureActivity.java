package none.zds.zdsstestapp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;

import com.hannesdorfmann.mosby.mvp.MvpView;
import com.squareup.picasso.Picasso;

import java.io.File;

import none.zds.zdsstestapp.R;
import none.zds.zdsstestapp.databinding.ActivityPictureBinding;
import none.zds.zdsstestapp.ui.base.ToolbarActivity;
import none.zds.zdsstestapp.ui.presenter.PicturePresenter;
import none.zds.zdsstestapp.util.Shares;
import none.zds.zdsstestapp.util.Toasts;
import none.zds.zdsstestapp.util.Utils;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import uk.co.senab.photoview.PhotoViewAttacher;

public class PictureActivity extends ToolbarActivity<MvpView, PicturePresenter> {

    public static final String TRANSIT_PIC = "picture";

    private ActivityPictureBinding binding;
    PhotoViewAttacher mPhotoViewAttacher;

    @Override
    protected void provideContentView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_picture);
    }

    @NonNull
    @Override
    public PicturePresenter createPresenter() {
        return new PicturePresenter();
    }

    @Override public boolean canBack() {
        return true;
    }

    public static Intent newIntent(Context context, String url, String desc) {
        return PicturePresenter.newIntent(context, url, desc);
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPresenter().parseIntent(getIntent());
        ViewCompat.setTransitionName(binding.ivPicture, TRANSIT_PIC);
        Picasso.with(this).load(getPresenter().getImageUrl()).into(binding.ivPicture);
        setAppBarAlpha(0.7f);
        setTitle(getPresenter().getImageTitle());
        setupPhotoAttacher();
    }

    private void setupPhotoAttacher() {
        mPhotoViewAttacher = new PhotoViewAttacher(binding.ivPicture);
        mPhotoViewAttacher.setOnViewTapListener((view, v, v1) -> hideOrShowToolbar());
        // @formatter:off
        mPhotoViewAttacher.setOnLongClickListener(v -> {
            new AlertDialog.Builder(PictureActivity.this)
                    .setMessage(getString(R.string.ask_saving_picture))
                    .setNegativeButton(android.R.string.cancel,
                            (dialog, which) -> dialog.dismiss())
                    .setPositiveButton(android.R.string.ok,
                            (dialog, which) -> {
                                saveImageToGallery();
                                dialog.dismiss();
                            })
                    .show();
            // @formatter:on
            return true;
        });
    }

    private void saveImageToGallery() {
        // @formatter:off
        Subscription s = Utils.saveImageAndGetPathObservable(this, getPresenter().getImageUrl(), getPresenter().getImageTitle())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(uri -> {
                File appDir = new File(Environment.getExternalStorageDirectory(), "Fuli");
                String msg = String.format(getString(R.string.picture_has_save_to),
                        appDir.getAbsolutePath());
                Toasts.showShort(msg);
            }, error -> Toasts.showLong(error.getMessage() + "\n再试试..."));
        // @formatter:on
        addSubscription(s);
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_picture, menu);
        // TODO: 把图片的一些信息，比如 who，加载到 Overflow 当中
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_share:
                Utils.saveImageAndGetPathObservable(this, getPresenter().getImageUrl(), getPresenter().getImageTitle())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(uri -> Shares.shareImage(this, uri,
                                getString(R.string.share_to)),
                                error -> Toasts.showLong(error.getMessage()));
                return true;
            case R.id.action_save:
                saveImageToGallery();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        mPhotoViewAttacher.cleanup();
    }
}
