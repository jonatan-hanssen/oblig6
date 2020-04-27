import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.IllegalFormatException;
import java.io.File;
import java.util.concurrent.locks.*;

class Labyrint {
	private Rute[][] rutenett;
	private int rader;
	private int radlengde;
	private UtveiMonitor utveiMonitor = new UtveiMonitor();
	private TraadMonitor traadMonitor;
	
	private Labyrint(Rute[][] rutenett, boolean verbose) {
		this.rutenett = rutenett;
		this.rader = rutenett.length;
		this.radlengde = rutenett[0].length;
		this.traadMonitor = new TraadMonitor(verbose);
	}
	
	static Labyrint lesFraFil(File fil, boolean verbose)
	throws FileNotFoundException
	{
		Scanner scanner = new Scanner(fil);
		
		String[] foersteLinje = scanner.nextLine().split(" ");
		
		int filRader = Integer.parseInt(foersteLinje[0]);
		int filRadlengde = Integer.parseInt(foersteLinje[1]);
		
		Rute[][] filRutenett = new Rute[filRader][filRadlengde];
		
		Labyrint filLabyrint = new Labyrint(filRutenett, verbose);
		/* Slik blir rutenettet representert av 2-d-arrayet:
		 *	[ [#,.,#],
		 *	  [.,.,#],
		 *	  [#,#,#] ]
		 * Altsaa er y-koordinaten foerst
		 */
		
		int radNr = 0;
		while (radNr < filRader) {
			char[] rad = scanner.nextLine().toCharArray();
			
			
			for (int i = 0; i < rad.length; i++) {
				/* labyrints referanse 'rutenett' peker paa samme array som
				 * filRutenett, saa dersom vi endrer paa filRutenett vil
				 * endringen ogsaa skje i labyrint.rutenett
				 */
				
				if (rad[i] == '.') {
					// Vi maa sjekke om dette er en aapning
					if (radNr == filRader-1 || radNr == 0 || i == 0 || i == rad.length-1) {
						filRutenett[radNr][i] = new Aapning(radNr,i,filLabyrint);
					}
					// Om ikke er den hvit
					else {
						filRutenett[radNr][i] = new HvitRute(radNr,i,filLabyrint);
					}
				}
				else if (rad[i] == '#') {
					filRutenett[radNr][i] = new SortRute(radNr,i,filLabyrint);
				}
			}
			radNr++;
		}
		filLabyrint.finnNaboer();
		return filLabyrint;
	}
	
	private void finnNaboer() {
		for (int i = 0; i < rutenett.length; i++) {
			for (int j = 0; j < rutenett[i].length; j++) {
				// Vi er naa paa en spesifikk rute og vi skal finne naboene dens
				if (i > 0) rutenett[i][j].nyNabo(1,rutenett[i-1][j]);
				if (j > 0) rutenett[i][j].nyNabo(2,rutenett[i][j-1]);
				if (i < rutenett.length - 1) rutenett[i][j].nyNabo(3,rutenett[i+1][j]);
				if (j < rutenett[i].length - 1) rutenett[i][j].nyNabo(0,rutenett[i][j+1]);
				// Dersom noe av dette ikke er tilfelle vil naboen der vaere null
			}
		}
	}
	
	public Liste<String> finnUtveiFra(int x, int y) {
		utveiMonitor.reset();
		traadMonitor.reset();
		traadMonitor.nyTraad();
		rutenett[y][x].gaa("");
		traadMonitor.traadFerdig();
		traadMonitor.vent();
		return utveiMonitor.hentUtveier();
	}
	
	private Liste<int[]> hentKoordinater(String utvei) {
		Liste<int[]> koordinater = new Lenkeliste<int[]>();
		
		String[] koordinaterString = utvei.split("-->");
		
		for (String par : koordinaterString) {
			String[] parArray = par
					.replace("(","")
					.replace(")","")
					.split(",");
			
			
			koordinater.leggTil(new int[]{Integer.parseInt(parArray[0]),
										  Integer.parseInt(parArray[1])});
		}
		
		return koordinater;
	}
	
	public String visUtvei(String utvei) {
		Liste<int[]> koordinater = hentKoordinater(utvei);
		
		String string = "";
		
		for (int i = 0; i < rutenett.length; i++) {
			for (int j = 0; j < rutenett[i].length; j++) {
				char ruteTegn = '#';
				if (rutenett[i][j].tilTegn() == '.') ruteTegn = ' ';
				
				boolean foersteSteg = true;
				for (int[] rute : koordinater) {
					if (rute[0] == j && rute[1] == i) {
						if (foersteSteg) ruteTegn = 'x';
						else ruteTegn = '.'; 
					}
					foersteSteg = false;
				}
			
				string += ruteTegn;
			}
			
			string += "\n";
		}
		return string;
	}
	
	public String kortestRute() {
		String kortest = hentUtveiListe().hent(0);
		for (String utvei : hentUtveiListe()) {
			if (utvei.length() < kortest.length()) kortest = utvei;
		}
		return kortest;
	}
	
	public void leggTilVei(String vei) {
		utveiMonitor.leggTil(vei);
	}
	
	public int hentAntallUtveier() {
		return hentUtveiListe().stoerrelse();
	}
	
	public Rute[][] hentRutenett() {
		return rutenett;
	}
	
	public Liste<String> hentUtveiListe() {
		return utveiMonitor.hentUtveier();
	}
	
	public UtveiMonitor hentUtveiMonitor() {
		return utveiMonitor;
	}
	
	public TraadMonitor hentTraadMonitor() {
		return traadMonitor;
	}
	
	@Override
	public String toString() {
		String string = "";
		string += rader + " " + radlengde + "\n";
		
		for (int i = 0; i < rutenett.length; i++) {
			for (int j = 0; j < rutenett[i].length; j++) {
				if (rutenett[i][j].tilTegn() == '#') string += "#";
				else if (rutenett[i][j].tilTegn() == '.') string += " ";
			}
			
			string += "\n";
		}
		return string;
	}
}










