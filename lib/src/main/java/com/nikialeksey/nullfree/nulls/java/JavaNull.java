package com.nikialeksey.nullfree.nulls.java;

import com.github.javaparser.Range;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.NullLiteralExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.nodeTypes.NodeWithAnnotations;
import com.nikialeksey.nullfree.nulls.Null;

import java.util.Arrays;
import java.util.List;
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

    @Override
    public boolean isSuppressed() {
        final List<Boolean> result = Arrays.asList(false);
        expr.walk(Node.TreeTraversal.PARENTS, node -> {
            if (node instanceof NodeWithAnnotations) {
                final NodeWithAnnotations declaration = (NodeWithAnnotations) node;

                if (declaration.isAnnotationPresent("SuppressWarnings")) {
                    final Optional<AnnotationExpr> suppressAnnotation = node.findFirst(
                        AnnotationExpr.class
                    );
                    if (suppressAnnotation.isPresent()) {
                        final List<StringLiteralExpr> values = suppressAnnotation.get().findAll(
                            StringLiteralExpr.class
                        );
                        for (final StringLiteralExpr value : values) {
                            if ("nullfree".equals(value.asString())) {
                                result.set(0, true);
                            }
                        }
                    }
                }
            }
        });

        return result.get(0);
    }

    @Override
    public boolean isInComparision() {
        final List<Boolean> result = Arrays.asList(false);
        final Optional<Node> optParent = expr.getParentNode();
        if (optParent.isPresent()) {
            final Node parent = optParent.get();
            final List<BinaryExpr> found = parent.findAll(BinaryExpr.class);
            if (found.size() == 1) {
                final BinaryExpr.Operator operator = found.get(0).getOperator();
                if (operator == BinaryExpr.Operator.NOT_EQUALS || operator == BinaryExpr.Operator.EQUALS) {
                    result.set(0, true);
                }
            }
        }
        return result.get(0);
    }
}
