import java.util.Iterator;

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
			throw new IndexOutOfBoundsException("Indeks " +
					indeks + " er negativ!");
		} else if (indeks >= antall) {
			throw new IndexOutOfBoundsException("Indeks " +
					indeks + " >= antall(" + antall + ") noder!");
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
		throw new UnsupportedOperationException("Ikke laget ennå!");
	}

	@Override
	public boolean tom() {
		throw new UnsupportedOperationException("Ikke laget ennå!");
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
		throw new UnsupportedOperationException("Ikke laget ennå!");
	}

	public Iterator<T> iterator(int indeks) {
		throw new UnsupportedOperationException("Ikke laget ennå!");
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

		@Override
		public boolean hasNext() {
			throw new UnsupportedOperationException("Ikke laget ennå!");
		}

		@Override
		public T next() {
			throw new UnsupportedOperationException("Ikke laget ennå!");
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("Ikke laget ennå!");
		}

	} // DobbeltLenketListeIterator
} // DobbeltLenketListe  
