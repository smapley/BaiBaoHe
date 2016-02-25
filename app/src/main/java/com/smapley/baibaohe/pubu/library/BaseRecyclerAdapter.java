package com.smapley.baibaohe.pubu.library;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smapley.baibaohe.R;
import com.smapley.baibaohe.pubu.adapter.waterFallWhiteItem;
import com.smapley.baibaohe.pubu.mode.Photo;

import java.util.List;

/**
 * @author Jack Tony
 * @brief recycleView的基础适配器，处理了添加头和底的逻辑
 * @date 2015/4/10
 */
public class BaseRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener, View.OnLongClickListener {

    protected final String TAG = getClass().getSimpleName();

    protected View customFooterView = null;

    protected List<Photo> mData;

    public BaseRecyclerAdapter(List<Photo> data) {
        mData = data;
    }

    /**
     * view的基本类型，这里只有头/底部/普通，在子类中可以扩展
     */
    class VIEW_TYPES {

        public static final int FOOTER = 8;
    }

    class SimpleViewHolder extends RecyclerView.ViewHolder {

        public SimpleViewHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     * 返回adapter中总共的item数目，包括头部和底部
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        int headerOrFooter = 0;
        if (customFooterView != null) {
            headerOrFooter++;
        }
        return mData.size() + headerOrFooter;
    }


    @Override
    public int getItemViewType(int position) {
        if (customFooterView != null && position == getItemCount() - 1) {
            return VIEW_TYPES.FOOTER;
        } else {
            return mData.get(position).getDataType();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPES.FOOTER && customFooterView != null) {
            return new SimpleViewHolder(customFooterView);
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pubu_white_item, parent, false);
        waterFallWhiteItem item = new waterFallWhiteItem(view);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        return item;
    }


    /**
     * 载入ViewHolder，这里仅仅处理header和footer视图的逻辑
     */
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int position) {
        if ((customFooterView != null && position == getItemCount() - 1)) {
            // 如果是header或者是footer则不处理
        } else {
            waterFallWhiteItem adapterItem = (waterFallWhiteItem) viewHolder;
            adapterItem.setViews(mData.get(position));
            viewHolder.itemView.setTag(mData.get(position));
        }
    }

    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onClick(view, (Photo) view.getTag());
        }
    }

    @Override
    public boolean onLongClick(View view) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onLongClick(view, (Photo) view.getTag());
        }
        return false;
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public void setClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public interface OnRecyclerViewItemClickListener {
        void onClick(View view, Photo data);

        void onLongClick(View view, Photo data);
    }

    public void updateData(List<Photo> data) {
        mData = data;
        notifyDataSetChanged();
    }
}
