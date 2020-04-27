class HvitRute extends Rute {
	public HvitRute(int y, int x, Labyrint hjem) {
		super(y,x,hjem);
	}
	
	@Override
	public char tilTegn() {
		return '.';
	}
}