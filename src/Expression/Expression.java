package Expression;

import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Stack;

public class Expression {
    private static final HashMap<Character, Integer> operators = new HashMap<>() {{
        put('(', 0);
        put('+', 1);
        put('-', 1);
        put('*', 2);
        put('/', 2);
        put('~', 3);
    }};

    private double operate(char c, double first, double second) {
        switch (c) {
            case '+' : return first + second;
            case '-' : return first - second;
            case '*' : return first * second;
            case '/' : {
                if (second == 0) {
                    throw new RuntimeException("Division by zero");
                }
                return first / second;
            }
            default: return 0;
        }
    }

    public double calculate(String expression) {
        String postfixNotationExpression = createPostfixNotation(expression);
        Stack<Double> doubleStack = new Stack<>();

        for (int i = 0; i < postfixNotationExpression.length(); ++i) {
            char c = postfixNotationExpression.charAt(i);
            if (Character.isDigit(c)) {
                StringBuilder stringBuilder = new StringBuilder();
                int pos = i;

                while (pos < postfixNotationExpression.length() && Character.isDigit(postfixNotationExpression.charAt(pos))) {
                    stringBuilder.append(postfixNotationExpression.charAt(pos));
                    ++pos;
                }

                stringBuilder.append(' ');
                i = pos - 1;

                doubleStack.push(Double.parseDouble(stringBuilder.toString()));
            }
            else if (operators.containsKey(c)) {
                if (c == '~') {
                    try {
                        double a = doubleStack.pop();
                        doubleStack.push(-a);
                    }
                    catch (EmptyStackException e) {
                        throw new RuntimeException("Wrong expression format");
                    }
                }
                else {
                    try {
                        double second = doubleStack.pop();
                        double first = doubleStack.pop();
                        doubleStack.push(operate(c, first, second));
                    }
                    catch (EmptyStackException e) {
                        throw new RuntimeException("Wrong expression format");
                    }
                }
            }
        }

        return doubleStack.pop();
    }
    
    public String createPostfixNotation(String expression) {
        if (expression == null || expression.equals("")) {
            throw new RuntimeException("No expression");
        }
        Stack<Character> operatorsStack = new Stack<>();
        StringBuilder stringBuilder = new StringBuilder();
        char lastSymbol = ' ';

        for (int i = 0; i < expression.length(); ++i) {
            char c = expression.charAt(i);
            if (Character.isDigit(c)) {
                int pos = i;

                while (pos < expression.length() && Character.isDigit(expression.charAt(pos))) {
                    stringBuilder.append(expression.charAt(pos));
                    ++pos;
                }

                stringBuilder.append(' ');
                i = pos - 1;
                lastSymbol = '1';
            }
            else if (c == '(') {
                operatorsStack.push('(');
                lastSymbol = '(';
            }
            else if (c == ')') {
                if (lastSymbol == '(') {
                    throw new RuntimeException("Wrong expression format");
                }
                while (!operatorsStack.empty() && operatorsStack.peek() != '(') {
                    stringBuilder.append(operatorsStack.pop());
                }
                if (operatorsStack.empty()) {
                    throw new RuntimeException("Wrong combination of parentheses");
                }
                operatorsStack.pop();
                lastSymbol = ')';
            }
            else if (operators.containsKey(c)) {
                if (operators.containsKey(lastSymbol) && lastSymbol != '(') {
                    throw new RuntimeException("2 operators in a row are not allowed");
                }
                else {
                    if (c == '-' && (i == 0 || lastSymbol == '(')) {
                        c = '~';
                    }
                    while (!operatorsStack.empty() && operators.get(operatorsStack.peek()) >= operators.get(c)) {
                        stringBuilder.append(operatorsStack.pop());
                    }
                    operatorsStack.push(c);
                    lastSymbol = c;
                }
            }
            else if (c != ' ') {
                throw new RuntimeException("Wrong expression format");
            }
        }

        while (!operatorsStack.empty()) {
            if (operatorsStack.peek() == '(') {
                throw new RuntimeException("Wrong combination of parentheses");
            }
            stringBuilder.append(operatorsStack.pop());
        }

        return stringBuilder.toString();
    }
}
