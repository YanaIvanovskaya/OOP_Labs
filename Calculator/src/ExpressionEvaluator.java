import exceptions.SemanticException;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class ExpressionEvaluator {

    private ExpressionEvaluator() {
    }

    public static void evaluate(@NotNull List<Expression> expressions) throws SemanticException {
        checkSemantic(expressions);
    }

    private static void checkSemantic(List<Expression> expressions) throws SemanticException {
        List<Expression.Variable> variables = filterVariables(expressions);
        checkVariableUnique(variables);

//        for (int i = 1; i < expressions.size(); i++) {
//            List<Expression> expressionsAbove = expressions.subList(0, i);
//
//        }
    }

    private static List<Expression.Variable> filterVariables(List<Expression> expressions) {
        return expressions.stream()
                .filter((Expression exp) -> exp instanceof Expression.Variable)
                .map((Expression exp) -> (Expression.Variable) exp)
                .toList();
    }

    private static List<Expression.Variable.Declaration> filterDeclarationVariables(List<Expression> expressions) {
        return expressions.stream()
                .filter((Expression exp) -> exp instanceof Expression.Variable.Declaration)
                .map((Expression exp) -> (Expression.Variable.Declaration) exp)
                .toList();
    }

    private static void checkVariableUnique(List<Expression.Variable> variables) {
        List<String> declarations = variables.stream()
                .filter((Expression exp) -> exp instanceof Expression.Variable.Declaration)
                .map((Expression exp) -> ((Expression.Variable) exp).getName())
                .toList();
        List<String> uniqueDeclarations = declarations.stream()
                .distinct()
                .toList();

        if (declarations.size() != uniqueDeclarations.size()) {
            throw new SemanticException();
        }
    }

    private static void filterUsableVariables(List<Expression.Variable> variables) {
        List<String> uses = variables.stream()
                .filter((Expression exp) -> exp instanceof Expression.Variable.Initialization)
                //.filter((Expression.Initialization exp) -> exp.isRedeclaration())
                .map((Expression exp) -> ((Expression.Variable.Initialization) exp).value())
                .toList();
    }

    private static void checkVariableDeclaration(List<Expression> expressions) {
        filterDeclarationVariables(expressions);
    }

}


