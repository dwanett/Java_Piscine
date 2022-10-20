package edu.school21.printer.logic;

import com.diogonunes.jcolor.Attribute;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static com.diogonunes.jcolor.Ansi.colorize;
import static com.diogonunes.jcolor.Attribute.*;

public class Logic {
	private static String WHITE;

	private static String BLACK;

	private static String PATH;

	private static BufferedImage image = null;


	public Logic(String white, String black, String path) {
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

		Attribute white = getColorAttribut(WHITE);

		Attribute black = getColorAttribut(BLACK);

		if (image == null) {
			openReadFile();
		}
		height = image.getHeight();
		width = image.getWidth();
		for (int i = 0; i != height; i++) {
			for (int j = 0; j != width; j++) {
				if (image.getRGB(j, i) == Color.white.getRGB()) {
					System.out.print(colorize(" ", white));
				}
				if (image.getRGB(j, i) == Color.black.getRGB()) {
					System.out.print(colorize(" ", black));
				}
			}
			System.out.println();

		}
	}

	public Attribute getColorAttribut(String strColor) {
		switch (strColor) {
			case "BLACK" : return BLACK_BACK();
			case "BLUE" : return BLUE_BACK();
			case "CYAN" : return CYAN_BACK();
			case "GREEN" : return GREEN_BACK();
			case "MAGENTA" : return MAGENTA_BACK();
			case "RED" : return RED_BACK();
			case "WHITE" : return WHITE_BACK();
			case "YELLOW" : return YELLOW_BACK();
			case "BRIGHT_BLACK" : return BRIGHT_BLACK_BACK();
			case "BRIGHT_BLUE" : return BRIGHT_BLUE_BACK();
			case "BRIGHT_CYAN" : return BRIGHT_CYAN_BACK();
			case "BRIGHT_GREEN" : return BRIGHT_GREEN_BACK();
			case "BRIGHT_MAGENTA" : return BRIGHT_MAGENTA_BACK();
			case "BRIGHT_RED" : return BRIGHT_RED_BACK();
			case "BRIGHT_WHITE" : return BRIGHT_WHITE_BACK();
			case "BRIGHT_YELLOW" : return BRIGHT_YELLOW_BACK();
		}
		System.err.println("Error: Color not found");
		System.exit(-1);
		return null;
	}
}
