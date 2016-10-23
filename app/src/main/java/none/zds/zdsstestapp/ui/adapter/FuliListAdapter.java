package none.zds.zdsstestapp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import none.zds.zdsstestapp.R;
import none.zds.zdsstestapp.entity.GankDataItem;
import none.zds.zdsstestapp.func.OnFuliItemClickListener;
import none.zds.zdsstestapp.ui.viewmodel.FuliItemViewModel;

public class FuliListAdapter extends RecyclerView.Adapter<FuliItemViewModel> {
    public static final String TAG = "FuliListAdapter";

    private List<GankDataItem> mList;
    private Context mContext;
    private OnFuliItemClickListener mOnFuliItemClickListener;

    public FuliListAdapter(Context context, List<GankDataItem> fuliList) {
        mList = fuliList;
        mContext = context;
    }

    @Override
    public FuliItemViewModel onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fuli, parent, false);
        return new FuliItemViewModel(v);
    }

    @Override
    public void onBindViewHolder(final FuliItemViewModel viewHolder, final int position) {
        GankDataItem gankFuLiItem = mList.get(position);
        int limit = 60;
        String text = gankFuLiItem.desc.length() > limit ? gankFuLiItem.desc.substring(0, limit) +
                "..." : gankFuLiItem.desc;
        viewHolder.setOnFuliItemClickListener(mOnFuliItemClickListener);
        viewHolder.setGankFuLiItem(gankFuLiItem);
        viewHolder.title.set(text);
        viewHolder.photoUrl.set(gankFuLiItem.url);
//        Picasso.with(mContext)
//             .load(gankFuLiItem.url)
//             .centerCrop()
//             .fit()
//             .into(viewHolder.getBinding().ivFuli, new Callback.EmptyCallback() {
//                 @Override
//                 public void onSuccess() {
//                     if (!viewHolder.getItemView().isShown()) {
//                         viewHolder.getItemView().setVisibility(View.VISIBLE);
//                     }
//                 }
//             });
    }

    @Override
    public void onViewRecycled(FuliItemViewModel viewHolder) {
        viewHolder.onViewRecycled();
        super.onViewRecycled(viewHolder);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setOnFuliClickListener(OnFuliItemClickListener onFuliItemClickListener) {
        mOnFuliItemClickListener = onFuliItemClickListener;
    }
}
