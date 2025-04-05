package io.github.maxmmin.sol.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Async {
    public static void await(List<Future<?>> futures) throws ConcurrentException {
        futures = new ArrayList<>(futures);

        while (!futures.isEmpty()) {
            Iterator<Future<?>> iterator = futures.iterator();

            while (iterator.hasNext()) {
                Future<?> future = iterator.next();

                if (future.isDone()) {
                    try {
                        future.get();
                        iterator.remove();
                    } catch (Exception e) {
                        futures.forEach(f -> {
                            if (!f.isDone() && !f.isCancelled()) f.cancel(true);
                        });

                        throw new ConcurrentException(
                                e instanceof ExecutionException ? e.getCause() : e
                        );
                    }
                }
            }
        }
    }

    public static class ConcurrentException extends Exception {
        public ConcurrentException() {
        }

        public ConcurrentException(String message) {
            super(message);
        }

        public ConcurrentException(String message, Throwable cause) {
            super(message, cause);
        }

        public ConcurrentException(Throwable cause) {
            super(cause);
        }

        public ConcurrentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
            super(message, cause, enableSuppression, writableStackTrace);
        }
    }
}

