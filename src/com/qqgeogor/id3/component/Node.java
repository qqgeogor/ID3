package com.qqgeogor.id3.component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Node implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4519209813918279240L;
	private SplittingCriterion splittingCriterions;
	private List<Node> leaves = new ArrayList<Node>();
	private OutputClass outputClass;
	private boolean isLeaf=false;
	private OutputClass mostClass;
	
	public List<Node> getLeaves() {
		return leaves;
	}
	public void setLeaves(List<Node> leaves) {
		this.leaves = leaves;
	}
	public OutputClass getOutputClass() {
		return outputClass;
	}
	public void setOutputClass(OutputClass outputClass) {
		this.outputClass = outputClass;
	}
	public boolean isLeaf() {
		return isLeaf;
	}
	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}
	public OutputClass getMostClass() {
		return mostClass;
	}
	public void setMostClass(OutputClass mostClass) {
		this.mostClass = mostClass;
	}
	public SplittingCriterion getSplittingCriterions() {
		return splittingCriterions;
	}
	public void setSplittingCriterions(SplittingCriterion splittingCriterions) {
		this.splittingCriterions = splittingCriterions;
	}
}
