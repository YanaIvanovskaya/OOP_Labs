import exceptions.SemanticError;
import exceptions.SemanticException;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ExpressionEvaluator {

    private ExpressionEvaluator() {
    }

    public static void evaluate(@NotNull List<Expression> expressions) throws SemanticException {
        checkSemantic(expressions);

        Map<Expression, Double> result = new HashMap<>();

        for (int i = 0; i < expressions.size(); i++) {
            Expression exp = expressions.get(i);
            if (exp instanceof Expression.Declaration) {
                result.put(exp, null);
            } else if (exp instanceof Expression.Initialization expInit) {
                switch (expInit.type()) {
                    case NUMBER -> result.put(exp, Double.parseDouble(expInit.value()));
                    case IDENTIFIER -> {
                        filterVariables(expressions.subList(0, i))
                                .stream()
                                .filter((e) -> e.getName().equals(expInit.getValue()))
                                .findFirst()
                        .get()
                        .getValue();
                    }
                }
            } else {
                System.out.println("unreachable");
            }

        }


    }

    private static void checkSemantic(List<Expression> expressions) throws SemanticException {
        List<Expression.Variable> variables = filterVariables(expressions);
        checkVariableUnique(variables);
        checkVariableDeclaration(variables);
    }

    private static List<Expression.Variable> filterVariables(List<Expression> expressions) {
        return expressions.stream()
                .filter((Expression exp) -> exp instanceof Expression.Variable)
                .map((Expression exp) -> (Expression.Variable) exp)
                .toList();
    }

    private static void checkVariableUnique(List<Expression.Variable> variables) {
        List<String> declarations = variables.stream()
                .map(Expression.Variable::getName)
                .toList();
        List<String> uniqueDeclarations = declarations.stream()
                .distinct()
                .toList();

        if (declarations.size() != uniqueDeclarations.size()) {
            throw new SemanticException(SemanticError.VARIABLE_ALREADY_DEFINED);
        }
    }

    private static List<String> filterDeclarationVariables(List<Expression.Variable> expressions) {
        return expressions.stream()
                .filter((Expression exp) -> exp instanceof Expression.Variable.Declaration)
                .map((Expression exp) -> (Expression.Variable.Declaration) exp)
                .map(Expression.Declaration::identifier)
                .distinct()
                .toList();
    }

    private static List<String> filterUsableVariables(List<Expression.Variable> variables) {
        return variables.stream()
                .filter((Expression exp) -> exp instanceof Expression.Initialization)
                .filter((exp) -> ((Expression.Initialization) exp).type()
                        == Expression.Initialization.ValueType.IDENTIFIER)
                .map((exp) -> ((Expression.Variable.Initialization) exp).value())
                .distinct()
                .toList();
    }

    private static void checkVariableDeclaration(List<Expression.Variable> expressions) {
        List<String> declared = filterDeclarationVariables(expressions);
        List<String> used = filterUsableVariables(expressions);
        if (declared.size() != used.size()) {
            throw new SemanticException(SemanticError.VARIABLE_NOT_DECLARED);
        }
    }

}


