package com.nikialeksey.nullfree;

import com.github.javaparser.Range;
import com.github.javaparser.ast.expr.NullLiteralExpr;

import java.util.Optional;

public class JavaNull implements Null {

    private final NullLiteralExpr expr;

    public JavaNull(final NullLiteralExpr expr) {
        this.expr = expr;
    }

    @Override
    public String description() {
        final Optional<Range> range = expr.getRange();
        final String description;
        if (range.isPresent()) {
            description = range.get().toString();
        } else if (expr.getParentNode().isPresent()) {
            description = expr.getParentNode().get().toString();
        } else {
            description = expr.findRootNode().toString();
        }
        return description;
    }
}
