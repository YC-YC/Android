package com.example.myservicetest;

import java.io.UnsupportedEncodingException;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.myservicelib.MyManager;
import com.example.myservicelib.bean.Person;

public class MainActivity extends Activity {

	private TextView mContent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mContent = (TextView) findViewById(R.id.textView1);
	}
	
	public void doClick(View view){
		switch (view.getId()) {
		case R.id.button1:
			MyManager.getInstance().setVal(100, 1200l, 'a', true, "Hello");
			byte[] msg = null;
			try {
				msg = "我是YC123".getBytes("utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			Person person = new Person("zhangsan", "beijin", 22, msg);
			MyManager.getInstance().setPerson(person);
			List<Person> persons = MyManager.getInstance().getPersons();
			for (int i = 0; i < persons.size(); i++){
				Log.i("TestService", "get = " + persons.get(i).toString());
				try {
					Log.i("TestService", "get msg= " + new String(persons.get(i).getMsg(), "utf-8"));
				} catch (Exception e) {
				}
			}
			MyManager.getInstance().setPersons(persons);
			break;

		default:
			break;
		}
	}

}
