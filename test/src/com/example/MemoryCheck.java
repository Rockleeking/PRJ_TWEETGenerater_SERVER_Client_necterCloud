package com.example;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.Scanner;

public class MemoryCheck {

    private Runtime runtime = Runtime.getRuntime();

    public void MemInfo() throws InterruptedException 
    {
    	Thread.sleep(400);
    	System.out.println("		THE MEMORY USAGE IS GIVEN BELOW");
    	System.out.println("------------------------------------------------------------");
    	Thread.sleep(400);
    	MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
    	System.out.println(String.format("Initial memory: %.2f GB", 
    		  (double)memoryMXBean.getHeapMemoryUsage().getInit() /1073741824));
    		System.out.println(String.format("Used heap memory: %.2f GB", 
    		  (double)memoryMXBean.getHeapMemoryUsage().getUsed() /1073741824));
    		System.out.println(String.format("Max heap memory: %.2f GB", 
    		  (double)memoryMXBean.getHeapMemoryUsage().getMax() /1073741824));
    		System.out.println(String.format("Committed memory: %.2f GB", 
    		  (double)memoryMXBean.getHeapMemoryUsage().getCommitted() /1073741824));
    }
    public void CPUUsage() throws InterruptedException
    {
    	Thread.sleep(400);
    	System.out.println("		THE CPU USAGE IS GIVEN BELOW");
    	System.out.println("------------------------------------------------------------");
    	Thread.sleep(400);
    	ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
    	 
    	for(Long threadID : threadMXBean.getAllThreadIds()) {
    	    ThreadInfo info = threadMXBean.getThreadInfo(threadID);
    	    System.out.println("Thread name: " + info.getThreadName());
    	    System.out.println("Thread State: " + info.getThreadState());
    	    System.out.println(String.format("CPU time: %s ns", 
    	      threadMXBean.getThreadCpuTime(threadID)));
    	  }
    }

    public void DiskInfo() throws InterruptedException {
    	Thread.sleep(400);
    	System.out.println("		THE DISK USAGE IS GIVEN BELOW");
    	System.out.println("------------------------------------------------------------");
    	Thread.sleep(400);
        /* Get a list of all filesystem roots on this system */
        File[] roots = File.listRoots();
        StringBuilder sb = new StringBuilder();

        /* For each filesystem root, print some info */
        for (File root : roots) {
        	System.out.println(String.format("Total space: %.2f GB", 
        	  (double)root.getTotalSpace() /1073741824));
        	System.out.println(String.format("Free space: %.2f GB", 
        	  (double)root.getFreeSpace() /1073741824));
        	System.out.println(String.format("Usable space: %.2f GB", 
        	  (double)root.getUsableSpace() /1073741824));
        }
    }
    public static void main(String[] args) throws InterruptedException {
    	MemoryCheck m=new MemoryCheck();
    	checkCard();
	}

	private static void checkCard() throws InterruptedException {
		System.out.println("------------------------------------------------------------------------");
		System.out.println();
		System.out.println("                                   ACTION"+"                              ");
		System.out.println("1. MONITOR YOUR DISK USAGE");
		System.out.println("2. MONITOR YOUR MEMORY USAGE");
		System.out.println("3. MONITOR YOUR CPU USAGE");

		System.out.println("4. END THIS QUERY");
		System.out.println();
		System.out.println("-----------------------------------------------------------------------");
		System.out.println();
		MemoryCheck m=new MemoryCheck();
		Scanner sc=new Scanner(System.in);
		int ch=sc.nextInt();
		switch (ch) {
		case 1: 
			Thread.sleep(300);
			m.DiskInfo();
			checkCard();
			break;

		case 2: 
			Thread.sleep(300);
			m.MemInfo();
			checkCard();
			break;

		case 3: 
			Thread.sleep(300);
			m.CPUUsage();
			checkCard();
			break;
//
//		case 1: 
//			Thread.sleep(300);
//			m.DiskInfo();
//			break;
//
//		case 1: 
//			Thread.sleep(300);
//			m.DiskInfo();
//			break;
//
//		case 1: 
//			Thread.sleep(300);
//			m.DiskInfo();
//			break;
		case 4:
			Thread.sleep(300);
			System.out.println("			THANK YOU");
			System.out.println("-------------------------------------");
			break;
		default:
			System.out.println("Enter a Valid Input!!");
			checkCard();
			break;
		}
		
	}
    
}