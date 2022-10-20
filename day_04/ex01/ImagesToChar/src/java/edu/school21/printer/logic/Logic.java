package edu.school21.printer.logic;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Logic {
	private static char WHITE;

	private static char BLACK;

	private static String PATH;

	private static BufferedImage image = null;


	public Logic(char white, char black, String path) {
		WHITE = white;
		BLACK = black;
		PATH = path;
	}

	private static void openReadFile(){

		try {
			image = ImageIO.read(File.class.getResourceAsStream(PATH));
		} catch (IOException e) {
			System.err.println(e.getMessage());
			System.exit(-1);
		}
	}

	public void printArray(){
		int height;

		int width;

		if (image == null) {
			openReadFile();
		}
		height = image.getHeight();
		width = image.getWidth();
		for (int i = 0; i != height; i++) {
			for (int j = 0; j != width; j++) {
				if (image.getRGB(j, i) == Color.white.getRGB()) {
					System.out.print(WHITE);
				}
				if (image.getRGB(j, i) == Color.black.getRGB()) {
					System.out.print(BLACK);
				}
			}
			System.out.println();
		}
	}
}
