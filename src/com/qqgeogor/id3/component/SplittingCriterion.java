package com.qqgeogor.id3.component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SplittingCriterion implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1855392326146582944L;
	private String splttingAttribute;
	public String getSplttingAttribute() {
		return splttingAttribute;
	}
	public void setSplttingAttribute(String splttingAttribute) {
		this.splttingAttribute = splttingAttribute;
	}
	
	

	public List<Object> getSplitOutput() {
		return splitOutput;
	}
	public void setSplitOutput(List<Object> splitOutput) {
		this.splitOutput = splitOutput;
	}



	private List<Object> splitOutput  = new ArrayList<Object>();
}
