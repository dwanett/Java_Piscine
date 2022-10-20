package edu.school21.render;

import edu.school21.preprocessor.PreProcessor;

public class RendererStandardImpl implements Renderer {
	PreProcessor preProcessor;

	public RendererStandardImpl(PreProcessor preProcessor) {
		this.preProcessor = preProcessor;
	}

	@Override
	public void print(String message) {
		System.out.println(preProcessor.replaceSymbol(message));
	}
}
