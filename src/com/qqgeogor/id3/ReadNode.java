package com.qqgeogor.id3;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import com.qqgeogor.id3.component.Node;

public class ReadNode {
	public static void main(String[] args) throws IOException, ClassNotFoundException{
		FileInputStream fis = new FileInputStream(new File("D:\\id3.id3"));
		ObjectInputStream ois = new ObjectInputStream(fis);
		Node  node = (Node)ois.readObject();
		
		System.out.println(node.getOutputClass());
		
	}
}
