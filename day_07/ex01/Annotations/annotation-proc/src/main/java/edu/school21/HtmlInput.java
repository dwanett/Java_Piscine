package edu.school21;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface HtmlInput {
	String type();

	String name();

	String placeholder();
}
