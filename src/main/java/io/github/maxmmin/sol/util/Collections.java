package io.github.maxmmin.sol.util;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class Collections {
    public static <V> Iterator<List<V>> partition(List<V> list, int partSize) {
        return new Iterator<List<V>>() {
            private int idx = 0;

            @Override
            public boolean hasNext() {
                return idx < list.size();
            }

            @Override
            public List<V> next() {
                if (!hasNext()) throw new NoSuchElementException();
                int listSize = list.size();
                int startIdx = this.idx;
                int endIdx = Math.min(startIdx + partSize, listSize);
                this.idx = endIdx;
                return list.subList(startIdx, endIdx);
            }
        };
    }
}
