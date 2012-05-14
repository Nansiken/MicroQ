package javacom;

import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.Semaphore;

import com.ericsson.otp.erlang.*;

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
	static OtpMbox mbox;
	static OtpErlangPid erlNodePID;
	
	
	public static QueueManager queue;
	public static StudentSpawner spawner;	
	public static Communicator com;
	
	public static Semaphore queue_sem;
	public static Semaphore microwave_sem;
	
	private static int spawnRate = 50;
	private static int switchRate = 50; 

	
	
	
	private static void establishConnection() {
		try {
			OtpErlangObject objectPID;
			erlNodeName = "erlcom@" + InetAddress.getLocalHost().getHostName();
			javaNodeName = "javacom@" + InetAddress.getLocalHost().getHostName();
			javaNode = new OtpNode(javaNodeName);
			mbox = javaNode.createMbox("mbox");
			
			objectPID = mbox.receive();
			erlNodePID = (OtpErlangPid) objectPID;
			
			/*
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
			*/
		
			System.out.println("Connection to erlcom established");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (OtpErlangExit e) {
			e.printStackTrace();
		} catch (OtpErlangDecodeException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * @return 
	 * 
	 */
	static void init(int microwaveAmount) {
		establishConnection();
		queue_sem = new Semaphore(0);
		microwave_sem = new Semaphore(microwaveAmount);
		queue = new QueueManager();
		spawner = new StudentSpawner(spawnRate, switchRate );
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		init(3);	
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
