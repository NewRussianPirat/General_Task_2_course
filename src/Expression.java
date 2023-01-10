import java.util.HashMap;
import java.util.Stack;

public class Expression {
    private static final HashMap<Character, Integer> operators = new HashMap<>() {{
        put('(', 0);
        put('+', 1);
        put('-', 1);
        put('*', 2);
        put('/', 2);
    }};

    public int calculate(String expression) {
        return 0;
    }
    
    public String createPostfixNotation(String expression) {
        Stack<Character> operatorsStack = new Stack<>();
        StringBuilder stringBuilder = new StringBuilder();

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
            }
            else if (c == '(') {
                operatorsStack.push('(');
            }
            else if (c == ')') {
                while (operatorsStack.peek() != '(') {
                    stringBuilder.append(operatorsStack.pop());
                }
                operatorsStack.pop();
            }
            else if (operators.containsKey(c)) {
                while (!operatorsStack.empty() && operators.get(operatorsStack.peek()) >= operators.get(c)) {
                    stringBuilder.append(operatorsStack.pop());
                }
                operatorsStack.push(c);
            }
        }

        while (!operatorsStack.empty()) {
            stringBuilder.append(operatorsStack.pop());
        }

        return stringBuilder.toString();
    }
}
