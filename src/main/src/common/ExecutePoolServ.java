package main.src.common;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutePoolServ {
	private static ExecutorService executorService;

	static {
		executorService = Executors.newCachedThreadPool();
	}

	private ExecutePoolServ(){}

	public static ExecutorService getExecutorService() {
		return executorService;
	}
}
