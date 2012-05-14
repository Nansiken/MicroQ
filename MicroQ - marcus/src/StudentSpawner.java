import java.lang.management.ManagementFactory;



public class StudentSpawner extends Thread{
	
	int spawnRate;
	
	public StudentSpawner(int sRate){
		spawnRate = sRate;
		if(randomSpawn()){
		Student student = new Student(ManagementFactory.getRuntimeMXBean().getName());
		System.out.println(Student.getPID());
		QueueManager.queue.add(student);
		}
	}
	
	public boolean randomSpawn(){
		if(Math.random() <= spawnRate){
			return true;
		}
		return false;
	}

}
