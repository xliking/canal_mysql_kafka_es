package com.easy.utils;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * <p>条件执行器</p>
 *
 * @author muchi
 * @since 2023/9/14 11:26
 */
public class Condition<T> {

    private final T t;

    private final Function<T, Boolean> function;

    private Condition(T t, Function<T, Boolean> function) {
        this.t = t;
        this.function = function;
    }

    private Condition() {
        throw new IllegalAccessError(this.getClass().getName());
    }

    public static <T> Condition<T> of(T t, Function<T, Boolean> function) {

        Objects.requireNonNull(function);
        return new Condition<>(t, function);
    }

    public Boolean get() {
        return function.apply(t);
    }

    public void handle(Consumer<T> consumer) {
        if (function.apply(t)) {
            consumer.accept(t);
        }
    }

    public void handle(Consumer<T> consumerTrue, Consumer<T> consumerFalse) {
        if (function.apply(t)) {
            consumerTrue.accept(t);
        } else {
            consumerFalse.accept(t);
        }
    }
}