package com.green.kinsomy.watcher;

import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.FileWriter;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by jinsong.fu@shanbay.com on 2019/3/19.
 */
public class ErrorLogCache {
	private LinkedBlockingQueue<ErrorBean> linkedBlockingQueue;
	private final String filePath;
	private Executor executor;

	public ErrorLogCache() {
		linkedBlockingQueue = new LinkedBlockingQueue<>();
		filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "test.txt";
		executor = Executors.newCachedThreadPool();
	}

	public void addError(ErrorBean errorBean) {
		errorBean.time = DataUtils.getNowTime();
		executor.execute(new ErrorSubmitter(errorBean));
	}

	public void startErrorHandler() {
		executor.execute(new ErrorHandler());
	}

	class ErrorSubmitter implements Runnable {
		private ErrorBean bean;

		public ErrorSubmitter(ErrorBean bean) {
			this.bean = bean;
		}

		@Override
		public void run() {
			linkedBlockingQueue.add(bean);
		}
	}

	class ErrorHandler implements Runnable {

		@Override
		public void run() {
			handleError();
		}

		private void handleError() {
			while (true) {
				try {
					ErrorBean errorBean = linkedBlockingQueue.take();
					String json = Model.toJson(errorBean);
					writeFile(filePath, json);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		private boolean writeFile(String filePath, String content) {
			if (TextUtils.isEmpty(content) || TextUtils.isEmpty(filePath)) {
				return false;
			}

			boolean retVal = false;

			try (FileWriter writer = new FileWriter(filePath, true)) {
				writer.write(content);
				writer.write("\n");
				retVal = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
			// ignore
			return retVal;
		}
	}

}
