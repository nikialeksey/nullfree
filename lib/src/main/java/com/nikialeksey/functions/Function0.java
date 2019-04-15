package com.nikialeksey.functions;

public interface Function0<R, E extends Exception> {
    R execute() throws E;
}
