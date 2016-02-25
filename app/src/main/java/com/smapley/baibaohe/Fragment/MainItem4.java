package com.smapley.baibaohe.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.smapley.baibaohe.Activity.DuiJiang;
import com.smapley.baibaohe.Activity.Help;
import com.smapley.baibaohe.Activity.JiLu;
import com.smapley.baibaohe.Activity.JiangQuan;
import com.smapley.baibaohe.Activity.LianMeng;
import com.smapley.baibaohe.Activity.Login;
import com.smapley.baibaohe.Activity.MainActivity;
import com.smapley.baibaohe.Activity.QianBao;
import com.smapley.baibaohe.Activity.Set;
import com.smapley.baibaohe.Activity.ZhanShi;
import com.smapley.baibaohe.R;
import com.smapley.baibaohe.utls.MyData;

/**
 * Created by smapley on 2015/5/11.
 */
public class MainItem4 extends Fragment {

    private SharedPreferences sharedPreferences;
    private View contentView;
    private LinearLayout lianmeng;
    private LinearLayout item0;
    private LinearLayout item1;
    private LinearLayout item2;
    private LinearLayout item3;
    private LinearLayout item4;
    private LinearLayout item5;
    private LinearLayout item6;
    private LinearLayout item7;
    private LinearLayout item8;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentView = inflater.inflate(R.layout.fragment_main_item4, container, false);
        sharedPreferences = getActivity().getSharedPreferences(MyData.USERSP, Context.MODE_PRIVATE);
        initView(contentView);
        return contentView;
    }

    private void showView() {
        switch (MyData.UTYPE) {
            case -1:
                item0.setVisibility(View.GONE);
                item1.setVisibility(View.GONE);
                item2.setVisibility(View.GONE);
                item3.setVisibility(View.GONE);
                item4.setVisibility(View.GONE);
                item5.setVisibility(View.GONE);
                item7.setVisibility(View.GONE);
                item6.setVisibility(View.VISIBLE);
                item8.setVisibility(View.VISIBLE);
                lianmeng.setVisibility(View.GONE);
                break;
            case 1:
                item0.setVisibility(View.VISIBLE);
                item1.setVisibility(View.VISIBLE);
                item2.setVisibility(View.VISIBLE);
                item3.setVisibility(View.VISIBLE);
                item4.setVisibility(View.GONE);
                item5.setVisibility(View.VISIBLE);
                item6.setVisibility(View.VISIBLE);
                item7.setVisibility(View.VISIBLE);
                item8.setVisibility(View.GONE);
                lianmeng.setVisibility(View.VISIBLE);
                break;
            case 2:
                item0.setVisibility(View.VISIBLE);
                item1.setVisibility(View.VISIBLE);
                item2.setVisibility(View.VISIBLE);
                item3.setVisibility(View.GONE);
                item4.setVisibility(View.GONE);
                item5.setVisibility(View.GONE);
                item6.setVisibility(View.VISIBLE);
                item7.setVisibility(View.VISIBLE);
                item8.setVisibility(View.GONE);
                lianmeng.setVisibility(View.GONE);
                break;
            case 9:
                item0.setVisibility(View.VISIBLE);
                item1.setVisibility(View.VISIBLE);
                item2.setVisibility(View.VISIBLE);
                item3.setVisibility(View.VISIBLE);
                item4.setVisibility(View.VISIBLE);
                item5.setVisibility(View.VISIBLE);
                item6.setVisibility(View.VISIBLE);
                item7.setVisibility(View.VISIBLE);
                item8.setVisibility(View.GONE);
                lianmeng.setVisibility(View.VISIBLE);
                break;
            default:
                item0.setVisibility(View.VISIBLE);
                item1.setVisibility(View.VISIBLE);
                item2.setVisibility(View.GONE);
                item3.setVisibility(View.GONE);
                item4.setVisibility(View.GONE);
                item5.setVisibility(View.GONE);
                item6.setVisibility(View.VISIBLE);
                item7.setVisibility(View.VISIBLE);
                item8.setVisibility(View.GONE);
                lianmeng.setVisibility(View.GONE);
                break;
        }
    }


    private void initView(View view) {
        item1 = (LinearLayout) view.findViewById(R.id.item4_1);
        item0 = (LinearLayout) view.findViewById(R.id.item4_0);
        item2 = (LinearLayout) view.findViewById(R.id.item4_2);
        item3 = (LinearLayout) view.findViewById(R.id.item4_3);
        item4 = (LinearLayout) view.findViewById(R.id.item4_4);
        item5 = (LinearLayout) view.findViewById(R.id.item4_5);
        item6 = (LinearLayout) view.findViewById(R.id.item4_6);
        item7 = (LinearLayout) view.findViewById(R.id.item4_7);
        item8 = (LinearLayout) view.findViewById(R.id.item4_8);
        lianmeng = (LinearLayout) view.findViewById(R.id.item4_51);
        showView();
        item0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), QianBao.class));
            }
        });
        item1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), JiLu.class));
            }
        });
        item2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), DuiJiang.class));
            }
        });
        item3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ZhanShi.class);
                intent.putExtra("src", 1);
                startActivity(intent);
            }
        });
        item4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), JiangQuan.class));
            }
        });
        item5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Set.class));
            }

        });
        item6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Help.class));
            }
        });
        item7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(getString(R.string.tips));
                builder.setMessage(getString(R.string.logout));
                builder.setNegativeButton(R.string.cancel, null);
                builder.setPositiveButton(R.string.Okay, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MyData.PHONE = "";
                        MyData.UTYPE = -1;
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("utype", -1);
                        editor.putString("phone", "");
                        editor.commit();
                        showView();
                    }
                });
                builder.create().show();
            }
        });

        item8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).viewPager.setCurrentItem(0, true);
                startActivity(new Intent(getActivity(), Login.class));
            }
        });
        lianmeng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), LianMeng.class));
            }
        });
    }


}

