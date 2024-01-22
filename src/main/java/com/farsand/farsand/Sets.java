package com.farsand.farsand;

import java.util.HashSet;
import java.util.Set;


public final class Sets {

    private Sets() {
        // private constructor to prevent instantiation
    }

    /**
     * Creates a new mutable {@code HashSet} instance containing the given elements.
     *
     * @param elements the elements to include in the set
     * @param <E>      the type of elements
     * @return a new {@code HashSet} containing the given elements
     */
    public static <E> Set<E> newHashSet(E... elements) {
        Set<E> set = new HashSet<>();
        for (E element : elements) {
            set.add(element);
        }
        return set;
    }

    // Add other set utility methods as needed

}