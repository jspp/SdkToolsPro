package com.jf.game.subPackage;

import java.util.Arrays;
import java.util.List;

/**
 * 用户状态
 * @author ouyang
 */
public enum GameClientType{
	 

	android_1("android",1),
	IOS_3 ("IOS",3);
    
	private GameClientType(String name, Integer id) {
		this.name=name;
		this.id=id;
	}
	private String  name; 
	private Integer id;
	 
    /**
     * 重写父类方法，便于程序中比较
     */
   	@Override
	public String toString() {
   		return this.id+"";
   	}
    /**
     * 通过ID 查找名字
     */
   	public static String findName(int Id){
   		GameClientType[] tyList = GameClientType.values();
   		for (int i = 0; i < tyList.length; i++) {
			if(tyList[i]!=null && tyList[i].id==Id){
				return tyList[i].name;
			}
		}
   		return "";
   	}
    /**
     * 查询所有的类型
     */
   	public List<GameClientType> findAll(){
   		GameClientType[] tyList = GameClientType.values();
   		return Arrays.asList(tyList);
   	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
