class Aapning extends HvitRute {
	public Aapning(int y, int x, Labyrint hjem) {
		super(y,x,hjem);
	}
	
	@Override
	public void gaa(String vei) {
		vei += "(" + x + "," + y + ")";
		hjem.leggTilVei(vei);
	}
}