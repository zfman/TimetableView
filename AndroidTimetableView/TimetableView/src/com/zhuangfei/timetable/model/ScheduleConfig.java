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
 */
public class ScheduleConfig {
    private ISchedule.OnConfigHandleListener mOnConfigHandleListener;
    private SharedPreferences mConfigPreferences;
    private SharedPreferences.Editor mEditor;
    private Map<String,String> mConfigMap;
    private Context context;
    private String configName="default_schedule_config";

    public ScheduleConfig(Context context){
        this.context=context;
        mConfigMap=new HashMap<>();
    }

    /**
     * 设置本地配置的名称
     * @param name 配置名称
     * @return ScheduleConfig
     */
    public ScheduleConfig setConfigName(String name){
        if(configName==null||name==null) return this;
        if(mConfigPreferences==null||!configName.equals(name)){
            configName=name;
            mConfigPreferences=context.getSharedPreferences(configName,Context.MODE_PRIVATE);
            mEditor=mConfigPreferences.edit();
        }
        return this;
    }

    /**
     * 设置配置处理器
     * @param mOnConfigHandleListener 配置处理器
     * @return ScheduleConfig
     */
    public ScheduleConfig setOnConfigHandleListener(ISchedule.OnConfigHandleListener mOnConfigHandleListener) {
        this.mOnConfigHandleListener = mOnConfigHandleListener;
        return this;
    }

    /**
     * 获取配置处理器
     * @return ISchedule.OnConfigHandleListener
     */
    public ISchedule.OnConfigHandleListener getOnConfigHandleListener() {
        return mOnConfigHandleListener;
    }

    /**
     * 将配置提交到缓存，需要使用commit()将其保存到本地
     * @param key 属性名
     * @param value 属性值
     * @return ScheduleConfig
     */
    public ScheduleConfig  put(String key, String value){
        if(mConfigMap==null||value==null) return this;
        mConfigMap.put(key,value);
        return this;
    }

    /**
     * 从缓存中取出属性key的值
     * @param key 属性名
     * @return String
     */
    public String get(String key){
        if(mConfigMap==null) return null;
        return mConfigMap.get(key);
    }

    /**
     * 将指定的Map作为缓存
     * @param mConfigMap Map<String, String>
     * @return ScheduleConfig
     */
    public ScheduleConfig setConfigMap(Map<String, String> mConfigMap) {
        this.mConfigMap = mConfigMap;
        return this;
    }

    /**
     * 获取缓存的属性Map
     * @return
     */
    public Map<String,String> getConfigMap() {
        return mConfigMap;
    }

    /**
     * 将缓存中的修改提交到本地
     */
    public void commit(){
        Set<String> set=new HashSet<>();
        for(Map.Entry<String,String> entry:mConfigMap.entrySet()){
            if(entry.getKey()==null||entry.getValue()==null) continue;
            set.add(entry.getKey().trim()+"="+entry.getValue().trim());
        }
        Set<String> finalSet=mConfigPreferences.getStringSet("scheduleconfig_set",new HashSet<String>());
        finalSet.addAll(set);
        mConfigMap.clear();
        mEditor.putStringSet("scheduleconfig_set",finalSet);
        mEditor.commit();
    }

    /**
     * 清除缓存和本地属性配置
     */
    public void clear(){
        mConfigMap.clear();
        mEditor.clear();
        mEditor.commit();
    }

    /**
     * 导出本地配置文件中的数据
     * @return set集合，每个元素都是一个配置，格式：key=value
     */
    public Set<String> export(){
        Set<String> finalSet=mConfigPreferences.getStringSet("scheduleconfig_set",new HashSet<String>());
        return finalSet;
    }

    /**
     * 将集合配置导入到本地
     * @param data
     */
    public void load(Set<String> data){
        Set<String> finalSet=mConfigPreferences.getStringSet("scheduleconfig_set",new HashSet<String>());
        finalSet.addAll(data);
        mConfigMap.clear();
        mEditor.putStringSet("scheduleconfig_set",finalSet);
        mEditor.commit();
    }

    /**
     * 设置TimetableView的属性，使配置生效
     * @param view TimetableView
     */
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
