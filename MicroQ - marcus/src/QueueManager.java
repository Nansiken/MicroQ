import java.util.concurrent.ConcurrentLinkedQueue;


public class QueueManager extends Thread implements Runnable{
	

	
	public static ConcurrentLinkedQueue<Student> queue = new ConcurrentLinkedQueue<Student>();
	
	
	public void checkReady(){
		if(queue.peek().waitingTime == 0){
			queue.add(queue.poll());
		}		
	}
	
	public void run(){

			try {
				for(int i = 0; i < 10; i++){
				Thread.sleep(1000);
				System.out.println(Thread.currentThread().getName());
				}
			} catch (InterruptedException e) {}
			Simulation.queue_sem.release()
	}
	/*
	public static void main(String[] args)
	{
			
			QueueManager trad = new QueueManager();
			Thread listener = new Thread(trad);
			listener.setName("Lyssnar");
			listener.start();
			StudentSpawner std1 = new StudentSpawner();
			StudentSpawner std2 = new StudentSpawner();
			StudentSpawner std3 = new StudentSpawner();
			StudentSpawner std4 = new StudentSpawner();
			System.out.println(queue);	
			trad.checkReady();
			System.out.println(queue);
		
	}
	*/

	public void grabMicrowave() {
		// TODO Auto-generated method stub
		
	}
}
