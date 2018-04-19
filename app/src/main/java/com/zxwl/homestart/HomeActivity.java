package com.zxwl.homestart;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.zxwl.homestart.Adapter.VpAdapter;
import com.zxwl.homestart.Adapter.VpItmeAdapter;
import com.zxwl.homestart.Bean.AppDetail;
import com.zxwl.homestart.Bean.VpitemBean;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends Activity {


    private ViewPager mViewPager;
    private LinearLayout mIndicator;
    private ArrayList<AppDetail> appDetails;

    private ArrayList<View> viewPagerList;
    private int mPageSize = 6;
    private int totalPage;
    private ImageView[] ivPoints;
    private PackageManager manager;
    private ArrayList<VpitemBean> vpitemBeans;
    private Drawable backimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        initView();
        loadApps();
        SzAdapter();

//        http://api.map.baidu.com/geocoder?location=25.990998,113.645966&output=json&key=28bcdd84fae25699606ffad27f8da77b

    }

    private void SzAdapter() {



        vpitemBeans = new ArrayList<>();
        for(int x=0;x<appDetails.size();x++){
            int i = x % 6;
            if(i==0){
                backimage = getResources().getDrawable(R.drawable.ic_launcher_background);
            }
            if(i==1){
                backimage = getResources().getDrawable(R.drawable.xxx);
            }
            if(i==2){
                backimage = getResources().getDrawable(R.drawable.ic_launcher_background);
            }
            if(i==3){
                backimage = getResources().getDrawable(R.drawable.xxx);
            }
            if(i==4){
                backimage = getResources().getDrawable(R.drawable.ic_launcher_background);
            }
            if(i==5){
                backimage = getResources().getDrawable(R.drawable.xxx);
            }
            vpitemBeans.add(new VpitemBean(appDetails.get(x).getLabel(),appDetails.get(x).getName(),appDetails.get(x).getIcon(),backimage));
        }
        viewPagerList = new ArrayList<>();
        totalPage = (int) Math.ceil(vpitemBeans.size() * 1.0 / mPageSize);
        for (int i = 0; i < totalPage; i++) {
            //每个页面都是inflate出一个新实例
            final GridView gridView = (GridView) View.inflate(this, R.layout.layout_gridview, null);

            gridView.setAdapter(new VpItmeAdapter(this, vpitemBeans, i, mPageSize,gridView));
            //添加item点击监听
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1,
                                        int position, long arg3) {
                    // TODO Auto-generated method stub
                    Object obj = gridView.getItemAtPosition(position);
                    if (obj != null && obj instanceof VpitemBean) {
                        Intent i = manager.getLaunchIntentForPackage(vpitemBeans.get(position).getPckename().toString());
                        HomeActivity.this.startActivity(i);
                    }
                }
            });
            //每一个GridView作为一个View对象添加到ViewPager集合中
            viewPagerList.add(gridView);
        }
        //设置ViewPager适配器
        mViewPager.setAdapter(new VpAdapter(viewPagerList));

        //添加小圆点
        ivPoints = new ImageView[totalPage];
        for (int i = 0; i < totalPage; i++) {
            //循坏加入点点图片组
            ivPoints[i] = new ImageView(this);
            if (i == 0) {
                ivPoints[i].setImageResource(R.drawable.xbd);
            } else {
                ivPoints[i].setImageResource(R.drawable.xd);
            }
            ivPoints[i].setPadding(8, 8, 8, 8);
            mIndicator.addView(ivPoints[i]);
        }
        //设置ViewPager的滑动监听，主要是设置点点的背景颜色的改变
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // TODO Auto-generated method stub
                //currentPage = position;
                for (int i = 0; i < totalPage; i++) {
                    if (i == position) {
                        ivPoints[i].setImageResource(R.drawable.xbd);
                    } else {
                        ivPoints[i].setImageResource(R.drawable.xd);
                    }
                }
            }
        });
    }

    private void loadApps() {

        manager = getPackageManager();

        appDetails = new ArrayList<>();

        Intent i = new Intent(Intent.ACTION_MAIN, null);

        i.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> availableActivities = manager.queryIntentActivities(i, 0);

        for (ResolveInfo ri : availableActivities) {
            AppDetail app = new AppDetail(ri.loadLabel(manager), ri.activityInfo.packageName, ri.activityInfo.loadIcon(manager));
            appDetails.add(app);

        }
    }
    private void initView() {

        mViewPager = (ViewPager) findViewById(R.id.ViewPager);
        mIndicator = (LinearLayout) findViewById(R.id.Indicator);
    }
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) { //按下的如果是BACK，同时没有重复
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }




}
