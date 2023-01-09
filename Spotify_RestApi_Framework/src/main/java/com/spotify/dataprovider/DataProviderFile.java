package com.spotify.dataprovider;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Target(java.lang.annotation.ElementType.METHOD)
public @interface DataProviderFile {
 
    String file() default "";
    String sheet() default "";
}
