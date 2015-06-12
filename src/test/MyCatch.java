package test;

import java.lang.Thread.UncaughtExceptionHandler;

public class MyCatch implements UncaughtExceptionHandler{

	public MyCatch(){
		Thread.setDefaultUncaughtExceptionHandler(this);
	}
	
	public void uncaughtException(Thread t, Throwable e) {
		System.out.println("MyCatch catch it");
		System.out.println(t.getName() +",errMsg:"+ e.getMessage());
		throw new RuntimeException("uncaughtException");
	}
	
	public static void main(String[] args) throws Exception {
		
		new MyCatch();
		 
		new Thread(){
			public void run(){
				System.out.println(this.getName() + ":thread run");
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
				}
			//	try{
			//		throw new RuntimeException("thread exception");
			//	}catch(Exception err){
			//		System.out.println("thread catch it");
			//	}
			}
		}.start();
		
		Thread.sleep(2000);
		
		System.out.println("2√Î∫Û...");
		
		throw new RuntimeException("main exception");
	}

	

	
}
