package com.green.kinsomy.watcher;

/**
 * Created by jinsong.fu@shanbay.com on 2019/3/19.
 */
public class NetworkInfo extends Model {
	public int NetworkType;//没有网络-0：WIFI网络1：4G网络-4：3G网络-3：2G网络-2

	public NetworkInfo(int networkType) {
		NetworkType = networkType;
	}
}
