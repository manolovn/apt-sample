package com.manolovn.android.apt_sample.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * Debug annotation
 *
 * @author manolovn
 */
@Retention(CLASS)
@Target(METHOD)
public @interface Debug {
}
