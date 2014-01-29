/**
 * 
 */
package com.prodyna.pac.conference.monitoring.performance;

import java.io.Serializable;

/**
 * This class represents a monitoring entry.
 * 
 * @author Andreas Heizenreder (andreas.heizenreder@prodyna.com)
 * 
 */
public class Entry implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5220912916524784912L;

	private final String service;
	private final String method;
	private long count;
	private long sum;
	private long minTime = Long.MAX_VALUE;
	private long maxTime = Long.MIN_VALUE;

	public Entry(String service, String method) {
		super();
		this.service = service;
		this.method = method;
	}

	public void report(long time) {
		if (time < minTime) {
			minTime = time;
		}
		if (time > maxTime) {
			maxTime = time;
		}
		sum += time;
		count++;
	}

	public float getAverage() {
		if (count == 0) {
			return 0;
		}
		return (float) sum / (float) count;
	}

	public String getService() {
		return service;
	}

	public String getMethod() {
		return method;
	}

	public long getCount() {
		return count;
	}

	public long getSum() {
		return sum;
	}

	public long getMinTime() {
		return minTime;
	}

	public long getMaxTime() {
		return maxTime;
	}
}
