//package com.yc.networkdemo.activity;
//
//import org.apache.http.Header;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.view.View;
//
//import com.yc.networkdemo.R;
//
//public class AsyncHttpClientActivity extends Activity {
//
//	/** 官网建议只有一个对象 */
//	private static AsyncHttpClient client = new AsyncHttpClient();
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_async);
//	}
//
//	public void doClick(View view) {
//		switch (view.getId()) {
//		case R.id.bt_async_get:
//			client.get("https://www.baidu.com", new AsyncHttpResponseHandler() {
//				@Override
//				public void onStart() {
//					// Initiated the request
//				}
//
//				@Override
//				public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers,
//						byte[] responseBody) {
//					// Successfully got a response
//				}
//
//				@Override
//				public void onFailure(int statusCode, Header[] headers,
//						byte[] responseBody, Throwable error) {
//					// Response failed :(
//				}
//
//				@Override
//				public void onRetry(int retryNo) {
//					// Request was retried
//				}
//
//				@Override
//				public void onProgress(long bytesWritten, long totalSize) {
//					// Progress notification
//				}
//
//				@Override
//				public void onFinish() {
//					// Completed the request (either success or failure)
//				}
//			});
//			break;
//		case R.id.bt_async_post:
//			break;
//		case R.id.bt_exit:
//			finish();
//			break;
//		default:
//			break;
//		}
//	}
//}
