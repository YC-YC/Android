package com.yc.networkdemo.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.Header;
import org.xutils.x;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.yc.networkdemo.R;

@ContentView(R.layout.activity_async)
public class AsyncHttpClientActivity extends Activity {

	protected static final String TAG = "TestAsync";
	/** 官网建议只有一个对象 */
	private static AsyncHttpClient client = new AsyncHttpClient();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		x.view().inject(this);
	}

	@Event(type = View.OnClickListener.class,value = R.id.bt_exit)
	private void doExit(View view){
		finish();
	}
	
	@Event(type = View.OnClickListener.class,value = {R.id.bt_async_get,
		R.id.bt_async_post})
	private void doClick(View view) {
		switch (view.getId()) {
		case R.id.bt_async_get:
			testAsyncGet();
			break;
		case R.id.bt_async_post:
			testAsyncPost();
			break;
		default:
			break;
		}
	}

	/**
	 * 
	 */
	private void testAsyncPost() {
		RequestParams params = new RequestParams();
		params.put("username", "yc");
		params.put("psw", "123");
		params.put("upload_file", "/mnt/sdcard/123.png");
//		params.put(key, stream)
		
		Map<String, String> map = new HashMap<String, String>();  
		 map.put("first_name", "James");  
		 map.put("last_name", "Smith");  
		 params.put("user", map);// url params: "user[first_name]=James&user[last_name]=Smith"
	
		 Set<String> set = new HashSet<String>(); // unordered collection  
		 set.add("music");  
		 set.add("art"); 
		 params.put("like", set);// url params: "like=music&like=art"  
	
		 List<String> list = new ArrayList<String>(); // Ordered collection  
		 list.add("Java");  
		 list.add("C");  
		 params.put("language", list);//url params: "languages[]=Java&languages[]=C"
		 
		 client.post("https://www.baidu.com", new AsyncHttpResponseHandler(){

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				super.onFailure(arg0, arg1, arg2, arg3);
				Log.i(TAG, "post onFailure");
			}

			@Override
			public void onFinish() {
				super.onFinish();
				Log.i(TAG, "post onFinish");
			}

			@Override
			public void onProgress(int bytesWritten, int totalSize) {
				super.onProgress(bytesWritten, totalSize);
				Log.i(TAG, "post onProgress");
			}

			@Override
			public void onStart() {
				super.onStart();
				Log.i(TAG, "post onStart");
			}
			
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				super.onSuccess(arg0, arg1, arg2);
				Log.i(TAG, "post onSuccess");
			}
		});
	}

	/**
	 * 
	 */
	private void testAsyncGet() {
		Log.i(TAG, "testAsyncGet");
		client.get("https://www.baidu.com", new AsyncHttpResponseHandler(){

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				super.onFailure(arg0, arg1, arg2, arg3);
				Log.i(TAG, "onFailure");
			}

			@Override
			public void onFinish() {
				super.onFinish();
				Log.i(TAG, "onFinish");
			}

			@Override
			public void onProgress(int bytesWritten, int totalSize) {
				super.onProgress(bytesWritten, totalSize);
				Log.i(TAG, "onProgress");
			}

			@Override
			public void onStart() {
				super.onStart();
				Log.i(TAG, "onStart");
			}
			
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				super.onSuccess(arg0, arg1, arg2);
				Log.i(TAG, "onSuccess");
			}
		});
	}
}
