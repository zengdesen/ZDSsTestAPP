package none.zds.zdsstestapp.ui.viewmodel;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import none.zds.zdsstestapp.databinding.ItemGankBinding;
import none.zds.zdsstestapp.entity.GankDataItem;
import none.zds.zdsstestapp.ui.activity.WebViewActivity;

public class GankItemViewModel extends RecyclerView.ViewHolder implements View.OnClickListener {
    public ObservableField<String> category = new ObservableField<String>();
    public ObservableField<String> title = new ObservableField<String>();

    private GankDataItem gankDataItem;
    private ItemGankBinding binding;

    public GankItemViewModel(View itemView) {
        super(itemView);
        binding = DataBindingUtil.bind(itemView);
        binding.setGank(this);
    }

    public ItemGankBinding getBinding() {
        return binding;
    }

    public void setGankDataItem(GankDataItem gankDataItem) {
        this.gankDataItem = gankDataItem;
    }

    @Override
    public void onClick(View v) {
        Intent intent = WebViewActivity.newIntent(v.getContext(), gankDataItem.url, gankDataItem.desc);
        v.getContext().startActivity(intent);
    }
}
