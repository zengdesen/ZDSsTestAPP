package none.zds.zdsstestapp.ui.viewmodel;

import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import none.zds.zdsstestapp.databinding.ItemGankHeaderBinding;
import none.zds.zdsstestapp.func.OnHeaderFuliClickListener;
import none.zds.zdsstestapp.func.OnLoadImageSuccess;

/**
 * Created by ZDS.
 */

public class GankItemHeaderViewModel extends RecyclerView.ViewHolder implements View.OnClickListener {
    public ObservableField<String> photoUrl =  new ObservableField<String>();

    private ItemGankHeaderBinding binding;
    private OnLoadImageSuccess onLoadImageSuccess = null;
    private OnHeaderFuliClickListener onHeaderFuliClickListener;

    public GankItemHeaderViewModel(View itemView) {
        super(itemView);
        binding = DataBindingUtil.bind(itemView);
        binding.setHeader(this);
        binding.ivFuli.setTag(this);
    }

    public ItemGankHeaderBinding getBinding() {
        return binding;
    }

    public void setOnLoadImageSuccess(OnLoadImageSuccess onLoadImageSuccess) {
        this.onLoadImageSuccess = onLoadImageSuccess;
    }

    public void onLoadImageSuccess() {
        binding.frameLayout.setVisibility(View.VISIBLE);
        if (onLoadImageSuccess != null) {
            onLoadImageSuccess.onSuccess();
        }
    }

    public void setOnHeaderFuliClickListener(OnHeaderFuliClickListener onHeaderFuliClickListener) {
        this.onHeaderFuliClickListener = onHeaderFuliClickListener;
    }

    @Override
    public void onClick(View v) {
        if (onHeaderFuliClickListener != null) {
            onHeaderFuliClickListener.onClick(binding.ivFuli);
        }
    }

    /**
     * 图片Url改变时候，会回调这个方法
     */
    @BindingAdapter({"gankHeaderImageUrl"})
    public static void setImageUrl(ImageView imageView, String url)
    {
        if(url != null)
        {
            Picasso.with(imageView.getContext())
                    .load(url)
                    .into(imageView, new Callback.EmptyCallback() {
                        @Override
                        public void onSuccess() {
//                            if (imageView.getVisibility() != View.VISIBLE) {
//                                imageView.setVisibility(View.VISIBLE);
//                            }
                            Object obj = imageView.getTag();
                            if (obj instanceof GankItemHeaderViewModel) {
                                GankItemHeaderViewModel viewModel = (GankItemHeaderViewModel) obj;
                                viewModel.onLoadImageSuccess();
                            }
                        }
                    });
        }
    }

    public void onViewRecycled() {
        Picasso.with(binding.ivFuli.getContext()).cancelRequest(binding.ivFuli);
    }
}
