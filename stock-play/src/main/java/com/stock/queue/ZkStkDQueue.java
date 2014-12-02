package com.stock.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.curator.ensemble.fixed.FixedEnsembleProvider;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.queue.DistributedQueue;
import org.apache.curator.framework.recipes.queue.QueueBuilder;
import org.apache.curator.retry.RetryOneTime;

import com.google.common.util.concurrent.AbstractExecutionThreadService;

public class ZkStkDQueue extends AbstractExecutionThreadService{

	DistributedQueue<ZkStkTask> queue;
	
	BlockingQueue<ZkStkTask> internalQueue = new LinkedBlockingQueue<ZkStkTask>();
	
	public void enqueueTask(String stockName) throws InterruptedException{
		internalQueue.add(new ZkStkTask(stockName));
	}
	
	protected void startUp() throws Exception{
		
		System.out.println("Startup ZkStkDQueue method...");
		
		CuratorFramework  client = CuratorFrameworkFactory.builder()
				.retryPolicy(new RetryOneTime(10)).namespace("ZkStockDqTest")
				.ensembleProvider(new FixedEnsembleProvider("localhost:2181"))
				.build();
		
		client.start();
		
		ZkStkDQConsumer consumer = new ZkStkDQConsumer();
		ZkStockDSerializer serializer = new ZkStockDSerializer();
		
		QueueBuilder<ZkStkTask> builder =  QueueBuilder.builder(client,
				consumer, serializer, "/stock");
		
		queue = builder.buildQueue();
		queue.start();		
	}	
	
	public ZkStkDQueue() throws Exception {
	}
	
	protected void shutDown() throws Exception{
		System.out.println("Shutdown method...");
		//stop();
	}	
	
	public void queueMessages() throws Exception {
		for (int i = 0; i < 10; i++) {
			ZkStkTask work = new ZkStkTask("testWork [" + i + "]");
			this.queue.put(work);
			System.out.println("Queued [" + i + "]");
		}
		Thread.sleep(5000);
	}

	@Override
	protected void run() throws Exception {
		// TODO Auto-generated method stub
		
		while(isRunning()){
			ZkStkTask zkWork = internalQueue.poll();
			if(zkWork != null){
				this.queue.put(zkWork);
				System.out.println("Task queued: " +zkWork.toString());
			}
			Thread.sleep(2000);
		}
	}	
	
}
