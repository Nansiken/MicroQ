package javacom;

import com.ericsson.otp.erlang.OtpErlangAtom;

public class ComThread {

	public ComThread(int switchRate){
		
		
		
	}
	
	public ComThread(Student s){
		
		Communicator.sendMessage(new OtpErlangAtom("" + Thread.currentThread().getId()), new OtpErlangAtom("new_client") , Student.getPID());
	}
	//Ny klient skapas eller värma
	//Kalla på communicator.sendmessage
}
