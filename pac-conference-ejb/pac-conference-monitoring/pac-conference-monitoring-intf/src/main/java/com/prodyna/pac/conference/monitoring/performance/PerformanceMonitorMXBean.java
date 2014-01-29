/**
 * 
 */
package com.prodyna.pac.conference.monitoring.performance;

import java.util.List;

/**
 * Interface for MXBean to collect performance information from application.
 * 
 * @author Andreas Heizenreder (andreas.heizenreder@prodyna.com)
 * 
 */
public interface PerformanceMonitorMXBean {

	/**
	 * name for monitoring bean.
	 */
	public static String PERFORMANCE_MONITOR_BEAN = "com.prodyna.pac.conference:service=PerformanceMonitor";

	/**
	 * reset current collected data.
	 */
	public void reset();

	/**
	 * register new monitoring name for service and method.
	 * 
	 * @param service
	 *            a String as a name for service.
	 * @param method
	 *            a String as a name for monitored method.
	 * @param time
	 *            number of milliseconds elapsed by method call.
	 */
	public void report(String service, String method, long time);

	/**
	 * get all monitorings entries.
	 * 
	 * @return a List instance with all collected monitoring entries.
	 */
	public List<Entry> getAll();

	/**
	 * get the number of monitored service methods.
	 * 
	 * @return the number of monitored service methods.
	 */
	public int getCount();

	/**
	 * get the entry with the worst response time.
	 * 
	 * @return the entry with the worst response time.
	 */
	public Entry getWorstByTime();

	/**
	 * get the entry with the worst average response time.
	 * 
	 * @return the entry with the worst average response time.
	 */
	public Entry getWorstByAverage();

	/**
	 * get the entry with the most methods calls.
	 * 
	 * @return the entry with the most methods call.
	 */
	public Entry getWorstByCount();

}
