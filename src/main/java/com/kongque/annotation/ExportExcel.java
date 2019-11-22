package com.kongque.annotation;

import java.lang.annotation.*;

/**
 * @author xiaxt
 * @date 2019/1/9.
 */
@Documented
@Target({ElementType.TYPE,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExportExcel {

    /**
     * 需要替换的值
     * @return
     */
    String[] source() default {};

    /**
     * 替换后的值
     * @return
     */
    String[] target() default {};
}
