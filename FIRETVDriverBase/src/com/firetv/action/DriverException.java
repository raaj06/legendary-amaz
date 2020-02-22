package com.firetv.action;

public class DriverException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public DriverException(Exception e) {
        super(e);
    }

    public DriverException(String msg) {
        super(msg);
    }
}
