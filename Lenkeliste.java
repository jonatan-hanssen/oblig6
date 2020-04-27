

import java.util.Iterator;

class Lenkeliste<T> implements Liste<T> {
	
	protected Node foersteNode = null;
	
	protected int stoerrelse = 0;
	
	class Node {
		
		protected Node neste = null;
		
		protected T data;
		
		public Node(T data) {
			this.data = data;
		}
	}
	
	class LenkelisteIterator implements Iterator<T> {
		private int i = 0;

		@Override
		public boolean hasNext() {
			return i < stoerrelse;
		}
		@Override
		public T next() {
			return hent(i++);
		}
	}
	
	@Override
	public Iterator<T> iterator() {
		return new LenkelisteIterator();
	}
	
	//jeg lager en metode som gir meg noden ved posisjonen,
	//denne vil jeg faa bruk faar mange ganger
	protected Node nodeVedPos(int pos) throws UgyldigListeIndeks {
		if (pos >= stoerrelse || pos < 0) throw new UgyldigListeIndeks(pos);
		//vi itererer gjennom nodene til vi kommer til noden paa posisjonen
		Node aktuellNode = foersteNode;
		for (int i = 0; i < pos; i++) {
			aktuellNode = aktuellNode.neste;
		}
		return aktuellNode;
	}
	
	@Override
	public int stoerrelse() {
		return stoerrelse;
	}
	
	@Override
    public void leggTil(int pos, T x) throws UgyldigListeIndeks {
		//dersom bruker vil legge til et objekt i starten maa vi lage et nytt
		//objekt aa gjoere det til foersteNode
		if (pos == 0) {
			Node nyNode = new Node(x);
			nyNode.neste = foersteNode;
			foersteNode = nyNode;
		}
		//om pos == stoerrelse vil man sette noe helt bakerst
		//da tar vi det siste elementet og endrer deres referanse fra 
		//null til new = Node(x)
		else if (pos == stoerrelse) {
			nodeVedPos(stoerrelse - 1).neste = new Node(x);
		}
		//tilslutt gjoer vi det som vil funke for alle andre tilfeller
		else {
			Node nyNode = new Node(x);
			nyNode.neste = nodeVedPos(pos);
			nodeVedPos(pos - 1).neste = nyNode;
		}
		stoerrelse++;
	}
	
	@Override
    public void leggTil(T x) {
		if (stoerrelse == 0) {
			foersteNode = new Node(x);
		}
		//vi legger til på slutten
		else {
			nodeVedPos(stoerrelse - 1).neste = new Node(x);
		}
		stoerrelse++;
	}
	
	@Override
    public void sett(int pos, T x) throws UgyldigListeIndeks {
		if (stoerrelse == 0) throw new UgyldigListeIndeks(-1);
		if (pos == 0) {
			Node nyNode = new Node(x);
			nyNode.neste = foersteNode.neste;
			foersteNode = nyNode;
		}
		else {
			//normalt vi nodeVedPos kaste UgyldigListeIndeks, men her vil den ikke
			//det ved pos == stoerrelse ettersom vi bruker pos - 1.
			//derfor må vi sjekke selv
			if (pos == stoerrelse) throw new UgyldigListeIndeks(pos);
			//alle andre tall vil nodeVedPos ta seg av
			Node aktuellNode = nodeVedPos(pos - 1);
			Node nyNode = new Node(x);
			
			nyNode.neste = aktuellNode.neste.neste;
			aktuellNode.neste = nyNode;
		}
		
	}
	
	@Override
    public T hent(int pos) throws UgyldigListeIndeks {
		return nodeVedPos(pos).data;
	}
	
	@Override
    public T fjern(int pos) throws UgyldigListeIndeks {
		if (stoerrelse == 0) throw new UgyldigListeIndeks(-1);
		//vi lager en data-variabel som vi vil lagre noe i og returnere
		T data;
		//dersom pos == 0, er det relativt lett å fjerne noden
		//vi tar bare å flytter foersteNode til noden etter, så vil den
		//ikke ha noen referanser til seg og forsvinne
		if (pos == 0) {
			data = foersteNode.data;
			foersteNode = foersteNode.neste;
			
			stoerrelse--;
			return data;
		}
		//normalt vi nodeVedPos kaste UgyldigListeIndeks ved pos == stoerrelse, 
		//men her vil den ikke det ettersom vi bruker pos - 1.
		//derfor må vi sjekke selv
		if (pos == stoerrelse) throw new UgyldigListeIndeks(pos);
		
		Node aktuellNode = nodeVedPos(pos - 1);
		data = aktuellNode.neste.data;
		aktuellNode.neste = aktuellNode.neste.neste;
		
		stoerrelse--;
		return data;
		
	}
	
	@Override
    public T fjern() throws UgyldigListeIndeks {
		if (stoerrelse == 0) throw new UgyldigListeIndeks(-1);
		//for aa fjerne den foerste noden flytter vi simpelthen den eneste 
		//referansen til den (foersteNode) til neste node
		T data = foersteNode.data;
		
		//dersom listen har ett element vil foersteNode.neste == null som passer bra
		foersteNode = foersteNode.neste;
		
		stoerrelse--;
		return data;
	}
}