package com.stock.queue;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.queue.QueueConsumer;
import org.apache.curator.framework.state.ConnectionState;

public class ZkStkDQConsumer implements QueueConsumer<ZkStkTask> {

	public void stateChanged(CuratorFramework framework, ConnectionState state) {
		System.out.println("State [" + state + "]");
		
	}

	public void consumeMessage(ZkStkTask work) throws Exception {
		System.out.println("Consuming (" + work + ")");		
	}

}
