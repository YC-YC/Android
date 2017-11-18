package com.yc.networkdemo.activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

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
import android.widget.TextView;

public class SocketActivity extends Activity {

	private static final String TAG = "TestSocket";
	
	private TextView mContent;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_socket);
		mContent = (TextView) findViewById(R.id.tv_content);
	}

	public void doClick(View view) {
		switch (view.getId()) {
		case R.id.bt_create_socket_server:
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						ServerSocket serverSocket = new ServerSocket(3000);
						while(true){
							Socket socket = serverSocket.accept();
							new Thread(new ServiceRunnable(socket)).start();
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}).start();
			break;
		case R.id.bt_create_socket_client:
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						Socket socket = new Socket();
						socket.connect(new InetSocketAddress("127.0.0.1", 3000), 5000);
						BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
						OutputStream output = socket.getOutputStream();
						String line = null;
						while((line = reader.readLine()) != null){
							appContent("Client read data:" + line + "\n");
						}
						output.write("client 写的数据\n".getBytes("gbk"));
						output.flush();
						output.close();
						reader.close();
						socket.close();
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}).start();
			break;
		case R.id.bt_exit:
			finish();
			break;
		default:
			break;
		}
	}
	
	private void appContent(final String msg){
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				mContent.append(msg);
			}
		});
	}
	
	private class ServiceRunnable implements Runnable{

		
		Socket mSocket = null;
		
		public ServiceRunnable(Socket socket) {
			mSocket = socket;
		}
		
		@Override
		public void run() {
			try {
				appContent("\n有新的客户端接入\n");
				InputStream input = mSocket.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(input, "gbk"));
				OutputStream output = mSocket.getOutputStream();
				output.write("hello client".getBytes("gbk"));
				output.flush();
				mSocket.shutdownOutput();
				String line = null;
				while((line = reader.readLine()) != null){
					appContent("socket service get info = " + line);
				}
				output.close();
				reader.close();
				input.close();
				mSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
}
