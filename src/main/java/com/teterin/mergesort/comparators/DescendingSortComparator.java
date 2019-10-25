package com.teterin.mergesort.comparators;

public class DescendingSortComparator implements ISortComparator {

    @Override
    public <T extends Comparable<T>> int compare(T o1, T o2) {
        return -o1.compareTo(o2);
    }
}
