package com.qqgeogor.id3.component;

import java.io.Serializable;

/**
 * @author qqgeogor
 * this class is used to encapsulate names of the output  class
 */
public class OutputClass implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3404168392286957507L;
	
	private String name;

	public OutputClass(String name) {
		// TODO Auto-generated constructor stub
		this.name = name;
	}
	public OutputClass(){};

	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "OutputClass:"+name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(obj.getClass().equals(this.getClass())){
			return this.getName().equals(((OutputClass)obj).getName());
		}else{
			return super.equals(obj);
		}
	}
}
