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

    public double operate(char c, double first, double second) {
        return switch (c) {
            case '+' -> first + second;
            case '-' -> first - second;
            case '*' -> first * second;
            case '/' -> first / second;
            default -> 0;
        };
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
                    double a = doubleStack.pop();
                    doubleStack.push(-a);
                }
                else {
                    double second = doubleStack.pop();
                    double first = doubleStack.pop();
                    doubleStack.push(operate(c, first, second));
                }
            }
        }

        return doubleStack.pop();
    }
    
    public String createPostfixNotation(String expression) {
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
                while (!operatorsStack.empty() && operatorsStack.peek() != '(') {
                    stringBuilder.append(operatorsStack.pop());
                }
                if (operatorsStack.empty()) {
                    throw new RuntimeException("Wrong number of parentheses");
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
        }

        while (!operatorsStack.empty()) {
            if (operatorsStack.peek() == '(') {
                throw new RuntimeException("Wrong number of parentheses");
            }
            stringBuilder.append(operatorsStack.pop());
        }

        return stringBuilder.toString();
    }
}
