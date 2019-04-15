package com.nikialeksey.nullfree;

import com.github.javaparser.ast.expr.NullLiteralExpr;

public class JavaNull implements Null {

    private final NullLiteralExpr expr;

    public JavaNull(final NullLiteralExpr expr) {
        this.expr = expr;
    }

    @Override
    public String description() {
        return expr.getRange().get().toString();
    }
}
