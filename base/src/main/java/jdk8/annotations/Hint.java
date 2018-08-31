package jdk8.annotations;

import java.lang.annotation.Repeatable;

/**
 * Created by mengtian on 2018/8/28
 * <p>
 * 添加 Repeatable 允许对同一类型使用多重注解
 */
@Repeatable(Hints.class)
public @interface Hint {
    String value();
}
