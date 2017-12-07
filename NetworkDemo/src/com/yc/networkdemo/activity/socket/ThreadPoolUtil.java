/**
 * 
 */
package com.yc.networkdemo.activity.socket;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * 线程池
 * @author YC
 * @time 2017-4-12 上午10:43:08 TODO:
 */
public class ThreadPoolUtil {
	private ThreadPoolUtil() {
		throw new UnsupportedOperationException("u can't instantiate me...");
	}

	public enum Type {
		/**
		 * 固定个数，
		 * 超出的线程会在队列中等待
		 * 大小可根据 Runtime.getRuntime().availableProcessors()
		 * */
		FixedThread, 
		/**
		 * 可缓存线程池，
		 * 线程池为无限大，
		 * 当执行第二个任务时第一个任务已经完成，会复用执行第一个任务的线程，而不用每次新建线程
		 * */
		CachedThread,
		/**
		 * 单一线程
		 * 所有任务按照指定顺序(FIFO, LIFO, 优先级)执行
		 */
		SingleThread,
	}

	private ExecutorService exec;
	private ScheduledExecutorService scheduleExec;

	/**
	 * ThreadPoolUtils构造函数
	 * 
	 * @param type 线程池类型
	 * @param corePoolSize 只对Fixed和Scheduled线程池起效
	 */
	public ThreadPoolUtil(Type type, int corePoolSize) {
		// 构造有定时功能的线程池
		scheduleExec = Executors.newScheduledThreadPool(corePoolSize);
		switch (type) {
		case FixedThread:
			// 构造一个固定线程数目的线程池
			exec = Executors.newFixedThreadPool(corePoolSize);
			break;
		case SingleThread:
			// 构造一个只支持一个线程的线程池,相当于newFixedThreadPool(1)
			exec = Executors.newSingleThreadExecutor();
			break;
		case CachedThread:
			// 构造一个缓冲功能的线程池
			exec = Executors.newCachedThreadPool();
			break;
		default:
			exec = scheduleExec;
			break;
		}
	}

	/**
	 * 在未来某个时间执行给定的命令
	 * 该命令可能在新的线程、已入池的线程或者正调用的线程中执行，这由 Executor 实现决定。
	 * 
	 * @param command
	 */
	public void execute(Runnable command) {
		exec.execute(command);
	}

	/**
	 * 在未来某个时间执行给定的命令链表
	 * 该命令可能在新的线程、已入池的线程或者正调用的线程中执行，这由 Executor 实现决定。
	 * @param commands 命令链表
	 */
	public void execute(List<Runnable> commands) {
		for (Runnable command : commands) {
			exec.execute(command);
		}
	}

	/**
	 * 待以前提交的任务执行完毕后关闭线程池
	 * 启动一次顺序关闭，执行以前提交的任务，但不接受新任务。 如果已经关闭，则调用没有作用。
	 */
	public void shutDown() {
		exec.shutdown();
	}

	/**
	 * 试图停止所有正在执行的活动任务
	 * 试图停止所有正在执行的活动任务，暂停处理正在等待的任务，并返回等待执行的任务列表。
	 * 无法保证能够停止正在处理的活动执行任务，但是会尽力尝试。
	 * @return 等待执行的任务的列表
	 */
	public List<Runnable> shutDownNow() {
		return exec.shutdownNow();
	}

	/**
	 * 判断线程池是否已关闭
	 */
	public boolean isShutDown() {
		return exec.isShutdown();
	}

	/**
	 * 关闭线程池后判断所有任务是否都已完成
	 * 注意，除非首先调用 shutdown 或 shutdownNow，否则 isTerminated 永不为 true。
	 */
	public boolean isTerminated() {
		return exec.isTerminated();
	}
}
