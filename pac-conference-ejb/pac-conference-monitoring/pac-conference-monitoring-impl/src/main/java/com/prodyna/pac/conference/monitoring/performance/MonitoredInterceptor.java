/**
 * 
 */
package com.prodyna.pac.conference.monitoring.performance;

import java.lang.management.ManagementFactory;
import java.lang.reflect.Method;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.management.MBeanServer;
import javax.management.MBeanServerInvocationHandler;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

import org.slf4j.Logger;

/**
 * Implementation for interseptor for collecting monitoring data.
 * 
 * @author Andreas Heizenreder (andreas.heizenreder@prodyna.com)
 * 
 */
@Monitored
@Interceptor
public class MonitoredInterceptor {

	@Inject
	private Logger log;

	private PerformanceMonitorMXBean performanceMonitor;

	/**
	 * default constructor.
	 */
	public MonitoredInterceptor() {
		try {
			MBeanServer mBeanServer = ManagementFactory
					.getPlatformMBeanServer();
			ObjectName performanceMonitorName = new ObjectName(
					PerformanceMonitorMXBean.PERFORMANCE_MONITOR_BEAN);
			performanceMonitor = MBeanServerInvocationHandler.newProxyInstance(
					mBeanServer, performanceMonitorName,
					PerformanceMonitorMXBean.class, false);
		} catch (MalformedObjectNameException e) {
			log.error("Cannot resolve performance monitor bean!");
		}

	}

	/**
	 * capture execution time of a service method call and stored it in
	 * monitoring bean.
	 * 
	 * @param ic
	 *            instance of invocation context.
	 * @return result of the execution as object.
	 * @throws Exception
	 *             for the errors while execution.
	 */
	@AroundInvoke
	public Object aroundInvoke(InvocationContext ic) throws Exception {
		long startTime = 0;
		long endTime = 0;
		startTime = System.currentTimeMillis();
		Object proceed = ic.proceed();
		endTime = System.currentTimeMillis();
		Method method = ic.getMethod();
		performanceMonitor.report(method.getDeclaringClass().getName(),
				method.getName(), endTime - startTime);
		return proceed;
	}

}
