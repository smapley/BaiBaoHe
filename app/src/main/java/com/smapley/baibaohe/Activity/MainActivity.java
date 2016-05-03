package com.smapley.baibaohe.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.smapley.baibaohe.Adapter.ViewPagerAdapter;
import com.smapley.baibaohe.Fragment.MainItem1;
import com.smapley.baibaohe.Fragment.MainItem2;
import com.smapley.baibaohe.Fragment.MainItem4;
import com.smapley.baibaohe.R;
import com.smapley.baibaohe.utls.Exit;
import com.smapley.baibaohe.utls.MyData;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends FragmentActivity implements View.OnClickListener {

    public TextView itemButton1;
    public TextView itemButton2;
    public TextView itemButton3;
    public TextView tag;
    private SharedPreferences sharedPreferences;
    private static Boolean isExit = false;
    public ViewPager viewPager;
    private ViewPagerAdapter viewPagerAapter;
    private MainItem1 item1;
    private MainItem2 item2;
    private MainItem4 item4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences(MyData.USERSP, MODE_PRIVATE);
        MyData.UTYPE = sharedPreferences.getInt("utype", -1);
        MyData.PHONE = sharedPreferences.getString("phone", "");
        initView();
        initFragment();

    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.id_viewpager);
        itemButton1 = (TextView) findViewById(R.id.main_item1);
        itemButton2 = (TextView) findViewById(R.id.main_item2);
        itemButton3 = (TextView) findViewById(R.id.main_item3);
        itemButton1.setOnClickListener(this);
        itemButton2.setOnClickListener(this);
        itemButton3.setOnClickListener(this);
    }


    private void initFragment() {
        item1 = new MainItem1();
        item2 = new MainItem2();
        item4 = new MainItem4();
        List<android.support.v4.app.Fragment> list = new ArrayList<android.support.v4.app.Fragment>();
        list.add(item1);
        list.add(item2);
        list.add(item4);
        viewPagerAapter = new ViewPagerAdapter(getSupportFragmentManager(), list);
        viewPager.setAdapter(viewPagerAapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                resetImageViewSrc();
                switch (position) {
                    case 0:
                        itemButton1.setBackgroundResource(R.drawable.main1s);
                        break;
                    case 1:
                        item2.getData();
                        itemButton2.setBackgroundResource(R.drawable.main2s);
                        break;
                    case 2:
                        itemButton3.setBackgroundResource(R.drawable.main3s);
                        break;
                }
            }

            private void resetImageViewSrc() {
                itemButton1.setBackgroundResource(R.drawable.main1);
                itemButton2.setBackgroundResource(R.drawable.main2);
                itemButton3.setBackgroundResource(R.drawable.main3);
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffPx) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_item1:
                viewPager.setCurrentItem(0, true);
                break;
            case R.id.main_item2:
                item2.getData();
                viewPager.setCurrentItem(1, true);
                break;
            case R.id.main_item3:
                viewPager.setCurrentItem(2, true);
                break;
        }
    }


    /*
       * 监听返回键，菜单打开时，按一次关闭菜单
       */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        super.onKeyDown(keyCode, event);
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            exitBy2Click();
        }
        return false;

    }

    /**
     * 两次返回键，退出程序
     */
    public void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            Toast.makeText(this, R.string.Exit, Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            new Exit();
            Exit.getInstance().exit();
        }
    }
}
