package edu.school21.printer;

import edu.school21.render.Renderer;

public class PrinterWithPrefixImpl implements Printer {
	private String prefix;
	private Renderer renderer;

	public PrinterWithPrefixImpl(Renderer renderer) {
		this.renderer = renderer;
		this.prefix = "";
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	@Override
	public void print(String massege) {
		if (!this.prefix.equals("")) {
			renderer.print(this.prefix + " " + massege);
		} else {
			renderer.print(massege);
		}
	}
}
