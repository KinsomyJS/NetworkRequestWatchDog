package com.green.kinsomy.watcher;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by jinsong.fu@shanbay.com on 2019/3/20.
 */
public class DataUtils {
	String pattern;
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);

	public static String getNowTime() {
		Date date = new Date(System.currentTimeMillis());
		return DATE_FORMAT.format(date);
	}
}
