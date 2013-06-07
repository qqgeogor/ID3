package com.qqgeogor.id3.algorithm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.qqgeogor.id3.component.Collection;
import com.qqgeogor.id3.component.TrainingData;

public class Gain implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2163104140242106662L;

	/**
	 * @param d
	 *            input of log2()function
	 * @return calculation result of log2() function
	 */
	public double log2(double d) {
		return Math.log(d) / Math.log(2);
	}

	/**
	 * @param d
	 *            Training Collection
	 * @return entropy value
	 * 
	 * 
	 *         this method calculate the entropy of the whole Training
	 *         Collection d and return the entropy as result
	 */
	public double entropyAverage(Collection d) {
		double entropy = 0.0;
		HashMap<String, Integer> temp = d.count();
		// System.out.println(temp);
		double length = d.getList().size();

		for (Entry<String, Integer> entry : temp.entrySet()) {

			double sum = log2(entry.getValue() / length)
					* (-1 * (entry.getValue() / length));
			// System.out.println("sum:"+sum);
			entropy += sum;
		}

		// System.out.println("entropy average: "+entropy);
		return entropy;
	}

	/**
	 * @param tds
	 *            List of training Data
	 * @param attributeName
	 *            the attribute name to be specified
	 * @return a list of attribute value of a specified attribute name column
	 */
	public List distinctAttributes(List<TrainingData> tds, String attributeName) {
		List list = new ArrayList();
		// gey all rows of attibute values of a specified attribute column
		for (TrainingData td : tds) {
			Object attributeValue = td.getAttributes().get(attributeName);
			if (!list.contains(attributeValue)) {
				list.add(attributeValue);
			}
		}

		return list;
	}

	/**
	 * @param d
	 *            Training Collection
	 * @param attributeName
	 *            attributeName the attribute name to be specified
	 * @return information gain of the system specified by the attribute name
	 */
	public double gain(Collection d, String attributeName) {
		double gain = this.entropyAverage(d)
				- this.entropyAtribute(d, attributeName);
		return gain;
	}


	/**
	 * @param d
	 *            Training Collection
	 * @param attributeName
	 *            attributeName the attribute name to be specified
	 * @param value
	 * @return a sub-collection of Training Collection, which satisfied
	 */
	public Collection createDj(Collection d, String attributeName, Object value) {
		Collection dj = new Collection();
		List<TrainingData> djList = dj.getList();
		// find djList satisfy the specified attribute value
		List<TrainingData> dList = d.getList();
		for (TrainingData td : dList) {
			boolean isSatisfy = td.getAttributes().get(attributeName)
					.equals(value);
			if (isSatisfy) {
				djList.add(td);
			}

		}
		return dj;
	}

	/**
	 * @param d
	 *            Training Collection
	 * @param attributeName
	 *            attributeName the attribute name to be specified
	 * @return entropy of the input attribute 
	 */
	public double entropyAtribute(Collection d, String attributeName) {
		double entropy = 0.0;
		List<TrainingData> list = d.getList();

		List attributeValues = distinctAttributes(list, attributeName);

		for (Object value : attributeValues) {

			Collection dj = this.createDj(d, attributeName, value);
			List<TrainingData> djList = dj.getList();
			List<TrainingData> dList = d.getList();
			// System.out.println(djList);
			// System.out.println("----------------");
			double length = dList.size();
			double dj_d = djList.size() / length;

			entropy += dj_d * entropyAverage(dj);
		}

		// System.out.println("entroy "+attributeName+": "+entropy);

		return entropy;
	}
}
