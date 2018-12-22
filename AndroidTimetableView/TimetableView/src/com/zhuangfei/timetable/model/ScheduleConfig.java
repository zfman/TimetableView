package com.zhuangfei.timetable.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.ArraySet;

import com.zhuangfei.timetable.TimetableView;
import com.zhuangfei.timetable.listener.ISchedule;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 本地配置
 * Created by Liu ZhuangFei on 2018/12/21.
 */
public class ScheduleConfig {
    private ISchedule.OnConfigHandleListener mOnConfigHandleListener;
    private SharedPreferences mConfigPreferences;
    private SharedPreferences.Editor mEditor;
    private Map<String,String> mConfigMap;
    public ScheduleConfig(Context context,String configName){
        mConfigPreferences=context.getSharedPreferences(configName,Context.MODE_PRIVATE);
        mEditor=mConfigPreferences.edit();
        mConfigMap=new HashMap<>();
    }

    public ScheduleConfig setOnConfigHandleListener(ISchedule.OnConfigHandleListener mOnConfigHandleListener) {
        this.mOnConfigHandleListener = mOnConfigHandleListener;
        return this;
    }

    public ISchedule.OnConfigHandleListener getOnConfigHandleListener() {
        return mOnConfigHandleListener;
    }

    public ScheduleConfig  put(String key, String value){
        if(value==null) return this;
        mConfigMap.put(key,value);
        return this;
    }

    public String get(String key){
        return mConfigMap.get(key);
    }

    public ScheduleConfig setConfigMap(Map<String, String> mConfigMap) {
        this.mConfigMap = mConfigMap;
        return this;
    }

    public Map<String, String> getConfigMap() {
        return mConfigMap;
    }

    public void commit(){
        Set<String> set=new HashSet<>();
        for(Map.Entry<String,String> entry:mConfigMap.entrySet()){
            if(entry.getKey()==null||entry.getValue()==null) continue;
            set.add(entry.getKey().trim()+"="+entry.getValue().trim());
        }
        Set<String> finalSet=mConfigPreferences.getStringSet("scheduleconfig_set",new HashSet<String>());
        finalSet.addAll(set);
        mEditor.putStringSet("scheduleconfig_set",finalSet);
        mEditor.commit();
    }

    public void use(TimetableView view){
        if(getConfigMap()==null||getOnConfigHandleListener()==null) return;
        Set<String> keySet=mConfigPreferences.getStringSet("scheduleconfig_set",new HashSet<String>());
        String[] configArray=null;
        for(String str:keySet){
            if(!TextUtils.isEmpty(str)&&str.indexOf("=")!=-1){
                str=str.trim();
                configArray=str.split("=");
                if(configArray.length==2){
                    getOnConfigHandleListener().onParseConfig(configArray[0],configArray[1],view);
                }
            }
        }
    }
}
