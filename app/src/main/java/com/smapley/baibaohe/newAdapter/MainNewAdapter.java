package com.smapley.baibaohe.newAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smapley.baibaohe.R;
import com.smapley.baibaohe.holder.MainHeadHolder;
import com.smapley.baibaohe.holder.MainHolder;
import com.smapley.baibaohe.mode.MainBase;
import com.smapley.baibaohe.mode.MainHead;
import com.smapley.baibaohe.mode.MainItem;
import com.smapley.baibaohe.utls.http.bitmap.GetBitmap;

import java.util.List;

/**
 * Created by smapley on 2015/6/24.
 */
public class MainNewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int LAST_POSITION = -1;
    private final Context mContext;
    public List<MainBase> listitem;
    private GetBitmap getBitmap;

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


    public MainNewAdapter(Context context, List listitem, GetBitmap getBitmap) {
        mContext = context;
        this.listitem = listitem;
        this.getBitmap = getBitmap;

    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;
        if (viewType == 0) {
            view = LayoutInflater.from(mContext).inflate(R.layout.lunbo, parent, false);
            return new MainHeadHolder(view);

        } else {
            view = LayoutInflater.from(mContext).inflate(R.layout.layout_main_item, parent, false);
            return new MainHolder(view);

        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) == 0) {
            ((MainHeadHolder) holder).setData(((MainHead) listitem.get(position)), mContext, getBitmap);

        } else {
            ((MainHolder) holder).setData(((MainItem) listitem.get(position)), mContext, getBitmap);
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
