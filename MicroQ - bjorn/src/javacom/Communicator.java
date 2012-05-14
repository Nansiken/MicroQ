package javacom;

import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;

import com.ericsson.otp.erlang.*;

public class Communicator implements Runnable {
	

	static HashMap<String, ComThread> tidMap;
	
	
	static OtpMbox mail = Simulation.javaNode.createMbox(""+Thread.currentThread().getId());
	private boolean newClient;
	private OtpErlangInt sRate;
	private OtpErlangPid pid;
	private OtpErlangAtom tid;
	private OtpErlangAtom atom;
	
	
	public Communicator(int switchRate)
	{
		sRate = new OtpErlangInt(switchRate);
		newClient = true;
	}
	/*
	public Communicator(Student s)
	{
		atom = new OtpErlangAtom("ready");
		pid = s.getPID();
	}
*/

	
	/*
	public static void newStudent(int switchRate, ComThread t, long tid)
	{
		tidMap.put("" + tid, t);
		sendMessage(new OtpErlangLong(tid), new OtpErlangAtom("new_client"), new OtpErlangInt(switchRate));
	}
		
		*/


	public static void sendMessage(OtpErlangAtom tid, OtpErlangAtom atom, OtpErlangObject obj) {
		OtpErlangObject[] message = new OtpErlangObject[3];
		message[0] = tid;
		message[1] = atom;
		message[1] = obj;
		OtpErlangTuple erlMessage = new OtpErlangTuple(message);
		mail.send(Simulation.erlNodePID, erlMessage);
		
	}
	
	@Override
	public void run() {
		
		
		
		/*
		while(true)
		{
			OtpErlangTuple tuple = null;
			OtpErlangObject msg;
			try {
				msg = mbox.receive();
				tuple = (OtpErlangTuple) msg;
			} catch (OtpErlangExit e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (OtpErlangDecodeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ComThread t =  tidMap.get(tuple.elementAt(0));
			t.receive(msg);
			
		}
		*/
		
		OtpErlangObject[] message = new OtpErlangObject[3];
		message[0] = atom;
		
		OtpErlangTuple tuple;
		if(newClient)
		{	
			sendMessage(new OtpErlangAtom(""+Thread.currentThread().getId()), atom, sRate);
			try {
				tuple = mail.receive();
			} catch (OtpErlangExit e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (OtpErlangDecodeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			tuple.
			QueueManager.addStudent(new Student())
		}
		else
		{
			sendMessage(atom, pid);
		}
		 
		// TODO Auto-generated method stub
		*/
	}

}
