package com.stock.queue;

public class ZkStkTask {
	private String work;

	public ZkStkTask(String work){
		this.work = work;
	}
	
	public String toString(){
		return this.work;
	}
}
