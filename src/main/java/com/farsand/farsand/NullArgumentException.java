package com.farsand.farsand;

public class NullArgumentException extends IllegalArgumentException {

    public NullArgumentException(String argumentName) {
        super("Argument '" + argumentName + "' cannot be null");
    }
}