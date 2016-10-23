package none.zds.zdsstestapp.ui.viewmodel;

import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import none.zds.zdsstestapp.databinding.ItemFuliBinding;
import none.zds.zdsstestapp.entity.GankDataItem;
import none.zds.zdsstestapp.func.OnFuliItemClickListener;

public class FuliItemViewModel extends RecyclerView.ViewHolder implements View.OnClickListener {
    public ObservableField<String> title = new ObservableField<String>();
    public ObservableField<String> photoUrl =  new ObservableField<String>();

    private ItemFuliBinding binding;
    private GankDataItem gankFuLiItem;
    private View itemView;
    private OnFuliItemClickListener onFuliItemClickListener = null;

    public FuliItemViewModel(View itemView) {
        super(itemView);
        binding = DataBindingUtil.bind(itemView);
        binding.ivFuli.setTag(itemView);
        binding.setFuli(this);
        this.itemView = itemView;
        binding.ivFuli.setOriginalSize(50, 50);
    }

    public void setGankFuLiItem(GankDataItem gankFuLiItem) {
        this.gankFuLiItem = gankFuLiItem;
    }

    public ItemFuliBinding getBinding() {
        return binding;
    }

    public View getItemView() {
        return itemView;
    }

    public void setOnFuliItemClickListener(OnFuliItemClickListener onFuliItemClickListener) {
        this.onFuliItemClickListener = onFuliItemClickListener;
    }

    @Override
    public void onClick(View v) {
        onFuliItemClickListener.onClick(gankFuLiItem);
    }

    /**
     * 图片Url改变时候，会回调这个方法
     */
    @BindingAdapter({"fuliImageUrl"})
    public static void setImageUrl(ImageView imageView, String url)
    {
//        Log.d("FuliItemViewModel", "setImageUrl:" + url);
        if(url != null)
        {
            Picasso.with(imageView.getContext()).
                    load(url)
                    .centerCrop()
                    .fit()
                    .into(imageView, new Callback.EmptyCallback() {
                        @Override
                        public void onSuccess() {
                            Object object = imageView.getTag();
                            if (object instanceof View) {
                                View itemView = (View) object;
                                if (!itemView.isShown()) {
                                    itemView.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    });
        }
    }

    public void onViewRecycled() {
        Picasso.with(binding.ivFuli.getContext()).cancelRequest(binding.ivFuli);
    }
}
