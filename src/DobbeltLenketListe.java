import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;

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
		Node<T> returnNode = null;

		if (indeks < antall / 2) {
			returnNode = hode;
			for (int i = 0; i < indeks; i++) returnNode = returnNode.neste;
		} else {
			returnNode = hale;
			for (int i = antall - 1; i > indeks; i--) returnNode = returnNode.forrige;
		}

		return returnNode;
	}

	// konstruktør
	public DobbeltLenketListe() {
		hode = hale = null;
		antall = 0;
		antallEndringer = 0;
	}

	@Override
	public int antall() {
		return antall;
	}

	@Override
	public boolean tom() {
		return (antall == 0 && hode == null && hale == null);
	}

	@Override
	public boolean leggInn(T verdi) {
		// Kaster exception hvis verdi er null
		Objects.requireNonNull(verdi, "Verdien kan ikke være et null-objekt");

		if (tom()) {
			hode = hale = new Node<T>(verdi, null, null);
		} else {
			hale = hale.neste = new Node<T>(verdi, hale, null);
		}

		antallEndringer++;
		antall++;

		return true;
	}

	@Override
	public void leggInn(int indeks, T verdi) {
		// Sjekk for null
		Objects.requireNonNull(verdi, "Verdien kan ikke være et null-objekt");

		// Modifisert indekssjekk
		if (indeks < 0) throw new IndexOutOfBoundsException("Indeks " + indeks + " er negativ!");
		else if (indeks > antall)
			throw new IndexOutOfBoundsException("Indeks " + indeks + " > antall(" + antall + ") noder!");

		// Tom -> først
		if (antall == 0 && indeks == 0) hode = hale = new Node<T>(verdi, null, null);
			// Legges først
		else if (indeks == 0) {
			hode = new Node<T>(verdi, null, hode);
			hode.neste.forrige = hode;
		} else if (indeks == antall) { // Legges sist
			hale = new Node<T>(verdi, hale, null);
			hale.forrige.neste = hale;
		} else { // Legges i midten
			Node<T> node = hode;

			for (int i = 0; i < indeks; i++) node = node.neste;

			node = new Node<T>(verdi, node.forrige, node);
			node.neste.forrige = node.forrige.neste = node;
		}

		antall++;
		antallEndringer++;
	}


	@Override
	public boolean inneholder(T verdi) {
		return indeksTil(verdi) != -1;
	}

	@Override
	public T hent(int indeks) {
		indeksKontroll(indeks);
		return finnNode(indeks).verdi;
	}

	@Override
	public int indeksTil(T verdi) {
		Node<T> node = hode;

		boolean funnet = false;
		int indeks = 0;

		while (node != null) {
			if (node.verdi.equals(verdi)) {
				funnet = true;
				break;
			}
			node = node.neste;
			indeks++;
		}

		return funnet ? indeks : -1;
	}

	@Override
	public T oppdater(int indeks, T nyverdi) {
		indeksKontroll(indeks);
		Objects.requireNonNull(nyverdi, "Den nye verdien kan ikke være et null-objekt");

		Node<T> node = finnNode(indeks);
		T gammelVerdi = node.verdi;

		node.verdi = nyverdi;

		antallEndringer++;
		return gammelVerdi;
	}

	@Override
	public boolean fjern(T verdi) {
		if (verdi == null) return false;

		Node<T> gjeldendeNode = hode;

		while (gjeldendeNode != null) {
			if (gjeldendeNode.verdi.equals(verdi)) {
				break;
			}

			gjeldendeNode = gjeldendeNode.neste;
		}

		if (gjeldendeNode == null) return false;

		if (gjeldendeNode == hode) { // Første node
			hode = hode.neste;

			if (hode != null) {
				hode.forrige = null;
			} else {
				hale = null;
			}
		} else if (gjeldendeNode == hale) { // Siste node
			hale = hale.forrige;
			hale.neste = null;
		} else {
			gjeldendeNode.forrige.neste = gjeldendeNode.neste;
			gjeldendeNode.neste.forrige = gjeldendeNode.forrige;
		}

		gjeldendeNode.verdi = null;
		gjeldendeNode.forrige = gjeldendeNode.neste = null;

		antall--;
		antallEndringer++;

		return true;
	}

	@Override
	public T fjern(int indeks) {
		indeksKontroll(indeks);
		Node<T> temp;

		if (indeks == 0) { // Første node
			temp = hode;
			hode = hode.neste;
			hode.forrige = null;
		} else if (indeks == antall - 1) { // Siste node
			temp = hale;
			hale = hale.forrige;
			hale.neste = null;
		} else {
			Node<T> p = finnNode(indeks - 1);

			temp = p.neste;

			p.neste = p.neste.neste;
			p.neste.forrige = p;
		}


		antall--;
		antallEndringer++;
		return temp.verdi;
	}

	@Override
	public void nullstill() {
		for (Node<T> temp = hode; temp != null; temp = temp.neste) {
			temp.verdi = null;
			temp.forrige = temp.neste = null;
		}

		hode = hale = null;
		antall = 0;
		antallEndringer++;
	}

	@Override
	public String toString() {
		if (tom()) return "[]";

		StringBuilder sb = new StringBuilder();
		sb.append('[');

		Node<T> node = hode;

		sb.append(node.verdi);
		node = node.neste;

		while (node != null) {
			sb.append(',').append(' ').append(node.verdi);
			node = node.neste;
		}

		sb.append(']');
		return sb.toString();
	}

	public String omvendtString() {
		if (tom()) return "[]";

		StringBuilder sb = new StringBuilder();
		sb.append('[');

		Node<T> node = hale;

		sb.append(node.verdi);
		node = node.forrige;

		while (node != null) {
			sb.append(',').append(' ').append(node.verdi);
			node = node.forrige;
		}

		sb.append(']');
		return sb.toString();
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

		private DobbeltLenketListeIterator(int indeks) {
			indeksKontroll(indeks);

			denne = hode;

			fjernOK = false;
			forventetAntallEndringer = antallEndringer;

			for (int i = 0; i < indeks; i++) next();
		}

		@Override
		public boolean hasNext() {
			return denne != null;
		}

		@Override
		public T next() {
			// TODO: Bedre melding?
			if (!hasNext()) {
				throw new NoSuchElementException("Listen har ingen flere verdier");
			} else if (antallEndringer != forventetAntallEndringer) {
				throw new ConcurrentModificationException("antallEndringer(" + antallEndringer + ") != forventetAntallEndringer(" + forventetAntallEndringer + ")");
			}

			// TODO: Bedre melding
			Objects.requireNonNull(denne, "Objektet kan ikke være et null-objekt");

			fjernOK = true;
			T tmp = denne.verdi;
			denne = denne.neste;

			return tmp;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("Ikke laget ennå!");
		}

		@Override
		public void forEachRemaining(Consumer<? super T> action) {
			Objects.requireNonNull(action, "Handling kan ikke være null!");
			Node<T> node = denne;

			while(node != null){
				action.accept(node.verdi);
				node = node.neste;
			}
		}

	} // DobbeltLenketListeIterator


	@Override
	public void forEach(Consumer<? super T> action) {
		Objects.requireNonNull(action, "Handling kan ikke være null!");
		Node<T> node = hode;

		while (node != null) {
			action.accept(node.verdi);
			node = node.neste;
		}

	}

} // DobbeltLenketListe
