package com.zhuangfei.timetable.model;

/**
 * 继承它，你可以在对象与对象之间实现切换。
 * 比如说需求是这样的：
 * 先调用TimetableView中的方法获取属性配置对象，设置一些属性后，
 * 再调用TimetableView的show()方法，使用一条链式来实现调用如下：
 * <p>
 * <pre>
 *     mTableView.config()
 *             .curWeek(1)
 *             .source(source)
 *             .callback(new ISchedule.OnItemClickListener() {
 *                  @Override
 *                  public void onItemClick(View v, List<Schedule> scheduleList) {
 *                      Log.d(TAG, "onItemClick: "+scheduleList.get(0).getName());
 *                  }
 *              })
 *              .toggle(mTableView)
 *              .showView();
 * </pre>
 */

public class ToggleEnable {
    public <T> T toggle(T obj) {
        return obj;
    }
}
