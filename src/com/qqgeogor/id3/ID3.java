package com.qqgeogor.id3;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.qqgeogor.id3.algorithm.Gain;
import com.qqgeogor.id3.component.AttributeList;
import com.qqgeogor.id3.component.Collection;
import com.qqgeogor.id3.component.Node;
import com.qqgeogor.id3.component.OutputClass;
import com.qqgeogor.id3.component.SplittingCriterion;
import com.qqgeogor.id3.component.TrainingData;

/**
 * @author qqgeogor
 * this is a project that deals with classification problems using the simplest id3 algorithm
 * the attribute splitting method is information gain, which is nonlinear
 * it takes strings as attribute names while attribute values are not restricted so any type can be used
 * the class Output is simply an encapsulation of class name
 * the Node class is infact the tree body, containing splitting criterions, splitting outputs, leaves Node and output class. 
 */
public class ID3 {
	
	
	/**
	 * @param collection training collections
	 * @param attributeList attribute list of training datas
	 * @return
	 */
	public Node gernerateTree(Collection collection,AttributeList attributeList){
		//create a new node
		Node node = new Node();
		
		//if unit in collection is the same class return node as leaf, mark the outputclass to be this class
		try {
			boolean isSameClass = collection.isSameClass(node);
			System.out.println("isSameClass: "+isSameClass);
			if(isSameClass){
				node.setLeaf(true);
				System.out.println(node.getOutputClass());
				//node.setOutputClass()
				return node;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// if attribute list is empty, return node as leaf,mark the mostclass to be this class;
		boolean isAttributeListEmpty = attributeList.isEmpty();
		if(isAttributeListEmpty){
			node.setLeaf(true);
			OutputClass mostClass=collection.findMostClass();
			node.setMostClass(mostClass);
			node.setOutputClass(mostClass);
			System.out.println(mostClass);
			
			return node;
		}
		
		//use attribute splitting method to find a proper splitting criterion for the node
		 SplittingCriterion splittingCriterions = attributeSpliting(collection, attributeList);
		 node.setSplittingCriterions(splittingCriterions);
		 for(Object outputValue:node.getSplittingCriterions().getSplitOutput()){
			 Gain gain = new Gain();
			 Collection dj=gain.createDj(collection, node.getSplittingCriterions().getSplttingAttribute(), outputValue);
			 if(dj.isEmpty()){
				Node leaf = new Node();
				leaf.setLeaf(true);
				leaf.setOutputClass(dj.findMostClass());
				leaf.setMostClass(dj.findMostClass());
				node.getLeaves().add(leaf);
				
			 }else{
				 Node next = gernerateTree(dj, attributeList);
				 node.getLeaves().add(next);
				 
			 }
			 
			 
		 }
		 
		 
		
		
		return node;
	}
	
	public OutputClass classify(TrainingData td,Node node){
		System.out.println("start classify");
		OutputClass oc = new OutputClass();
		boolean isLeaf = node.isLeaf();
		//System.out.println(isLeaf);
		while(!isLeaf){
			String splittingAttribute = node.getSplittingCriterions().getSplttingAttribute();
			
			System.out.println("splittingAttribute in classify: "+splittingAttribute);
			Object attributeValue =td.getAttributes().get(splittingAttribute);
			int index = node.getSplittingCriterions().getSplitOutput().indexOf(attributeValue);
			node =node.getLeaves().get(index);
			isLeaf = node.isLeaf();
		}
		oc = node.getOutputClass();
		
		
		
		return oc;
	}
	
	/**
	 * @param collection Training Collection
	 * @param attributeList attribute list from training collection
	 * @return	a splitting criterion
	 * 
	 * this method create a splitting criterion which is used to generate tree bruches
	 */
	public SplittingCriterion attributeSpliting(Collection collection,AttributeList attributeList){
		SplittingCriterion sc = new SplittingCriterion();
		
		//using information gain algorithm to get the attribute with most entropy
		Gain gain = new Gain();
		List<String> attributes = attributeList.getAttributes();
		double tempValue=0;
		String tempAttribute="";
		int count=0;
		//get information gain of all attribute
		
		for(String attributeName:attributes){
			
			double gainAttribute = gain.gain(collection, attributeName);
			//for the firt time ,asign the value to temp containers, then compare the value and determine the attribute with most entropy
			if(count==0){
				tempValue = gainAttribute;
				tempAttribute = attributeName;
				count++;
			}else{
				if(gainAttribute>tempValue){
					tempValue = gainAttribute;
					tempAttribute = attributeName;
				}
				
			}
		}
		// because the method is nonlinear, attribute should be removed from attribute list
		attributeList.removeAttribute(tempAttribute);
		sc.setSplttingAttribute(tempAttribute);
		sc.setSplitOutput(gain.distinctAttributes(collection.getList(), tempAttribute));

		
		return sc;
	}
	
	/**
	 * @param collection
	 * @return attribute list
	 * 
	 * get attribute list from training data
	 */
	public AttributeList getAttributeList(Collection collection){
		AttributeList list = new AttributeList();
		list.setAttributes(new ArrayList(collection.getList().get(0).getAttributes().keySet()));
		return list;
		
	}
	
	
	/**
	 * @param node the node to be perserved
	 * @param f file destination
	 * @throws IOException
	 * 
	 * perserve descision tree to local file system
	 */
	public void perserveNode(Node node,File f) throws IOException{
		

		FileOutputStream fos = new FileOutputStream(f);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(node);
		oos.flush();
		oos.close();
	}
	
	/**
	 * @param f file destination
	 * @return descision tree node 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * 
	 * import decision tree from local file system
	 */
	public Node importNode(File f) throws IOException, ClassNotFoundException{
		FileInputStream fis = new FileInputStream(f);
		ObjectInputStream ois = new ObjectInputStream(fis);
		Node  node = (Node)ois.readObject();
		return node;
	}


	
	

	/**
	 * @param age
	 * @param income
	 * @param student
	 * @param credit_rating
	 * @param outputclass
	 * @return
	 * 
	 * 
	 * test training data factory
	 */
	protected TrainingData tdFactory(String age, String income, String student,
			String credit_rating, String outputclass) {

		TrainingData td = new TrainingData();
		HashMap<String, Object> map = td.getAttributes();
		map.put("age", age);
		map.put("income", income);
		map.put("student", student);
		map.put("credit_rating", credit_rating);
		td.getOutputClass().setName(outputclass);

		return td;
	}
	/**
	 * @param args
	 * @throws IOException
	 * 
	 * test id3
	 */
	public static void main(String[] args) throws IOException{
		
		//test 
		ID3 id3 = new ID3();
		Collection d = new Collection();
		List<TrainingData> list = d.getList();
		TrainingData td1 = id3.tdFactory("youth","high","no","fair","no");

		TrainingData td2 = id3.tdFactory("youth","high","no","excellent","no");

		TrainingData td3 = id3.tdFactory("middle_aged","high","no","fair","yes");

		TrainingData td4 = id3.tdFactory("senior","medium","no","fair","yes");

		TrainingData td5 = id3.tdFactory("senior","low","yes","fair","yes");

		TrainingData td6 = id3.tdFactory("senior","low","yes","excellent","no");

		TrainingData td7 =	id3.tdFactory("middle_aged","low","yes","excellent","yes");
		
		TrainingData td8 = id3.tdFactory("youth","medium","no","fair","no");

		TrainingData td9 = id3.tdFactory("youth","low","yes","fair","yes");

		TrainingData td10 = id3.tdFactory("senior","medium","yes","fair","yes");

		TrainingData td11 = id3.tdFactory("youth","medium","yes","excellent","yes");

		TrainingData td12 = id3.tdFactory("middle_aged","medium","no","excellent","yes");

		TrainingData td13 = id3.tdFactory("middle_aged","high","yes","fair","yes");

		TrainingData td14 = id3.tdFactory("senior","medium","no","excellent","no");
		list.add(td1);
		list.add(td2);
		list.add(td3);
		list.add(td4);
		list.add(td5);
		list.add(td6);
		list.add(td7);
		list.add(td8);
		list.add(td9);
		list.add(td10);
		list.add(td11);
		list.add(td12);
		list.add(td13);
		list.add(td14);
		Gain gain = new Gain();
		double age =gain.gain(d, "age");
		double entropy = gain.entropyAverage(d);
		System.out.println("entropy average:"+entropy);
		System.out.println("gain: "+age);
		
		
		
		AttributeList res = id3.getAttributeList(d);
		System.out.println(res);
		
		Node node = id3.gernerateTree(d, id3.getAttributeList(d));
		OutputClass oc = id3.classify(td4, node);
		System.out.println(oc);
		
		
		
	}

}
