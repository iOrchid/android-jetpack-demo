package in.zhiwei.jetpack.dagger;

import javax.inject.Qualifier;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Author: zhiwei.
 * Date: 2018/11/7 0007,14:16.
 */
@Qualifier
@Retention(value = RetentionPolicy.RUNTIME)
public @interface PersonDefault {
}
