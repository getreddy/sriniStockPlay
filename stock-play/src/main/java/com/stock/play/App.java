package com.stock.play;

import com.stock.queue.ZkStkDQueue;

/**
 * Hello world!
 *
 */
public class App 
{
	
	
	public void testDistributedQueue(String stkName) throws Throwable {
		ZkStkDQueue queuer = new ZkStkDQueue();
		queuer.startAsync();
    	//queuer.queueMessages();
		queuer.enqueueTask(stkName);
		
    }
	
    public static void main( String[] args ) throws Throwable
    {
    	String stkName = "";
    	if(args != null && args.length > 0)
    		stkName = args[0];
    	if(stkName.isEmpty()){
    		System.out.println("Please send stock name as argument..");
    		return;
    	}
    		
    	App obj = new App();
    	obj.testDistributedQueue(stkName);
        System.out.println( "Srini Stock World!" );
    }
}
