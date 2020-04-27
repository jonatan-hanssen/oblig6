import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.*;

class TraadMonitor {
	
	private int antallTraader = 0;
	private int ferdigeTraader = 0;
	private boolean waiting = false;
	private CountDownLatch latch = new CountDownLatch(1);
	private Lock lock = new ReentrantLock();
	private boolean verbose;
	
	public TraadMonitor(boolean verbose) {
		this.verbose = verbose;
		
	}
	
	public void nyTraad() {
		lock.lock();
		try {
			antallTraader++;
			if (verbose) System.out.println("Antall traader: " + antallTraader 
							+ ". Ferdige traader: " + ferdigeTraader + ".");
		}
		finally {
			lock.unlock();
		}
	}
	
	public void traadFerdig() {
		lock.lock();
		try {
			ferdigeTraader++;
			if (verbose) System.out.println("Antall traader: " + antallTraader
						+ ". Ferdige traader: " + ferdigeTraader + ".");
			if (ferdigeTraader == antallTraader) latch.countDown();
		}
		finally {
			lock.unlock();
		}
	}
	
	public void reset() {
		antallTraader = 0;
		ferdigeTraader = 0;
		latch = new CountDownLatch(1);
	}
	
	public void vent() {
		System.out.println("hey");
		try {
			while (ferdigeTraader < antallTraader) {
				waiting = true;
				latch.await();
				System.out.println(antallTraader);
			}
		}
		catch (InterruptedException e) {
			System.exit(1);
		}
	}
}