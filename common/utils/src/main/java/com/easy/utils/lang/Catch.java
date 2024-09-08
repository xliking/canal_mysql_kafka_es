package com.easy.utils.lang;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * <p>异常捕获</p>
 *
 * @author muchi
 * @since 2023/7/5 14:29
 */
public class Catch<T> {

    private final Supplier<T> trySupplier;

    private Runnable finalRunnable;

    private Catch() {
        throw new IllegalAccessError(this.getClass().getName());
    }

    private Catch(Supplier<T> trySupplier) {
        this.trySupplier = trySupplier;
    }

    public static <T> Catch<T> of(Supplier<T> trySupplier) {

        Objects.requireNonNull(trySupplier);
        return new Catch<>(trySupplier);
    }

    public Catch<T> last(Runnable finalRunnable) {

        this.finalRunnable = finalRunnable;
        return this;
    }

    public T handle(Supplier<T> catchSupplier) {
        try {
            return trySupplier.get();
        } catch (Exception e) {
            e.printStackTrace();
            return catchSupplier.get();
        } finally {
            if (null != finalRunnable) {
                finalRunnable.run();
            }
        }
    }
}