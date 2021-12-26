package vuesample.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Bo Wang
 *
 *         vue-sample-server 25 Dec 2021
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Query {

	String propName() default "";

	Type type() default Type.EQUAL;

	Operand operand() default Operand.AND;

	String joinName() default "";

	Join join() default Join.LEFT;

	enum Operand {
		OR, AND, MUSTAND
	}

	enum Type {
		EQUAL, GREATER_THAN, LESS_THAN, INNER_LIKE, LEFT_LIKE, RIGHT_LIKE, LESS_THAN_NQ, IN, NOT_EQUAL
	}

	enum Join {
		LEFT, RIGHT, INNER
	}
}
