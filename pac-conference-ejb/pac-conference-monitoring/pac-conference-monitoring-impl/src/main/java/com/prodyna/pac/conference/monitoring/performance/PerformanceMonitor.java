/**
 * 
 */
package com.prodyna.pac.conference.monitoring.performance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Implementation for performance monitor.
 * 
 * @author Andreas Heizenreder (andreas.heizenreder@prodyna.com)
 * 
 */
public class PerformanceMonitor implements PerformanceMonitorMXBean {

	private HashMap<String, Entry> entries = new HashMap<String, Entry>();

	/**
	 * 
	 */
	public PerformanceMonitor() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.monitoring.performance.PerformanceMonitorMXBean
	 * #reset()
	 */
	@Override
	public void reset() {
		entries.clear();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.monitoring.performance.PerformanceMonitorMXBean
	 * #report(java.lang.String, java.lang.String, long)
	 */
	@Override
	public void report(String service, String method, long time) {
		String key = service + ":" + method;
		Entry entry;
		entry = entries.get(key);
		if (entry == null) {
			entry = new Entry(service, method);
			entries.put(key, entry);
		}
		entry.report(time);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.monitoring.performance.PerformanceMonitorMXBean
	 * #getAll()
	 */
	@Override
	public List<Entry> getAll() {
		return new ArrayList<Entry>(entries.values());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.monitoring.performance.PerformanceMonitorMXBean
	 * #getCount()
	 */
	@Override
	public int getCount() {
		return entries.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.monitoring.performance.PerformanceMonitorMXBean
	 * #getWorstByTime()
	 */
	@Override
	public Entry getWorstByTime() {
		long worstTimeSum = -1;
		Entry worstEntry = null;
		
		for (Entry entry:entries.values()){
			if ( worstTimeSum < entry.getSum()){
				worstTimeSum = entry.getSum();
				worstEntry = entry;
			}
		}
		
		return worstEntry;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.monitoring.performance.PerformanceMonitorMXBean
	 * #getWorstByAverage()
	 */
	@Override
	public Entry getWorstByAverage() {
		float worstTimeAvg = -1;
		Entry worstEntry = null;
		
		for (Entry entry:entries.values()){
			if ( worstTimeAvg < entry.getAverage()){
				worstTimeAvg = entry.getAverage();
				worstEntry = entry;
			}
		}
		
		return worstEntry;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.monitoring.performance.PerformanceMonitorMXBean
	 * #getWorstByCount()
	 */
	@Override
	public Entry getWorstByCount() {
		long worstCount = -1;
		Entry worstEntry = null;
		
		for (Entry entry:entries.values()){
			if ( worstCount < entry.getSum()){
				worstCount = entry.getCount();
				worstEntry = entry;
			}
		}
		
		return worstEntry;
	}

}
