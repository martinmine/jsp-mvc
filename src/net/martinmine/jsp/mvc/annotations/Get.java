package net.martinmine.jsp.mvc.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation specifying that the function should be invoked for HTTP GET requests.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Verb(value = "GET")
public @interface Get {
}
