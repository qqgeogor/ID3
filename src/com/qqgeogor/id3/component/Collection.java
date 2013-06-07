package com.qqgeogor.id3.component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class Collection {

	private List<TrainingData> list= new ArrayList<TrainingData>();
	public boolean isEmpty(){
		return list.isEmpty();
	}
	
	/**
	 * @param currentNode current node
	 * @return	if all unit in collection has the same class
	 * @throws Exception
	 */
	public boolean isSameClass(Node currentNode) throws Exception {
		// TODO Auto-generated method stub
		if(list.size()==0){
			System.out.println("no data in the collection");
			throw new Exception("no data in the collection");
		}else if(list.size()==1){
			OutputClass outputClass = this.list.get(0).getOutputClass();
			currentNode.setOutputClass(outputClass);
			return true;
		}else{
			OutputClass temp = list.get(0).getOutputClass();
			for(TrainingData td:list){
				if(!td.getOutputClass().equals(temp)){
					return false;
				}
			}
			currentNode.setOutputClass(temp);
			return true;
		}
		
		
		
	}
	public List<TrainingData> getList() {
		return list;
	}
	public void setList(List<TrainingData> list) {
		this.list = list;
	}
	
	public HashMap count(){
		HashMap<String, Integer> temp = new HashMap<String, Integer>();
		for(TrainingData td:list){
			String name=td.getOutputClass().getName();
			if(temp.containsKey(name)){
				temp.put(name, temp.get(name)+1);
			}else{
				temp.put(name,1);
			}
		}
		return temp;
	}
	/**
	 * @return the most class in the collection
	 */
	public OutputClass findMostClass() {
		// TODO Auto-generated method stub
		
		HashMap temp = this.count();
		List<Entry<String, Integer>> entries = new ArrayList<Entry<String, Integer>>(temp.entrySet());
		Collections.sort(entries,new Comparator<Entry<String, Integer>>() {

			@Override
			public int compare(Entry<String, Integer> o1, Entry<String, Integer>o2) {
				// TODO Auto-generated method stub
				return o1.getValue().compareTo(o2.getValue());
			}
			
		});
		
		
		return new OutputClass(entries.get(entries.size()-1).getKey());
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return list.toString();
	}

}
