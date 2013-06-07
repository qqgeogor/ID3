package com.qqgeogor.id3.component;

import java.util.ArrayList;
import java.util.List;

public class AttributeList {
	private List<String> attributes=new ArrayList<String>();
	public void removeAttribute(String attribute){
		
		
		attributes.remove(attribute);
	}
	public List<String> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<String> attributes) {
		this.attributes = attributes;
	}

	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return attributes.isEmpty();
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return attributes.toString();
	}
}
