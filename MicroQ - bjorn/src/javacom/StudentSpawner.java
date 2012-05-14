package javacom;

import java.lang.management.ManagementFactory;



public class StudentSpawner implements Runnable{
	
	private int spawnRate;
	private int switchRate;
	
	public StudentSpawner(int sRate, int switchRate){
		spawnRate = sRate;
		this.switchRate = switchRate;
	}
	
	public boolean randomSpawn(){
		if((Math.random()*100) <= spawnRate){
			return true;
		}
		return false;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true)
		{
			if(randomSpawn())
			{
				Thread t = new Thread(new Communicator(switchRate));
				t.start();
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
