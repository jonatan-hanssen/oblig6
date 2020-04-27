import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class Oblig6 {
	public static void main(String[] args) {
		String filnavn = null;
		boolean verbose = false;
		
		if (args.length > 0) {
			filnavn = args[0];
		} else {
			System.out.println("FEIL! Riktig bruk av programmet: "
							   +"java Oblig5 <labyrintfil>");
			return;
		}
		
		if (args.length > 1) 
			if (args[1].equals("verbose")) verbose = true;
		File fil = new File(filnavn);
		Labyrint l = null;
		try {
			l = Labyrint.lesFraFil(fil,verbose);
		} catch (FileNotFoundException e) {
			System.out.printf("FEIL: Kunne ikke lese fra '%s'\n", filnavn);
			System.exit(1);
		}
		System.out.println(l);

		// les start-koordinater fra standard input
		Scanner inn = new Scanner(System.in);
		System.out.println("Skriv inn koordinater <kolonne> <rad> ('a' for aa avslutte)");
		String[] ord = inn.nextLine().split(" ");
		while (!ord[0].equals("a")) {

			try {
				int startKol = Integer.parseInt(ord[0]);
				int startRad = Integer.parseInt(ord[1]);

				Liste<String> utveier = l.finnUtveiFra(startKol, startRad);
				if (utveier.stoerrelse() != 0) {
					String korteste_eneste = "Korteste";
					if (utveier.stoerrelse() == 1) korteste_eneste = "Eneste";
					int utveiNr = 0;
					if (verbose && utveier.stoerrelse() > 1) {
						for (String s : utveier) {
							utveiNr++;
							System.out.println("\nUtvei nr " + utveiNr + ":");
							System.out.println(l.visUtvei(s));
							System.out.println(s);
						}
					}
					System.out.println("\n\n\n" + korteste_eneste + " utvei:");
					System.out.println(l.visUtvei(l.kortestRute()));
					System.out.println(l.kortestRute());
					System.out.println("\nAntall utveier: " + l.hentAntallUtveier());
					System.out.println("\n" + korteste_eneste + " utvei er printet ut ovenfor.");
					if (!verbose) {
						System.out.println(
							"\nSkriv \"verbose\" etter filnavnet naar du " +
							"starter programmet for aa se alle mulige utveier.\n" +
							"Eksempel: java Oblig5 1.in verbose");
					}
				} else {
					System.out.println("Ingen utveier.");
				}
				System.out.println();
			} catch (NumberFormatException e) {
				System.out.println("Ugyldig input!");
			}
			
			System.out.println("Skriv inn nye koordinater ('a' for aa avslutte)");
			ord = inn.nextLine().split(" ");
		}
	}
}