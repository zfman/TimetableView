package com.zhuangfei.android_timetableview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.zhuangfei.android_timetableview.R;
import com.zhuangfei.timetable.model.Schedule;
import com.zhuangfei.timetable.model.ScheduleColorPool;

import java.util.List;

/**
 * Created by Liu ZhuangFei on 2018/6/18.
 */

public class NonViewAdapter extends BaseAdapter {

    List<Schedule> schedules;
    Context context;
    LayoutInflater inflater;

    public NonViewAdapter(Context context, List<Schedule> schedules) {
        this.context = context;
        this.schedules = schedules;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return schedules.size();
    }

    @Override
    public Object getItem(int i) {
        return schedules.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View mView = null;
        ViewHolder holder;
        if (null == convertView) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_nonview, null);
            holder.nameTextView = convertView.findViewById(R.id.id_nonview_name);
            holder.colorTextView = convertView.findViewById(R.id.id_nonview_color);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Schedule schedule= (Schedule) getItem(i);
        ScheduleColorPool colorPool=new ScheduleColorPool(context);
        holder.nameTextView.setText(schedule.getName());
        holder.colorTextView.setBackgroundColor(colorPool.getColorAuto(schedule.getColorRandom()));
        return convertView;
    }

    class ViewHolder {
        TextView nameTextView;
        TextView colorTextView;
    }
}
