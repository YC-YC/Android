/**
 * 
 */
package com.example.myservicelib.bean;

import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

/**
 * @author YC
 * @time 2017-8-29 上午10:28:23
 * TODO:
 * 1、实现Parcelable接口，重写writeToParcel
 * 2、创建CREATOR方法
 * 3、Person实现传入参数为Parcel的构造函数
 * 4、创建对就的aidl文件（只是个命名）
 */
public class Person implements Parcelable, IJsonConver{

	private String name;
	private String addr;
	private int age;
	private byte[] msg;
	
	public Person(){
		
	}

	
	
	/**
	 * @param name
	 * @param addr
	 * @param age
	 * @param msg
	 */
	public Person(String name, String addr, int age, byte[] msg) {
		super();
		this.name = name;
		this.addr = addr;
		this.age = age;
		this.msg = msg;
	}



	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}



	/**
	 * @return the addr
	 */
	public String getAddr() {
		return addr;
	}



	/**
	 * @return the age
	 */
	public int getAge() {
		return age;
	}



	/**
	 * @return the msg
	 */
	public byte[] getMsg() {
		return msg;
	}



	/**
	 * @return the creator
	 */
	public static Parcelable.Creator<Person> getCreator() {
		return CREATOR;
	}



	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}



	/**
	 * @param addr the addr to set
	 */
	public void setAddr(String addr) {
		this.addr = addr;
	}



	/**
	 * @param age the age to set
	 */
	public void setAge(int age) {
		this.age = age;
	}



	/**
	 * @param msg the msg to set
	 */
	public void setMsg(byte[] msg) {
		this.msg = msg;
	}



	@Override
	public int describeContents() {
		// TODO 默认实现即可
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO 注意write和read的顺序
		dest.writeString(name);
		dest.writeString(addr);
		dest.writeInt(age);
		dest.writeInt(msg.length);
		dest.writeByteArray(msg);
	}
	
	public Person(Parcel source){
		name = source.readString();
		addr = source.readString();
		age = source.readInt();
		
		byte[] _bytes = new byte[source.readInt()];
		source.readByteArray(_bytes);
		msg = _bytes;
	}
	
	@Override
	public String toString() {
		return "Person [name=" + name + ", addr=" + addr + ", age=" + age + "]";
	}
	
	public static final Parcelable.Creator<Person> CREATOR = new Parcelable.Creator<Person>() {

		@Override
		public Person createFromParcel(Parcel source) {
			return new Person(source);
		}

		@Override
		public Person[] newArray(int size) {
			return new Person[size];
		}
	};

	@Override
	public String Object2Json() {
		JSONObject object = new JSONObject();
		try {
			object.put("name", this.name);
			object.put("addr", this.addr);
			object.put("age", this.age);
			if (this.msg != null){
				object.put("msg", new String(this.msg, "utf-8"));
			}
			return object.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}



	@Override
	public Object Json2Object(String jsonStr) {
//		Person person = JSON.parseObject(jsonStr, Person.class);
		if (!TextUtils.isEmpty(jsonStr)){
			JSONObject jsonObject;
			try {
				jsonObject = new JSONObject(jsonStr);
				if (jsonObject.has("name")){
					this.setName(jsonObject.getString("name"));
				}
				if (jsonObject.has("addr")){
					this.setAddr(jsonObject.getString("addr"));
				}
				if (jsonObject.has("age")){
					this.setAge(jsonObject.getInt("age"));
				}
				if (jsonObject.has("msg")){
					this.setMsg(jsonObject.getString("msg").getBytes("utf-8"));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return this;
	}
}
