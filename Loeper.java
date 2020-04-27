class Loeper implements Runnable {
	
	Rute rute;
	String vei;
	TraadMonitor monitor;
	
	public Loeper(Rute rute, TraadMonitor monitor, String vei) {
		this.rute = rute;
		this.monitor = monitor;
		this.vei = vei;
	}
	
	public void run() {
		monitor.nyTraad();
		rute.gaa(vei);
		monitor.traadFerdig();
	}


}