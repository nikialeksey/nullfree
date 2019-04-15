package com.nikialeksey.functions;

public interface Function1<R, A, E extends Exception> {
    R execute(A a1) throws E;
}
