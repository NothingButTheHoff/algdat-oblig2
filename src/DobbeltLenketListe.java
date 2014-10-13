import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class DobbeltLenketListe<T> implements Liste<T> {

	// En indre nodeklasse
	private static final class Node<T> {
		private T verdi;
		private Node<T> forrige, neste;

		private Node(T verdi, Node<T> forrige, Node<T> neste) {
			this.verdi = verdi;
			this.forrige = forrige;
			this.neste = neste;
		}
	}

	private Node<T> hode;          // peker til den første i listen
	private Node<T> hale;          // peker til den siste i listen
	private int antall;            // antall noder i listen
	private int antallEndringer;   // antall endringer i listen

	// hjelpemetode
	private void indeksKontroll(int indeks) {
		if (indeks < 0) {
			throw new IndexOutOfBoundsException("Indeks " + indeks + " er negativ!");
		} else if (indeks >= antall) {
			throw new IndexOutOfBoundsException("Indeks " + indeks + " >= antall(" + antall + ") noder!");
		}
	}

	// hjelpemetode
	private Node<T> finnNode(int indeks) {
		throw new UnsupportedOperationException("Ikke laget ennå!");
	}

	// konstruktør
	public DobbeltLenketListe() {
		hode = hale = null;
		antall = 0;
		antallEndringer = 0;
	}

	@Override
	public int antall() {
		antall = 0;
		for (T t : this) antall++;
		return antall;
	}

	@Override
	public boolean tom() {
		return antall() == 0;
	}

	@Override
	public boolean leggInn(T verdi) {
		throw new UnsupportedOperationException("Ikke laget ennå!");
	}

	@Override
	public void leggInn(int indeks, T verdi) {
		throw new UnsupportedOperationException("Ikke laget ennå!");
	}

	@Override
	public boolean inneholder(T verdi) {
		throw new UnsupportedOperationException("Ikke laget ennå!");
	}

	@Override
	public T hent(int indeks) {
		throw new UnsupportedOperationException("Ikke laget ennå!");
	}

	@Override
	public int indeksTil(T verdi) {
		throw new UnsupportedOperationException("Ikke laget ennå!");
	}

	@Override
	public T oppdater(int indeks, T nyverdi) {
		throw new UnsupportedOperationException("Ikke laget ennå!");
	}

	@Override
	public boolean fjern(T verdi) {
		throw new UnsupportedOperationException("Ikke laget ennå!");
	}

	@Override
	public T fjern(int indeks) {
		throw new UnsupportedOperationException("Ikke laget ennå!");
	}

	@Override
	public void nullstill() {
		throw new UnsupportedOperationException("Ikke laget ennå!");
	}

	@Override
	public String toString() {
		throw new UnsupportedOperationException("Ikke laget ennå!");
	}

	public String omvendtString() {
		throw new UnsupportedOperationException("Ikke laget ennå!");
	}

	@Override
	public Iterator<T> iterator() {
		return new DobbeltLenketListeIterator();
	}

	public Iterator<T> iterator(int indeks) {
		return new DobbeltLenketListeIterator(indeks);
	}

	private class DobbeltLenketListeIterator implements Iterator<T> {
		private Node<T> denne;
		private boolean fjernOK;
		private int forventetAntallEndringer;

		private DobbeltLenketListeIterator() {
			denne = hode;     // denne starter på den første i listen
			fjernOK = false;  // blir sann når next() kalles
			forventetAntallEndringer = antallEndringer;  // teller endringer
		}

		private DobbeltLenketListeIterator(int indeks){
			indeksKontroll(indeks);

			denne = hode;
			fjernOK = false;
			forventetAntallEndringer = antallEndringer;

			for(int i=0; i<indeks;i++) next();
		}

		@Override
		public boolean hasNext() {
			return denne != null;
		}

		@Override
		public T next() {
			// TODO: Bedre melding?
			if(antallEndringer != forventetAntallEndringer){
				throw new ConcurrentModificationException("antallEndringer("+antallEndringer+") != forventetAntallEndringer("+forventetAntallEndringer+")");
			}

			// TODO: Bedre melding
			if(denne == null) throw new NoSuchElementException("Elementet er null");

			fjernOK = true;
			T tmp = denne.verdi;
			denne = denne.neste;

			return tmp;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("Ikke laget ennå!");
		}

	} // DobbeltLenketListeIterator
} // DobbeltLenketListe
