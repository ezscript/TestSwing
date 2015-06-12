package test;
/**
 * 用于同步性多延时操作，如智能提示
 * 
 * @author MaXin
 */
public class DelayThread extends Thread {
	
	private boolean deepSleep = true;
	private long deepSleepTime = 100;
	private long targetTime = 0;
	DelayMethod delayMethod = null;
	private boolean breakWhile = false;
	
	public interface DelayMethod{
		public void run();
		
	}
	
	public DelayThread(DelayMethod delayMethod){
		this.delayMethod = delayMethod;
		this.start();
	}
	
	public DelayThread(DelayMethod delayMethod,long deepSleepTime){
		this(delayMethod);
		if(deepSleepTime <=0 ){
			throw new RuntimeException("sleepTime couldn't < 0");
		}
		this.deepSleepTime = deepSleepTime;
	}

	public void run() {
		while(true){
			try{
				if(breakWhile){
					break;
				}
				if(deepSleep){
					Thread.sleep(deepSleepTime);
					continue;
				}else if(System.currentTimeMillis() < targetTime  ){
					Thread.sleep(  targetTime - System.currentTimeMillis());
					continue;
				}else{
					delayMethod.run();
					reset();
				}
			}catch(Exception e){
				e.printStackTrace();
				reset();
			}
		}//end while
	}//end run
	

	private void reset() {
		deepSleep = true;
		targetTime = System.currentTimeMillis() + 1000;
	}


	public boolean isDeepSleep() {
		return deepSleep;
	}
	
	public void doDlay(long delayTime){
		this.targetTime =  System.currentTimeMillis() + delayTime;
		deepSleep = false;
	}

	public void doNothing(){
		deepSleep = true;
	}
	
	public void releaseThread(){
		breakWhile = true;
	}
	
	public static void main(String [] args){
		DelayThread d = new DelayThread(
			//实现接口
			new DelayThread.DelayMethod(){
				public void run(){
					System.out.println("xxxxx");
				}
			}
		);
		
		
		for(int i = 1 ; i<= 3; i++ ){
			//在xx时间后启动方法
			d.doDlay( i * 1000);	
		}
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//温和的释放运行中的线程
		d.releaseThread();
	}
}

