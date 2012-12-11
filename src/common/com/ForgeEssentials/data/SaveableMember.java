package com.ForgeEssentials.data;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface SaveableMember
{
	boolean objectLoadingField() default false;
	boolean nullableField() default false;
}
