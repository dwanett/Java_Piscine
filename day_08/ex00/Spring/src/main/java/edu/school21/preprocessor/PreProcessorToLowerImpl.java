package edu.school21.preprocessor;

public class PreProcessorToLowerImpl implements PreProcessor {
	@Override
	public String replaceSymbol(String message) {
		return message.toLowerCase();
	}
}
