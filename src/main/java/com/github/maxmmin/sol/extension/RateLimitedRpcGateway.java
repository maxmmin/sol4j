package com.github.maxmmin.sol.extension;

import io.github.bucket4j.Bucket;
import lombok.extern.slf4j.Slf4j;
import com.github.maxmmin.sol.core.client.DelegatingRpcGateway;
import com.github.maxmmin.sol.core.client.RpcGateway;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


@Slf4j
public class RateLimitedRpcGateway extends DelegatingRpcGateway {
    private final Bucket bucket;
    private final Lock lock = new ReentrantLock();

    public RateLimitedRpcGateway(RpcGateway rpcGateway, Bucket bucket) {
        super(rpcGateway);
        this.bucket = bucket;
    }

    @Override
    protected void beforeEach(RequestContext requestContext) {
        boolean multi = requestContext.getWeight() > 1;
        if (multi) {
            lock.lock();
            try {
                consumeToken();
                int leftWeight = requestContext.getWeight() - 1;
                if (leftWeight > 0) consumeImmediately(leftWeight);
            } finally {
                lock.unlock();
            }
        }
        else consumeToken();
    }

    protected void consumeToken() {
        try {
            bucket.asBlocking().consume(1L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    protected void consumeImmediately(long tokens) {
        bucket.consumeIgnoringRateLimits(tokens);
    }
}
