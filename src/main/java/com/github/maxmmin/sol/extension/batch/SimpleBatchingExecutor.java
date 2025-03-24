package com.github.maxmmin.sol.extension.batch;

import lombok.RequiredArgsConstructor;
import com.github.maxmmin.clue.core.util.CollectionsUtil;
import com.github.maxmmin.clue.core.util.Async;
import com.github.maxmmin.clue.core.util.DaemonThreadFactory;
import com.github.maxmmin.sol.core.exception.RpcException;
import com.github.maxmmin.sol.core.exception.WrappedRpcException;
import com.github.maxmmin.sol.core.type.request.RpcRequest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

@RequiredArgsConstructor
public class SimpleBatchingExecutor implements RpcBatchingExecutor {
    private final ExecutorService executorService;


    @Override
    public <V> void execute(List<RpcRequest> requests, int batchSize, RpcRequestsHandler handler) throws RpcException {
        if (batchSize < 1) throw new IllegalArgumentException("batchSize must be greater than 0");

        Iterator<List<RpcRequest>> iterator = CollectionsUtil.partition(requests, batchSize);
        List<Future<?>> futures = new ArrayList<>(requests.size());
        while (iterator.hasNext()) {
            List<RpcRequest> rpcRequests = iterator.next();
            Future<?> future = run(rpcRequests, handler);
            futures.add(future);
        }
        try {
            Async.await(futures);
        } catch (Async.ConcurrentException e) {
            if (e.getCause() instanceof RpcException rpcException) throw rpcException;
            else if (e.getCause() instanceof WrappedRpcException wrappedRpcException) throw wrappedRpcException.getCause();
            else throw new RuntimeException(e);
        }
    }

    protected Future<?> run(List<RpcRequest> requests, RpcRequestsHandler handler) {
        return executorService.submit(() -> {
            try {
                handler.accept(requests);
            } catch (RpcException rpcException) {
                throw new WrappedRpcException(rpcException);
            }
        });
    }

    public static SimpleBatchingExecutor forkJoinPool() {
        return new SimpleBatchingExecutor(ForkJoinPool.commonPool());
    }

    public static SimpleBatchingExecutor singleThreadPool() {
        return new SimpleBatchingExecutor(Executors.newSingleThreadExecutor(new DaemonThreadFactory("batching-executor-single-pool")));
    }
}
