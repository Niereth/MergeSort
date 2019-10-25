package com.teterin.mergesort.comparators;

public interface ISortComparator {

    <T extends Comparable<T>> int compare(T o1, T o2);
}
