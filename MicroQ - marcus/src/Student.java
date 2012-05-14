

public class Student {

public static String PID;
public int waitingTime = 0;
public int heatingTime = 120;
	
	public Student(String PID) {
		Student.setPID(PID);		
	}
	
	public static String getPID() {
		return PID;
	}

	public static void setPID(String pID) {
		PID = pID;
	}

}
