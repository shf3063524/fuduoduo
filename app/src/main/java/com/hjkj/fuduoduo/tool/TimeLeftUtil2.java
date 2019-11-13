package com.hjkj.fuduoduo.tool;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeLeftUtil2 {
	
	private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
	
	public static String doCalculate(String date) {
		Date createTime;
		try {
			createTime = SDF.parse(date);
			createTime.setTime(createTime.getTime()+3600*1000*24*2);
			Date d = new Date();
			Long t = createTime.getTime() - d.getTime();
			long leftTime =	t/(1000*60);
			long day = leftTime/(24*60);
			long hours = (leftTime - day*24*60)/60;
			long minutes = leftTime - day*24*60 - hours*60;
			String result = "" + day + "天" + hours + "小时" + minutes + "分钟";
			return result;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
}
