package recoleccion;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import data.BoxArt;
import data.Game;
import data.Game.Genre;
import data.Image;
import data.Platform;

public class Extraccion {

	public static void main(String[] args) throws IOException {
		GamesDBConnector gdbc = new GamesDBConnector();

		// Extraccion.getJuegos(gdbc);

		// Extraccion.plataformas(gdbc);

		Extraccion.juegos(gdbc);

		// Set<Long> gamesPlatform = Extraccion.juegosBD();

		// Extraccion.imagsJuegos(gdbc, gamesPlatform);

		// Extraccion.generossJuegos(gdbc, gamesPlatform);

		// Extraccion.plataformasJuegos(gdbc, gamesPlatform);
	}

	public static void plataformasJuegos(GamesDBConnector gdbc, Set<Long> games)
			throws IOException {

		String file = "juegos_plataformas.csv";
		File fichero = new File(file);
		BufferedWriter bw = new BufferedWriter(new FileWriter(fichero));
		bw.write("id%%plataforma\n");

		Set<Long> idP = new HashSet<Long>();
		idP.add((long) 1); // PC
		idP.add((long) 4920); // XONE
		idP.add((long) 4912); // 3DS
		idP.add((long) 38); // WIIU
		idP.add((long) 4919); // PS4
		idP.add((long) 9); // WII
		idP.add((long) 12); // PS3
		idP.add((long) 15); // X360
		idP.add((long) 39); // PSVita

		for (Long id : idP) {
			List<Game> gp = gdbc.getPlatformGames(id);

			List<Long> gpid = new LinkedList<Long>();

			for (Game g : gp) {
				gpid.add(g.getId());
			}

			for (Long i : games) {
				if (gpid.contains(i)) {
					String ss = i + "%%" + id + "\n";
					System.out.print(ss);
					bw.write(ss);
				}
			}
		}

		bw.flush();
		bw.close();

	}

	public static void generossJuegos(GamesDBConnector gdbc,
			Set<Long> gamesPlatform) throws IOException {

		String file = "juegos_generos.csv";
		File fichero = new File(file);
		BufferedWriter bw = new BufferedWriter(new FileWriter(fichero));
		bw.write("id%%genero\n");

		for (Long id : gamesPlatform) {
			Game g = gdbc.getGame("" + id);
			List<Genre> generos = g.getGenres();

			if (generos != null) {

				for (Genre genero : generos) {
					String ss = id + "%%" + genero.toString() + "\n";
					System.out.print(ss);
					bw.write(ss);
				}
			}
		}

		bw.flush();
		bw.close();
	}

	public static void imagsJuegos(GamesDBConnector gdbc,
			Set<Long> gamesPlatform) throws IOException {

		String file = "juegos_imgs.csv";
		File fichero = new File(file);
		BufferedWriter bw = new BufferedWriter(new FileWriter(fichero));
		bw.write("id%%imagen\n");

		for (Long id : gamesPlatform) {
			Game g = gdbc.getGame("" + id);

			Image i = g.getImages();
			if (i.getBoxArt() != null) {
				for (BoxArt b : i.getBoxArt()) {
					if (b.getBoxArt() == null)
						break;
					String ss = g.getBaseUrl() + b.getBoxArt();
					if (!ss.contains("original") || !ss.contains("front")) {
					} else {
						String s = g.getId() + "%%" + ss + "\n";
						System.out.print(s);
						bw.write(s);
						break;
					}
				}
			}
		}

		bw.flush();
		bw.close();
	}

	public static String getImagen(Game g) {
		Image i = g.getImages();
		if (i.getBoxArt() != null) {
			for (BoxArt b : i.getBoxArt()) {
				if (b.getBoxArt() == null)
					break;
				String ss = g.getBaseUrl() + b.getBoxArt();
				if (!ss.contains("original") || !ss.contains("front")) {
				} else {
					return ss;
				}
			}
		}
		return "null";
	}

	public static Set<Long> juegosBD() throws IOException {
		String f = "ids.csv";
		File file = new File(f);
		Scanner s = new Scanner(new FileInputStream(file));

		Set<Long> games = new HashSet<Long>();

		while (s.hasNext()) {
			Long l = s.nextLong();
			// System.out.println(l);
			games.add(l);
			s.nextLine();
		}
		s.close();

		return games;
	}

	public static void getJuegos(GamesDBConnector gdbc) throws IOException {
		Set<Long> idP = new HashSet<Long>();
		idP.add((long) 1); // PC
		idP.add((long) 4920); // XONE
		idP.add((long) 4912); // 3DS
		idP.add((long) 38); // WIIU
		idP.add((long) 4919); // PS4
		idP.add((long) 9); // WII
		idP.add((long) 12); // PS3
		idP.add((long) 15); // X360
		idP.add((long) 39); // PSVita

		String file = "ids.csv";
		File fichero = new File(file);
		BufferedWriter bw = new BufferedWriter(new FileWriter(fichero));

		Set<Long> games = new HashSet<Long>();

		for (Long i : idP) {
			System.out.println("Palataforma: " + i);
			List<Game> lg = gdbc.getPlatformGames(i);
			for (Game g : lg) {
				if (g.getId() != 23612 && Extraccion.isNumeric(g.getId() + "")) {
					System.out.println(g.getId());
					games.add(g.getId());
				}
			}
		}

		for (Long id : games) {
			bw.write(id + "\n");
		}

		bw.flush();
		bw.close();
	}

	public static void juegos(GamesDBConnector gdbc) throws IOException {
		Set<Long> idP = new HashSet<Long>();
		idP.add((long) 1); // PC
		idP.add((long) 4920); // XONE
		idP.add((long) 4912); // 3DS
		idP.add((long) 38); // WIIU
		idP.add((long) 4919); // PS4
		idP.add((long) 9); // WII
		idP.add((long) 12); // PS3
		idP.add((long) 15); // X360
		idP.add((long) 39); // PSVita

		String file = "juegos2.csv";
		File fichero = new File(file);
		BufferedWriter bw = new BufferedWriter(new FileWriter(fichero));
		bw.write("id%%titulo%%resumen%%lanzamiento%%rating%%precio%%imagen\n");

		Set<Long> gamesPlatform = new HashSet<Long>();

		for (Long i : idP) {
			List<Game> lg = gdbc.getPlatformGames(i);
			for (Game g : lg) {
				gamesPlatform.add(g.getId());
			}
		}

		Set<Game> games = new HashSet<Game>();
		for (Long id : gamesPlatform) {
			if (id != 23612 && Extraccion.isNumeric(id + "")) {

				Game g = gdbc.getGame("" + id);
				int precio = new Random().nextInt(30) + 30;
				String s = g.getId() + "%%" + g.getGameTitle() + "%%"
						+ g.getOverview() + "%%" + g.getReleaseDate() + "%%"
						+ g.getRating() + "%%" + precio + "%%"
						+ Extraccion.getImagen(g) + "\n";
				System.out.print(s);
				bw.write(s);
				games.add(g);
			}
		}

		bw.flush();
		bw.close();
	}

	public static boolean isNumeric(String str) {
		if (str == null) {
			return false;
		}
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if (Character.isDigit(str.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}

	public static void plataformas(GamesDBConnector gdbc) throws IOException {
		String file = "plataforma.csv";
		File fichero = new File(file);
		BufferedWriter bw = new BufferedWriter(new FileWriter(fichero));

		System.out.println("Empezando a recolectar datos de plataformas...");

		bw.write("id%%name%%alias\n");

		List<Platform> lp = gdbc.getPlatformsList();

		for (int i = 0; i < lp.size(); i++) {
			Platform platform = lp.get(i);

			Platform platform2 = gdbc.getPlatform(platform.getId());

			platform2.setAlias(platform.getAlias());
			platform2.setName(platform.getName());

			lp.remove(i);
			lp.add(platform2);

			bw.write(lp.get(i).datos_pales() + "\n");
		}
		bw.flush();
		bw.close();

		System.out.println("Datos de plataformas recolectados.");

	}
}
