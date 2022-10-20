package edu.school21.printer;

import edu.school21.render.Renderer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PrinterWithDateTimeImpl implements Printer{
	private Renderer renderer;

	public PrinterWithDateTimeImpl(Renderer renderer) {
		this.renderer = renderer;
	}
	@Override
	public void print(String massege) {
		renderer.print(massege + " " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
	}
}
