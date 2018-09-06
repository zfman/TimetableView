package com.zhuangfei.android_timetableview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zhuangfei.android_timetableview.views.AttrActivity;
import com.zhuangfei.android_timetableview.views.BaseFuncActivity;
import com.zhuangfei.android_timetableview.views.ColorPoolActivity;
import com.zhuangfei.android_timetableview.views.CustomWidthActivity;
import com.zhuangfei.android_timetableview.views.DateActivity;
import com.zhuangfei.android_timetableview.views.DateDelayActivity;
import com.zhuangfei.android_timetableview.views.ElasticActivity;
import com.zhuangfei.android_timetableview.views.ExtrasActivity;
import com.zhuangfei.android_timetableview.views.FlaglayoutActivity;
import com.zhuangfei.android_timetableview.views.ItemStyleActivity;
import com.zhuangfei.android_timetableview.views.NonViewActivity;
import com.zhuangfei.android_timetableview.views.SlideActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        Button button1=findViewById(R.id.id_btn1);
        Button button2=findViewById(R.id.id_btn2);
        Button button3=findViewById(R.id.id_btn3);
        Button button4=findViewById(R.id.id_btn4);
        Button button5=findViewById(R.id.id_btn5);
        Button button6=findViewById(R.id.id_btn6);
        Button button7=findViewById(R.id.id_btn7);
        Button button8=findViewById(R.id.id_btn8);
        Button button9=findViewById(R.id.id_btn9);
        Button button10=findViewById(R.id.id_btn10);
        Button button11=findViewById(R.id.id_btn11);
        Button button12=findViewById(R.id.id_btn12);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);
        button9.setOnClickListener(this);
        button10.setOnClickListener(this);
        button11.setOnClickListener(this);
        button12.setOnClickListener(this);
    }

    public void toTarget(Class cla){
        Intent intent=new Intent(this,cla);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.id_btn1:
                toTarget(BaseFuncActivity.class);
                break;
            case R.id.id_btn2:
                toTarget(AttrActivity.class);
                break;
            case R.id.id_btn3:
                toTarget(ColorPoolActivity.class);
                break;
            case R.id.id_btn4:
                toTarget(ItemStyleActivity.class);
                break;
            case R.id.id_btn5:
                toTarget(ElasticActivity.class);
                break;
            case R.id.id_btn6:
                toTarget(SlideActivity.class);
                break;
            case R.id.id_btn7:
                toTarget(DateActivity.class);
                break;
            case R.id.id_btn8:
                toTarget(NonViewActivity.class);
                break;
            case R.id.id_btn9:
                toTarget(ExtrasActivity.class);
                break;
            case R.id.id_btn10:
                toTarget(FlaglayoutActivity.class);
                break;
            case R.id.id_btn11:
                toTarget(DateDelayActivity.class);
                break;
            case R.id.id_btn12:
                toTarget(CustomWidthActivity.class);
                break;

        }
    }
}
