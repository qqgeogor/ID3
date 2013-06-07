package com.qqgeogor.id3;

import java.util.HashMap;
import java.util.List;

import com.qqgeogor.id3.algorithm.Gain;
import com.qqgeogor.id3.component.AttributeList;
import com.qqgeogor.id3.component.Collection;
import com.qqgeogor.id3.component.Node;
import com.qqgeogor.id3.component.OutputClass;
import com.qqgeogor.id3.component.TrainingData;

public class RunGain {

	/**
	 * @param args
	 */
	
	public  TrainingData tdFactory(String age, String income,String student,String credit_rating,String outputclass){
		
		TrainingData td = new TrainingData();
		HashMap<String, Object> map=td.getAttributes();
		map.put("age", age);
		map.put("income", income);
		map.put("student", student);
		map.put("credit_rating", credit_rating);
		td.getOutputClass().setName(outputclass);
		
		return td;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RunGain rg = new RunGain();
		Collection d = new Collection();
		List<TrainingData> list = d.getList();
		TrainingData td1 = rg.tdFactory("youth","high","no","fair","no");

		TrainingData td2 = rg.tdFactory("youth","high","no","excellent","no");

		TrainingData td3 = rg.tdFactory("middle_aged","high","no","fair","yes");

		TrainingData td4 = rg.tdFactory("senior","medium","no","fair","yes");

		TrainingData td5 = rg.tdFactory("senior","low","yes","fair","yes");

		TrainingData td6 = rg.tdFactory("senior","low","yes","excellent","no");

		TrainingData td7 = rg.tdFactory("middle_aged","low","yes","excellent","yes");
		
		TrainingData td8 = rg.tdFactory("youth","medium","no","fair","no");

		TrainingData td9 = rg.tdFactory("youth","low","yes","fair","yes");

		TrainingData td10 = rg.tdFactory("senior","medium","yes","fair","yes");

		TrainingData td11 = rg.tdFactory("youth","medium","yes","excellent","yes");

		TrainingData td12 = rg.tdFactory("middle_aged","medium","no","excellent","yes");

		TrainingData td13 = rg.tdFactory("middle_aged","high","yes","fair","yes");

		TrainingData td14 = rg.tdFactory("senior","medium","no","excellent","no");
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
		
		
		ID3 id3= new ID3();	
		AttributeList res = id3.getAttributeList(d);
		System.out.println(res);
		
		Node node = id3.gernerateTree(d, id3.getAttributeList(d));
		OutputClass oc = id3.classify(td4, node);
		System.out.println(oc);
		
	}

}
