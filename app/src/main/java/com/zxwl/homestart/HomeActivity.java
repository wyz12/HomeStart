package com.zxwl.homestart;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zxwl.homestart.Adapter.VpAdapter;
import com.zxwl.homestart.Adapter.VpItmeAdapter;
import com.zxwl.homestart.Bean.AppDetail;
import com.zxwl.homestart.Bean.VpitemBean;
import com.zxwl.homestart.Utlis.Address;
import com.zxwl.homestart.Utlis.okhttp;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class HomeActivity extends Activity implements View.OnClickListener {


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
    private TextView mStime;
    private TextView mSstime;
    private LinearLayout mTime;
    private ImageView mWecimage1;
    private TextView mCurrtp;
    private TextView mTodaytp;
    private TextView mZcity;
    private LinearLayout mWetcher1;
    private ImageView mWecimage2;
    private TextView mCurrtp2;
    private TextView mTodaytp2;
    private TextView mFcity;
    private LinearLayout mWetcher2;
    private LinearLayout mWetcher;
    private ImageView mCallimage;
    private TextView mCalltextview;
    private LinearLayout mCall;
    private ImageView mLxrimage;
    private TextView mLxrtextview;
    private LinearLayout mLxr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initView();
        loadApps();
        SzAdapter();
        TimeMethod();
        AddressMethod();


    }

    private void AddressMethod() {
        Address address = new Address();
        String hqaddress = address.hqaddress(this);

        okhttp.getInstance().sendGet(" http://api.map.baidu.com/geocoder?location="+hqaddress+"&output=json&key=28bcdd84fae25699606ffad27f8da77b", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String string = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = new JSONObject(string);
                            String city1 = jsonObject.getJSONObject("result").getJSONObject("addressComponent").getString("city");
                            String city2 = jsonObject.getJSONObject("result").getJSONObject("addressComponent").getString("district");
                            mZcity.setText(city1);
                            mZcity.setText(city2);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    private void TimeMethod() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 EEEE");
        Date date = new Date();
        format = new SimpleDateFormat("HH:mm");
        mStime.setText(format.format(date));
        format = new SimpleDateFormat("MM月dd日");
        String format1 = format.format(date);
        format = new SimpleDateFormat("EEEE");
        mSstime.setText(format1+format.format(date));
    }

    private void SzAdapter() {


        vpitemBeans = new ArrayList<>();
        for (int x = 0; x < appDetails.size(); x++) {
            int i = x % 6;
            if (i == 0) {
                backimage = getResources().getDrawable(R.drawable.ic_launcher_background);
            }
            if (i == 1) {
                backimage = getResources().getDrawable(R.drawable.xxx);
            }
            if (i == 2) {
                backimage = getResources().getDrawable(R.drawable.ic_launcher_background);
            }
            if (i == 3) {
                backimage = getResources().getDrawable(R.drawable.xxx);
            }
            if (i == 4) {
                backimage = getResources().getDrawable(R.drawable.ic_launcher_background);
            }
            if (i == 5) {
                backimage = getResources().getDrawable(R.drawable.xxx);
            }
            vpitemBeans.add(new VpitemBean(appDetails.get(x).getLabel(), appDetails.get(x).getName(), appDetails.get(x).getIcon(), backimage));
        }
        viewPagerList = new ArrayList<>();
        totalPage = (int) Math.ceil(vpitemBeans.size() * 1.0 / mPageSize);
        for (int i = 0; i < totalPage; i++) {
            //每个页面都是inflate出一个新实例
            final GridView gridView = (GridView) View.inflate(this, R.layout.layout_gridview, null);

            gridView.setAdapter(new VpItmeAdapter(this, vpitemBeans, i, mPageSize, gridView));
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
        mStime = (TextView) findViewById(R.id.stime);
        mSstime = (TextView) findViewById(R.id.sstime);
        mTime = (LinearLayout) findViewById(R.id.time);
        mWecimage1 = (ImageView) findViewById(R.id.wecimage1);
        mCurrtp = (TextView) findViewById(R.id.currtp);
        mTodaytp = (TextView) findViewById(R.id.todaytp);
        mZcity = (TextView) findViewById(R.id.zcity);
        mWetcher1 = (LinearLayout) findViewById(R.id.wetcher1);
        mWecimage2 = (ImageView) findViewById(R.id.wecimage2);
        mCurrtp2 = (TextView) findViewById(R.id.currtp2);
        mTodaytp2 = (TextView) findViewById(R.id.todaytp2);
        mFcity = (TextView) findViewById(R.id.fcity);
        mWetcher2 = (LinearLayout) findViewById(R.id.wetcher2);
        mWetcher = (LinearLayout) findViewById(R.id.wetcher);
        mCallimage = (ImageView) findViewById(R.id.callimage);
        mCalltextview = (TextView) findViewById(R.id.calltextview);
        mCall = (LinearLayout) findViewById(R.id.call);
        mLxrimage = (ImageView) findViewById(R.id.lxrimage);
        mLxrtextview = (TextView) findViewById(R.id.lxrtextview);
        mLxr = (LinearLayout) findViewById(R.id.lxr);
        mViewPager.setOnClickListener(this);
        mIndicator.setOnClickListener(this);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) { //按下的如果是BACK，同时没有重复
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.ViewPager:
                break;
            case R.id.Indicator:
                break;
        }
    }
}
