package com.green.kinsomy.watcher;

import android.content.Context;

/**
 * Created by jinsong.fu@shanbay.com on 2019/3/19.
 */
public class ErrorBean extends Model {
	public ErrorBean(Context context) {
		this.networkInfo = new NetworkInfo(NetWorkUtils.getAPNType(context));
	}

	public String time;//错误发生时间
	public RequestBean request;
	public ResponseBean response;
	private NetworkInfo networkInfo;
}

