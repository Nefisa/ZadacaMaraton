import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MaratonTest {

	public static void main(String[] args) throws InputMismatchException, FileNotFoundException {

		maraton();
	}

	public static void maraton() throws FileNotFoundException {

		Scanner input = new Scanner(System.in);

		System.out.println("Izaberite opciju:");
		System.out.println("1. Najbrzi ucesnik i njegovo vrijeme");
		System.out.println("2. Poredak ucesnika");
		System.out.println("3. Provjera vremena po imenu");
		System.out.println("4. Prosjecno vrijeme");
		System.out.println("5. Najbolji maratonci");
		System.out.println("6. Broj linija file-a na adresi : http://www.textfiles.com/science/astronau.txt");
		System.out.println("7. Imena ucesnika sortirana po abecedi");

		try {

			int izbor = input.nextInt();

			while (izbor < 1 || izbor > 7) {
				System.out.println("Pogresan unos. Pokusajte ponovo:");
				izbor = input.nextInt();
			}

			switch (izbor) {
			case 1:
				System.out.println("Najbrzi ucesnik je " + najbrziUcesnik().getIme() + " sa vremenom "
						+ najbrziUcesnik().getVrijeme());
				break;
			case 2:
				poredakUcesnika();
				break;
			case 3:
				System.out.println("Unesite ime korisnika cije vrijeme zelite provjeriti: ");
				String imeZaProvjeru = input.next();

				provjeriPoImenu(imeZaProvjeru.toLowerCase());
				break;
			case 4:
				prosjecnoVrijemeMaratona();
				break;
			case 5:
				najboljiMaratonci();
				break;
			case 6:
				brojLinija();
				break;
			case 7:
				sortiranjeImena();
				break;

			}
		} catch (InputMismatchException e) {
			System.out.println("Uneseni format ne odgovara trazenom formatu unosa. Pokusajte ponovo: ");
			input.nextLine();
			maraton();
		}

		input.close();

	}

	public static ArrayList<Ucesnik> procitajIzFilea() throws FileNotFoundException, InputMismatchException {

		ArrayList<Ucesnik> listaUcesnika = new ArrayList<>();

		java.io.File file = new java.io.File("maraton.txt");

		try (Scanner citac = new Scanner(file);) {

			while (citac.hasNext()) {
				String ime = citac.next();
				int vrijeme = citac.nextInt();
				listaUcesnika.add(new Ucesnik(ime, vrijeme));
			}
		}

		return listaUcesnika;

	}

	public static Ucesnik najbrziUcesnik() throws InputMismatchException, FileNotFoundException {

		ArrayList<Ucesnik> listaUcesnika = procitajIzFilea();

		Ucesnik najbrzi = new Ucesnik(listaUcesnika.get(0).getIme(), listaUcesnika.get(0).getVrijeme());

		for (int i = 1; i < listaUcesnika.size(); i++) {
			if (listaUcesnika.get(i).getVrijeme() < najbrzi.getVrijeme()) {
				najbrzi.setIme(listaUcesnika.get(i).getIme());
				najbrzi.setVrijeme(listaUcesnika.get(i).getVrijeme());
			}
		}

		return najbrzi;
	}

	public static void poredakUcesnika() throws InputMismatchException, FileNotFoundException {

		ArrayList<Ucesnik> listaUcesnika = procitajIzFilea();

		for (int i = 0; i < listaUcesnika.size() - 1; i++) {
			for (int j = i + 1; j < listaUcesnika.size(); j++) {
				String temp1 = listaUcesnika.get(i).getIme();
				int temp2 = listaUcesnika.get(i).getVrijeme();
				if (temp2 > listaUcesnika.get(j).getVrijeme()) {
					listaUcesnika.get(i).setIme(listaUcesnika.get(j).getIme());
					listaUcesnika.get(i).setVrijeme(listaUcesnika.get(j).getVrijeme());
					listaUcesnika.get(j).setIme(temp1);
					listaUcesnika.get(j).setVrijeme(temp2);
				}
			}
		}

		System.out.println("Poredak ucesnika sa ostvarenim vremenima (do najmanjeg ka najvecem):");

		for (Ucesnik u : listaUcesnika) {
			System.out.println(u.getIme() + " " + u.getVrijeme());
		}

	}

	public static void provjeriPoImenu(String ime) throws InputMismatchException, FileNotFoundException {

		ArrayList<Ucesnik> listaUcesnika = procitajIzFilea();

		for (int i = 0; i < listaUcesnika.size(); i++) {
			if (listaUcesnika.get(i).getIme().toLowerCase().equals(ime)) {
				System.out.println("Korisnik sa imenom " + listaUcesnika.get(i).getIme() + " je ostvario vrijeme "
						+ listaUcesnika.get(i).getVrijeme());
				break;
			}
		}
	}

	public static void prosjecnoVrijemeMaratona() throws InputMismatchException, FileNotFoundException {

		ArrayList<Ucesnik> listaUcesnika = procitajIzFilea();

		int sumaVremena = 0;

		for (Ucesnik u : listaUcesnika) {
			sumaVremena += u.getVrijeme();
		}

		double prosjecnoVrijeme = sumaVremena * 1.0 / listaUcesnika.size();

		System.out.printf("Prosjecno vrijeme ucesnika maratona iznosi %6.2f\n", prosjecnoVrijeme);
	}

	public static void najboljiMaratonci() throws InputMismatchException, FileNotFoundException {

		ArrayList<Ucesnik> listaUcesnika = procitajIzFilea();

		ArrayList<Ucesnik> listaNajboljih = new ArrayList<>();

		for (int i = 0; i < listaUcesnika.size(); i++) {
			if (listaUcesnika.get(i).getVrijeme() < 300)
				listaNajboljih.add(listaUcesnika.get(i));

		}

		java.io.File file = new java.io.File("najboljiMaratonci.txt");

		try (java.io.PrintWriter output = new java.io.PrintWriter(file);) {

			for (int i = 0; i < listaNajboljih.size(); i++) {
				output.print(listaNajboljih.get(i).getIme() + " ");
				output.println(listaNajboljih.get(i).getVrijeme());

			}
		}

	}

	public static void sortiranjeImena() throws FileNotFoundException {

		ArrayList<String> imenaUcesnika = new ArrayList<>();

		java.io.File file = new java.io.File("imena.txt");

		try (Scanner citac = new Scanner(file);) {

			while (citac.hasNext()) {
				String ime = citac.next();
				imenaUcesnika.add(ime);
			}
		}

		for (int i = 0; i < imenaUcesnika.size() - 1; i++) {
			for (int j = i + 1; j < imenaUcesnika.size(); j++) {
				String temp = imenaUcesnika.get(i);
				if (temp.compareTo(imenaUcesnika.get(j)) > 0) {
					imenaUcesnika.set(i, imenaUcesnika.get(j));
					imenaUcesnika.set(j, temp);
				}
			}

		}

		System.out.println("Imena ucesnika sortirana po abecedi: ");

		for (String s : imenaUcesnika)
			System.out.println(s.toString());

	}

	public static void brojLinija() {

		try {
			java.net.URL url = new java.net.URL("http://www.textfiles.com/science/astronau.txt");
			int count = 0;
			Scanner input = new Scanner(url.openStream());

			while (input.hasNextLine()) {
				String linija = input.nextLine();
				count++;
			}

			input.close();

			System.out.println("U navedenom file-u ima " + count + " linija.");

		} catch (java.net.MalformedURLException e) {

			System.out.println("Neispravan URL");

		} catch (java.io.IOException e) {

			System.out.println("Ne postoji file pod tim imenom.");
		}

	}

}
