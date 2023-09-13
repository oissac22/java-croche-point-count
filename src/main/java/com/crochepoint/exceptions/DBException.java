package com.crochepoint.exceptions;

public class DBException extends RuntimeException {
    public DBException(String msg) {
        super(msg);
    }
}
