package com.yc.networkdemo.activity;

import org.xutils.x;
import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

import com.yc.networkdemo.R;
import com.yc.networkdemo.R.id;
import com.yc.networkdemo.R.layout;
import com.yc.networkdemo.R.menu;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

/**
 * xutil包函有View、http、image、db四大功能模块
 * @author YC2
 * @time 2017-11-18 下午2:27:09
 * TODO:
 */
@ContentView(R.layout.activity_xutil)
public class XUtilActivity extends Activity {

	private static final String TAG = "TestXUtil";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		x.view().inject(this);
	}

	@Event(type = View.OnClickListener.class,value = R.id.bt_exit)
	private void doExit(View view){
		finish();
	}
	
	@Event(type = View.OnClickListener.class,value = {R.id.bt_xutils_http_post,
		R.id.bt_xutils_http_get, R.id.bt_xutils_http_req})
	private void doXUtilsHttp(View view){
		switch (view.getId()) {
		case R.id.bt_xutils_http_get:
			Log.i(TAG, "get");
			xutilGet();
			break;
		case R.id.bt_xutils_http_post:
			Log.i(TAG, "post");
			xutilPost();
			break;
		case R.id.bt_xutils_http_req:
			xutilReq();
			break;
		default:
			break;
		}
	}
	
	private void xutilGet(){
		String url = "http://baidu.com";
		RequestParams params = new RequestParams(url);
		params.addQueryStringParameter("usename", "yc");
		params.addQueryStringParameter("psw", "123");
		x.http().get(params, new Callback.CommonCallback<String>() {

			@Override
			public void onCancelled(CancelledException arg0) {
				Log.i(TAG, "onCancelled");
			}

			@Override
			public void onError(Throwable arg0, boolean arg1) {
				Log.i(TAG, "onError");
			}

			@Override
			public void onFinished() {
				Log.i(TAG, "onFinished");
			}

			@Override
			public void onSuccess(String arg0) {
				Log.i(TAG, "onSuccess");
			}
		} );
		
	}
	
	private void xutilPost(){
		String url = "http://baidu.com";
		RequestParams params = new RequestParams(url);
		params.addBodyParameter("usename", "yc");
		params.addParameter("psw", "123");
		params.addHeader("hander", "android");
		x.http().post(params, new Callback.CommonCallback<String>() {

			@Override
			public void onCancelled(CancelledException arg0) {
				Log.i(TAG, "post onCancelled");
			}

			@Override
			public void onError(Throwable arg0, boolean arg1) {
				Log.i(TAG, "post onError");
			}

			@Override
			public void onFinished() {
				Log.i(TAG, "post onFinished");
			}

			@Override
			public void onSuccess(String arg0) {
				Log.i(TAG, "post onSuccess");
			}
		});
	}

	private void xutilReq(){
		String url = "http://baidu.com";
		RequestParams params = new RequestParams(url);
		params.addBodyParameter("usename", "yc");
		x.http().request(HttpMethod.PUT, params, new Callback.CommonCallback<String>() {

			@Override
			public void onCancelled(CancelledException arg0) {
				Log.i(TAG, "request onCancelled");
			}

			@Override
			public void onError(Throwable arg0, boolean arg1) {
				Log.i(TAG, "request onError");
			}

			@Override
			public void onFinished() {
				Log.i(TAG, "request onFinished");
			}

			@Override
			public void onSuccess(String arg0) {
				Log.i(TAG, "request onSuccess");
			}
		});
	}
}
