package com.smapley.baibaohe.newAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smapley.baibaohe.R;
import com.smapley.baibaohe.holder.SetHeadHolder;
import com.smapley.baibaohe.holder.SetHolder;
import com.smapley.baibaohe.mode.MainBase;
import com.smapley.baibaohe.mode.SetHead;
import com.smapley.baibaohe.mode.SetItem;
import com.smapley.baibaohe.utls.http.bitmap.GetBitmap;

import java.util.List;

/**
 * Created by smapley on 2015/6/24.
 */
public class SetNewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int LAST_POSITION = -1;
    private final Context mContext;
    public List<MainBase> listitem;
    private GetBitmap getBitmap;
    public boolean edit = false;
    public boolean delect = false;

    public void add(MainBase item, int position) {
        listitem.add(position, item);
        notifyItemInserted(position);
    }

    public void remove() {
        while (getItemCount() > 20) {
            listitem.remove(getItemCount() - 1);
            notifyItemRemoved(getItemCount() - 1);
        }
//        notifyDataSetChanged();

    }


    public SetNewAdapter(Context context, List listitem, GetBitmap getBitmap) {
        mContext = context;
        this.listitem = listitem;
        this.getBitmap = getBitmap;
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;
        if (viewType == 0) {
            view = LayoutInflater.from(mContext).inflate(R.layout.layout_sethead_item, parent, false);
            return new SetHeadHolder(view);

        } else {
            view = LayoutInflater.from(mContext).inflate(R.layout.layout_set_item, parent, false);
            return new SetHolder(view);

        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) == 0) {
            ((SetHeadHolder) holder).setData(((SetHead) listitem.get(position)), mContext, edit, getBitmap);
        } else {
            ((SetHolder) holder).setData(((SetItem) listitem.get(position)), false, delect, mContext, getBitmap, position, this);
        }

    }

    @Override
    public int getItemCount() {
        return listitem.size();
    }

    @Override
    public int getItemViewType(int position) {
        return listitem.get(position).getType();
    }
}
