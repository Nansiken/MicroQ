package javacom;

import com.ericsson.otp.erlang.OtpErlangPid;

public class Student {

static OtpErlangPid PID;
public int waitingTime = 0;
public int heatingTime = 120;
	
	public Student(OtpErlangPid PID) {
		Student.setPID(PID);		
	}
	
	public static OtpErlangPid getPID() {
		return PID;
	}

	public static void setPID(OtpErlangPid pID2) {
		PID = pID2;
	}

}
