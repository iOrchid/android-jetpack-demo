package org.zhiwei.jetpack.dagger2;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Author: zhiwei.
 * Date: 2018/11/7 0007,14:16.
 */
@Qualifier
@Retention(value = RetentionPolicy.RUNTIME)
public @interface PersonDefault {
}
