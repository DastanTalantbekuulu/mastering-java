package com.programs.challenges.socketprog.parallec;

import java.util.HashMap;
import java.util.Map;

import io.parallec.core.ParallecResponseHandler;
import io.parallec.core.ParallelClient;
import io.parallec.core.ParallelTask;
import io.parallec.core.ResponseOnSingleTask;
import io.parallec.core.util.PcStringUtils;

public class TcpApp {

	public static void main(String[] args) {

		TcpApp app = new TcpApp();
		app.executeTask("First Task");
		app.executeTask("Second Task");
		app.executeTask("Third Task");

	}
	
	private void executeTask(String requestMonitor) {
		ParallelClient pc = new ParallelClient();
		Map<String, Object> responseContext = new HashMap<>();
		responseContext.put("temp", null);
		ParallelTask task = pc.prepareTcp(requestMonitor).async().setSaveResponseToTask(true)
		.setTargetHostsFromString("localhost")
		.setTcpPort(10081)
		.setTcpConnectTimeoutMillis(1000)
		.setResponseContext(responseContext)
		.setConcurrency(100)
		.execute(new ParallecResponseHandler() {

			@Override
			public void onCompleted(ResponseOnSingleTask res, Map<String, Object> responseContext) {
				System.out.println("Responose:" + res.getResponseContent() + " host: " + res.getHost() + " errmsg: "
						+ res.getErrorMessage());

				System.out.println("Response Context: " + responseContext);
			}
		});
		
		while (!task.isCompleted()) {
			try {
				Thread.sleep(100L);
				System.out.println(String.format("POLL_JOB_PROGRESS (%.5g%%)  PT jobid: %s", task.getProgress(),
						task.getTaskId()));
				pc.logHealth();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		System.out.println("Result Summary\n " + PcStringUtils.renderJson(task.getAggregateResultFullSummary()));

		System.out.println("Result Brief Summary\n " + PcStringUtils.renderJson(task.getAggregateResultCountSummary()));
		
		System.out.println("Task Response\n "+task.getResponseContext());
		pc.releaseExternalResources();
	}

}
