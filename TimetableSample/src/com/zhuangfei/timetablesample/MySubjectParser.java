package com.zhuangfei.timetablesample;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * 解析器
 * @author zf
 *
 */
public class MySubjectParser{

	public List<MySubject> parse(String parseString) {
		List<MySubject> courses = new ArrayList<>();
		
		try {
			JSONArray array = new JSONArray(parseString);
			for(int i=0;i<array.length();i++){
				JSONArray array2=array.getJSONArray(i);
				String term=array2.getString(0);
				String name=array2.getString(3);
				String teacher=array2.getString(8);
				if(array2.length()<=10){
					courses.add(new MySubject(term,name,null, teacher, null, -1, -1, -1, -1,null));
					continue;
				}
				String string=array2.getString(17);
				if(string!=null){
					string=string.replaceAll("\\(.*?\\)", "");
				}
				String room=array2.getString(16)+string;
				String weeks=array2.getString(11);
				int day,start,step;
				try {
					day=Integer.parseInt(array2.getString(12));
					start=Integer.parseInt(array2.getString(13));
					step=Integer.parseInt(array2.getString(14));
				} catch (Exception e) {
					day=-1;
					start=-1;
					step=-1;
				}
				
				
				courses.add(new MySubject(term,name, room, teacher, getWeekList(weeks), start, step, day, -1,null));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return courses;
	}
	
	public static List<Integer> getWeekList(String weeksString){
		List<Integer> weekList=new ArrayList<>();
		if(weeksString==null||weeksString.length()==0) return weekList;
		
		weeksString=weeksString.replaceAll("[^\\d\\-\\,]", "");
		if(weeksString.indexOf(",")!=-1){
			String[] arr=weeksString.split(",");
			for(int i=0;i<arr.length;i++){
				weekList.addAll(getWeekList2(arr[i]));
			}
		}else{
			weekList.addAll(getWeekList2(weeksString));
		}
		return weekList;
	}
	
	public static List<Integer> getWeekList2(String weeksString){
		List<Integer> weekList=new ArrayList<>();
		int first=-1,end=-1,index=-1;
		if((index=weeksString.indexOf("-"))!=-1){
			first=Integer.parseInt(weeksString.substring(0,index));
			end=Integer.parseInt(weeksString.substring(index+1));
		}else{
			first=Integer.parseInt(weeksString);
			end=first;
		}
		
		for(int i=first;i<=end;i++)
			weekList.add(i);
		return weekList;
	}

}
