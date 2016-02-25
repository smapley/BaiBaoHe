package com.smapley.baibaohe.pubu.adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smapley.baibaohe.R;
import com.smapley.baibaohe.pubu.extra.fresco.DynamicHeightSimpleDraweeView;
import com.smapley.baibaohe.pubu.mode.Photo;
import com.smapley.baibaohe.utls.MyData;


public class waterFallWhiteItem extends RecyclerView.ViewHolder {

    /**
     * 内容主体的图片
     */
    public DynamicHeightSimpleDraweeView contentSdv;
    public TextView name;
    private TextView shuliang;
    private TextView yizhong;
    private LinearLayout linearLayout;
    private TextView biaozhu;
    private TextView jilv;
    private TextView jia;


    @SuppressWarnings("unchecked")
    protected <T extends View> T getView(int id) {
        return (T) itemView.findViewById(id);
    }

    public waterFallWhiteItem(View view) {
        super(view);
        contentSdv = (DynamicHeightSimpleDraweeView) view.findViewById(R.id.pubu_wf_item_content_DraweeView);
        name = (TextView) view.findViewById(R.id.pubu_wf_item_name);
        biaozhu = (TextView) view.findViewById(R.id.pubu_wf_item_biaozhu);
        shuliang = (TextView) view.findViewById(R.id.pubu_wf_item_shuliang);
        yizhong = (TextView) view.findViewById(R.id.pubu_wf_item_yizhong);
        linearLayout = (LinearLayout) view.findViewById(R.id.pubu_wf_item_lin);
        jilv = (TextView) view.findViewById(R.id.pubu_item_jilv);
        jia=(TextView)view.findViewById(R.id.jia);
    }

    public void setViews(Photo data) {
        if (data.ming != null) {
            linearLayout.setVisibility(View.VISIBLE);
            switch (data.nw) {
                case 0:
                    biaozhu.setText(R.string.nei);
                    break;
                case 1:
                    biaozhu.setText(R.string.wai);
                    break;
            }
            if(Integer.parseInt(data.jin)!=0||Integer.parseInt(data.bao)!=0){
                jia.setText(data.jia);
            }else{
                jia.setText(R.string.bukehuangou);
            }
            name.setText(MyData.ToDBC(data.ming));
            shuliang.setText(data.sl);
            yizhong.setText(data.yz);
            if (MyData.UTYPE == 1) {
                jilv.setVisibility(View.VISIBLE);
                jilv.setText(data.jilv);
            }
        }
        contentSdv.setImageURI(Uri.parse(MyData.URL_FILE_SUO + data.pic));

        float picRatio = (float) Integer.parseInt(data.height) / Integer.parseInt(data.weight);
        contentSdv.setHeightRatio(picRatio);

    }


}

