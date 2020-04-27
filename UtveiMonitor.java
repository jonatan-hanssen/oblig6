import java.util.concurrent.locks.*;
class UtveiMonitor {
	
	private int antallTraader = 0;
	private int ferdigeTraader = 0;
	
	private Lock utveiLaas = new ReentrantLock();
	
	private Liste<String> utveiListe = new Lenkeliste<String>();
	
	
	public void leggTil(String utvei) {
		utveiLaas.lock();
		try {
			utveiListe.leggTil(utvei);
		}
		finally {
			utveiLaas.unlock();
		}
	}
	
	public void reset() {
		utveiListe = new Lenkeliste<String>();
	}
	
	public Liste<String> hentUtveier() {
		return utveiListe;
	}
}