package javacom;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ConcurrentLinkedQueue;
import com.ericsson.otp.erlang.*;



public class QueueManager {
	
	public static String erlNode;
	public static String javaNode;
	public static ConcurrentLinkedQueue<Student> Queue;
	
	public static void init() throws IOException, OtpAuthException {
		OtpSelf client;
		OtpPeer server;
		OtpConnection connection;
		
		client = new OtpSelf("javacom");
		server = new OtpPeer(erlNode);
		connection = client.connect(server);
		
		connection.sendRPC("hello", "hej", new OtpErlangObject[] {});
		try {
			OtpErlangObject response = connection.receiveMsg().getMsg();
			System.out.println(response.toString());
		} catch (OtpErlangDecodeException e) {
			e.printStackTrace();
		} catch (OtpErlangExit e) {
			e.printStackTrace();
		}
		
		
	}
	
	public static void addStudent(Student s)
	{
		
		Queue.add(s);
		Simulation.queue_sem.release();
	}
	
	public static void grabMicrowave()
	{
		Student s = Queue.remove();
		Communicator c = new Communicator(s);
	}
	public static void main(String [] args) throws UnknownHostException
	{

		erlNode = "erlcom@" + InetAddress.getLocalHost().getHostName();
		javaNode = "javacom@" + InetAddress.getLocalHost().getHostName();
		
		try {
			OtpNode node = new OtpNode(javaNode);
			while (!node.ping("erlcom", 2000));
			System.out.println("Connected to erlcom");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		/*
		try {
			init();
		} catch (OtpAuthException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
		
	}
}
