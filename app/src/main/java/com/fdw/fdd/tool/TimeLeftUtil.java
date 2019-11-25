package com.fdw.fdd.tool;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TimeLeftUtil {

	private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");

	public static String doCalculate(String date) {
		Date createTime;
		try {
			createTime = SDF.parse(date);
			createTime.setTime(createTime.getTime()+3600*1000);
			Date d = new Date();
			Long t = createTime.getTime() - d.getTime();
			String result = ((Long)(t/(1000*60))).toString();
			return result;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}
