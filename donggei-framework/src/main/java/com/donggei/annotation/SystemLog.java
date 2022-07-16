package com.donggei.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


//这些都是源注解   @Target(ElementType.METHOD) 注解可以加到哪个上面  @Retention(RetentionPolicy.RUNTIME) 程序运行保持到哪个阶段
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SystemLog {
    String businessName();
}
