package none.zds.zdsstestapp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.util.List;

import none.zds.zdsstestapp.R;
import none.zds.zdsstestapp.entity.GankDataItem;
import none.zds.zdsstestapp.func.OnHeaderFuliClickListener;
import none.zds.zdsstestapp.ui.viewmodel.GankItemHeaderViewModel;
import none.zds.zdsstestapp.ui.viewmodel.GankItemViewModel;
import none.zds.zdsstestapp.util.StringStyles;

public class GankListAdapter extends HeaderRecyclerViewAdapter {
    private static final int ANIM_DELAY = 138;
    private int mLastAnimPosition = -1;
    private List<GankDataItem> mGankList;
    private String mFiliUrl = "";
    private GankItemHeaderViewModel mHeader = null;
    private OnHeaderFuliClickListener onHeaderFuliClickListener = null;

    public GankListAdapter(List<GankDataItem> gankList) {
        mGankList = gankList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateBasicItemViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_gank, parent, false);
        return new GankItemViewModel(v);
    }

    @Override
    public void onBindBasicItemView(RecyclerView.ViewHolder viewHolder, int position) {
        if (!(viewHolder instanceof GankItemViewModel)) {
            return;
        }
        GankItemViewModel gankItemViewModel = (GankItemViewModel) viewHolder;
        GankDataItem gankDataItem = mGankList.get(position);
        gankItemViewModel.setGankDataItem(gankDataItem);
        if (position == 0) {
            showCategory(gankItemViewModel);
        } else {
            boolean theCategoryOfLastEqualsToThis = mGankList.get(
                    position - 1).type.equals(mGankList.get(position).type);
            if (!theCategoryOfLastEqualsToThis) {
                showCategory(gankItemViewModel);
            } else {
                hideCategory(gankItemViewModel);
            }
        }
        gankItemViewModel.category.set(gankDataItem.type);
        SpannableStringBuilder builder = new SpannableStringBuilder(gankDataItem.desc).append(
                StringStyles.format(gankItemViewModel.getBinding().tvTitle.getContext(), " (via. " +
                        gankDataItem.who +
                        ")", R.style.ViaTextAppearance));
        CharSequence gankText = builder.subSequence(0, builder.length());

        gankItemViewModel.title.set(gankText.toString());
        showItemAnim(gankItemViewModel.getBinding().tvTitle, position);
    }

    @Override
    public int getBasicItemCount() {
        return mGankList.size();
    }

    @Override
    public int getBasicItemType(int position) {
        return 0;
    }

    @Override
    public boolean useHeader() {
        return true;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_gank_header, parent, false);
        mHeader = new GankItemHeaderViewModel(v);
        mHeader.setOnHeaderFuliClickListener((imageView) -> {
            onHeaderFuliClickListener.onClick(imageView);
        });
        return mHeader;
    }

    @Override
    public void onBindHeaderView(RecyclerView.ViewHolder viewHolder, int position) {
        // notifyDataSetChanged会触发onViewRecycled,使第一次图片加载不会显示,
        // 但是会留在cache中,下次加载会显示,所以多notifyChange一下
        if (mFiliUrl.equals(mHeader.photoUrl.get())) {
            mHeader.photoUrl.notifyChange();
        } else {
            mHeader.photoUrl.set(mFiliUrl);
        }
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder viewHolder) {
        if (!(viewHolder instanceof GankItemHeaderViewModel)) {
            return;
        }
        ((GankItemHeaderViewModel) viewHolder).onViewRecycled();
        super.onViewRecycled(viewHolder);
    }

    public void setFiliUrl(String filiUrl) {
        mFiliUrl = filiUrl;
    }

    public void setOnHeaderFuliClickListener(OnHeaderFuliClickListener onHeaderFuliClickListener) {
        this.onHeaderFuliClickListener = onHeaderFuliClickListener;
    }

    private void showCategory(GankItemViewModel holder) {
        if (!isVisibleOf(holder.getBinding().tvCategory)) {
            holder.getBinding().tvCategory.setVisibility(View.VISIBLE);
        }
    }

    private void hideCategory(GankItemViewModel holder) {
        if (isVisibleOf(holder.getBinding().tvCategory)) {
            holder.getBinding().tvCategory.setVisibility(View.GONE);
        }
    }

    /**
     * view.isShown() is a kidding...
     */
    private boolean isVisibleOf(View view) {
        return view.getVisibility() == View.VISIBLE;
    }

    public void showItemAnim(final View view, final int position) {
        Context context = view.getContext();
        if (position > mLastAnimPosition) {
            view.setAlpha(0);
            view.postDelayed(() -> {
                Animation animation = AnimationUtils.loadAnimation(context,
                        R.anim.slide_in_right);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        view.setAlpha(1);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {}

                    @Override
                    public void onAnimationRepeat(Animation animation) {}
                });
                view.startAnimation(animation);
            }, ANIM_DELAY * position);
            mLastAnimPosition = position;
        }
    }
}
