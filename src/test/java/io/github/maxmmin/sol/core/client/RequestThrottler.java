package io.github.maxmmin.sol.core.client;

import java.time.Duration;
import java.util.concurrent.locks.LockSupport;

public class RequestThrottler {
    private final long nanosGap;

    public RequestThrottler(int RPS) {
        if (RPS < 1) throw new IllegalArgumentException("Invalid requests per second");
        this.nanosGap = Duration.ofSeconds(1).toNanos() / RPS;
    }

    //@todo maybe replace with normal rate limiter
    public void throttle() {
        LockSupport.parkNanos(nanosGap);
    }

    public static RequestThrottler create () {
        return new RequestThrottler(TestClientProperties.getRequestsPerSecond());
    }
}
