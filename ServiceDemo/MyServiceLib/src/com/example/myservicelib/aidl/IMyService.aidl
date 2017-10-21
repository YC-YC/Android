package com.example.myservicelib.aidl;
import com.example.myservicelib.bean.Person;

/**
 * @author YC
 * @time 2017-4-20 下午3:09:02
 * TODO:
 *对于Java编程语言的基本数据类型 (int, long, char, boolean等),
 *String和CharSequence，集合接口类型List和Map，不需要import 语句
 *其它类型需要有方向指示，包括in、out和inout，in表示由客户端设置，out表示由服务端设置，inout是两者均可设置
 *
 */
interface IMyService {

	/**
	 * 
	 * @param val
	 */
	void setVal(int valInt, long valLong, char valChar, boolean valBoolean, String str);
	
	/**
	 * 
	 * @return
	 */
	int getVal();
	
	/**
	 * 保存Person
	 * @param person
	 */
	void setPerson(in Person person);
	
	/**
	 * 保存Persons
	 * @param person
	 */
	void setPersons(in List<Person> persons);
	
	/**
	 * 获取Person
	 */
	List<Person> getPersons();
	
}
