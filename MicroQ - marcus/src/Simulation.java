import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.Semaphore;

import com.ericsson.otp.erlang.OtpNode;

/**
 * 
 */

/**
 * @author Simon
 *
 */
public class Simulation {
	
	static String erlNodeName;
	static String javaNodeName;
	static OtpNode javaNode;
	static QueueManager queue;
	public static StudentSpawner spawner;
	
	public static Semaphore queue_sem;
	public static Semaphore microwave_sem;
	
	private static int spawnRate = 50; 
	
	
	
	private static int establishConnection() {
		try {
			erlNodeName = "erlcom@" + InetAddress.getLocalHost().getHostName();
			javaNodeName = "javacom@" + InetAddress.getLocalHost().getHostName();
			javaNode = new OtpNode(javaNodeName);
			int i = 1;
			while(!javaNode.ping("erlcom", 1000))
			{
				System.out.println("Connection attempt " + i + " to erlcom failed...");
				i++;
				if(i > 5)
					break;
			}
			if(i >= 5)
			{
				System.out.println("Connection to erlcom failed...");
				return 1;
			}
			System.out.println("Connection to erlcom established");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * @return 
	 * 
	 */
	static int init(int microwaveAmount) {
		int retCode = establishConnection();
		queue_sem = new Semaphore(0);
		microwave_sem = new Semaphore(microwaveAmount);
		queue = new QueueManager();
		spawner = new StudentSpawner(spawnRate);
		return retCode;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int errCode = init(3);
		if(errCode == 0)
		{
			System.out.println("Setup done...");
		}
		else
		{
			System.out.println("Setup Error, code: " + errCode);
			System.exit(0);
		}
		runSimulation();
		
		
	}

	private static void runSimulation() {
		while(true)
		{
			try {
				microwave_sem.acquire();
				queue_sem.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			queue.grabMicrowave();
		}
		
	}

}
