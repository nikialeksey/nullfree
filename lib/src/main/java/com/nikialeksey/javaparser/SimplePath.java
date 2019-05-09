package com.nikialeksey.javaparser;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.ObjectCreationExpr;

import java.util.Optional;

public class SimplePath implements Path {

    private final Node node;

    public SimplePath(final Node node) {
        this.node = node;
    }

    @Override
    public String asString() {
        final StringBuilder path = new StringBuilder();
        node.walk(Node.TreeTraversal.PARENTS, node -> {
            if (node instanceof ClassOrInterfaceDeclaration) {
                path.insert(0, ((ClassOrInterfaceDeclaration) node).getNameAsString());
                path.insert(0, '$');
            }
            if (node instanceof ObjectCreationExpr) {
                path.insert(0, ((ObjectCreationExpr) node).getType().getNameAsString());
                path.insert(0, '$');
            }
            if (node instanceof MethodDeclaration) {
                path.insert(0, ((MethodDeclaration) node).getNameAsString());
                path.insert(0, '#');
            }
            if (node instanceof CompilationUnit) {
                final Optional<PackageDeclaration> pkg = ((CompilationUnit) node).getPackageDeclaration();
                if (pkg.isPresent()) {
                    path.replace(0, 1, ".");
                    path.insert(0, pkg.get().getNameAsString());
                }
            }
        });
        return path.toString();
    }
}
