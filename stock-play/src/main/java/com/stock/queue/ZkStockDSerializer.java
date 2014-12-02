package com.stock.queue;

import org.apache.curator.framework.recipes.queue.QueueSerializer;

public class ZkStockDSerializer implements QueueSerializer<ZkStkTask>{

	public byte[] serialize(ZkStkTask work) {
		return work.toString().getBytes();
	}

	public ZkStkTask deserialize(byte[] buffer) {
		String work = new String(buffer);
		return new ZkStkTask(work);
	}

}
