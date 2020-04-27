abstract class Rute {
	protected int y;
	protected int x;
	protected Labyrint hjem;
	protected Rute[] naboer = new Rute[4];
	/*	
	 * En rutes naboer representeres av arrayet slik:
	 *			1
	 *		  2 R 0
	 *			3
	 */
	public Rute(int y, int x, Labyrint hjem) {
		this.y = y;
		this.x = x;
		this.hjem = hjem;
		
	}
	
	public void gaa(String vei) {
		vei += hentKoordinater() + "-->";

		Rute[] muligeVeier = new Rute[3];
		int veierFunnet = 0;
		for (int i = 0; i < naboer.length; i++) {
			if (naboer[i] != null 
			&& !vei.contains(naboer[i].hentKoordinater())
			&& !(naboer[i] instanceof SortRute)) {
				muligeVeier[veierFunnet] = naboer[i];
				veierFunnet++;
			}
		}
		
		if (veierFunnet == 1) muligeVeier[0].gaa(vei);
		else if (veierFunnet == 2) {
			Loeper loeper1 = new Loeper(muligeVeier[0],hjem.hentTraadMonitor(),vei);
			new Thread(loeper1).start();
			
			muligeVeier[1].gaa(vei);
		}
		else if (veierFunnet == 3) {
			Loeper loeper1 = new Loeper(muligeVeier[0],hjem.hentTraadMonitor(),vei);
			new Thread(loeper1).start();
			
			Loeper loeper2 = new Loeper(muligeVeier[1],hjem.hentTraadMonitor(),vei);
			new Thread(loeper2).start();
			
			muligeVeier[2].gaa(vei);
		}
	}
	
	public void nyNabo(int indeks, Rute nyNabo) {
		naboer[indeks] = nyNabo;
	}
	
	public String hentKoordinater() {
		return "(" + x + "," + y + ")";
	}
	
	abstract char tilTegn();
	
	@Override
	public String toString() {
		String naboString = "";
		if (naboer[0] != null) naboString += "\nNabo til hoyre: " + naboer[0].tilTegn();
		else naboString += "\nIngen nabo til hoyre.";
		
		if (naboer[1] != null) naboString += "\nNabo over: " + naboer[1].tilTegn();
		else naboString += "\nIngen nabo over.";
		
		if (naboer[2] != null) naboString += "\nNabo til venstre: " + naboer[2].tilTegn();
		else naboString += "\nIngen nabo til venstre.";
		
		if (naboer[3] != null) naboString += "\nNabo under: " + naboer[3].tilTegn();
		else naboString += "\nIngen nabo under.";
		
		return "Rute ved y = " + y + " x = " + x + " med representasjon " + tilTegn() + naboString;
	}
}