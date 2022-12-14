package edu.school21.render;

import edu.school21.preprocessor.PreProcessor;

public class RendererErrImpl implements Renderer {
	PreProcessor preProcessor;

	public RendererErrImpl(PreProcessor preProcessor) {
		this.preProcessor = preProcessor;
	}

	@Override
	public void print(String message) {
		System.err.println(preProcessor.replaceSymbol(message));
	}
}
