package com.zhuangfei.android_timetableview;

import android.graphics.Color;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zhuangfei.android_timetableview.fragment.TabFragment1;
import com.zhuangfei.android_timetableview.fragment.TabFragment2;
import com.zhuangfei.android_timetableview.fragment.TabFragment3;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TabFragment1 tabFragment1;
    private TabFragment2 tabFragment2;
    private TabFragment3 tabFragment3;

    LinearLayout tabLayout1;
    LinearLayout tabLayout2;
    LinearLayout tabLayout3;

    ImageView tabImageView1;
    ImageView tabImageView2;
    ImageView tabImageView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initView();
        select(0);
    }

    private void initView() {
        tabFragment1=new TabFragment1();
        tabFragment2=new TabFragment2();
        tabFragment3=new TabFragment3();

        tabLayout1=findViewById(R.id.tabLayout1);
        tabLayout2=findViewById(R.id.tabLayout2);
        tabLayout3=findViewById(R.id.tabLayout3);

        tabImageView1=findViewById(R.id.tabImageView1);
        tabImageView2=findViewById(R.id.tabImageView2);
        tabImageView3=findViewById(R.id.tabImageView3);

        tabLayout1.setOnClickListener(this);
        tabLayout2.setOnClickListener(this);
        tabLayout3.setOnClickListener(this);
    }

    private void initTab() {
        int color = getResources().getColor(R.color.app_gray);
        tabImageView1.setColorFilter(color);
        tabImageView2.setColorFilter(color);
        tabImageView3.setColorFilter(color);
    }

    private void select(int i) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        initTab();
        int color = Color.RED;

        switch (i) {
            case 0:
                tabImageView1.setColorFilter(color);
                transaction.replace(R.id.frameLayout,tabFragment1);
                break;
            case 1:
                tabImageView2.setColorFilter(color);
                transaction.replace(R.id.frameLayout, tabFragment2);
                break;
            case 2:
                tabImageView3.setColorFilter(color);
                transaction.replace(R.id.frameLayout, tabFragment3);
                break;
            default:
                break;
        }
        transaction.commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tabLayout1:
                select(0);
                break;
            case R.id.tabLayout2:
                select(1);
                break;
            case R.id.tabLayout3:
                select(2);
                break;
            default:
                break;
        }
    }
}
