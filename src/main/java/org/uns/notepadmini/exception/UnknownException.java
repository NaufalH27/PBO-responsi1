package org.uns.notepadmini.exception;

public class UnknownException extends Exception {
    private final  StackTraceElement[]  stackTrace;
    private final String ERROR_MSG = "terdapat internal error";

    public UnknownException(Exception e) {
        e.printStackTrace();
        this.stackTrace =e.getStackTrace();
    }


    @Override
    public StackTraceElement[] getStackTrace() {
        return this.stackTrace;
    }

    public String getMsg() {
        return this.ERROR_MSG;
    }
    

}
