package com.qqgeogor.id3.component;

import java.util.HashMap;

/**
 * @author qqgeogor
 * this class contains training data, using hashmap to perserve attributs
 */
public class TrainingData {
	/**
	 * key is the attribute name in string type, while value contains attribute value which can be any type 
	 */
	private HashMap<String,Object> attributes = new HashMap<String, Object>();
	
	
	/**
	 * mark of a real out put class
	 */
	private OutputClass outputClass = new OutputClass();
	public OutputClass getOutputClass() {
		return outputClass;
	}
	public void setOutputClass(OutputClass outputClass) {
		this.outputClass = outputClass;
	}
	public HashMap<String, Object> getAttributes() {
		return attributes;
	}
	public void setAttributes(HashMap<String, Object> attributes) {
		this.attributes = attributes;
	}
	
	public String toString(){
		return attributes.toString()+", outputClass: "+outputClass.toString();
	}
}
