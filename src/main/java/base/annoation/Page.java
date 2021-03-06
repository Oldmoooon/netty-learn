package base.annoation;

import base.enums.PageType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author guyue
 * @date 2018/10/15
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface Page {
    PageType type() default PageType.DEFAULT;

    String template();
}
