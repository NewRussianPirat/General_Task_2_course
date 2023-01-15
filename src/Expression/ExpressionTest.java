package Expression;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ExpressionTest {
    @Test
    void createPostfixNotation() {
        Expression expression = new Expression();
        assertEquals("2 2 +", expression.createPostfixNotation("2+2"));
        assertEquals("2 2 2 *+", expression.createPostfixNotation("2+2*2"));
        assertEquals("2 2 +2 *", expression.createPostfixNotation("(2+2)*2"));
        assertEquals("2 2 +2 2 +*", expression.createPostfixNotation("(2+2)*(2+2)"));
        assertEquals("2 2 +2 2 +*", expression.createPostfixNotation("((2+2)*(2+2))"));
        assertEquals("2 2 +", expression.createPostfixNotation("2 + 2"));
        assertEquals("2 2 +3 -", expression.createPostfixNotation("2 + 2 - 3"));
        assertEquals("2 3 -0 4 *+3 2 /-1 +", expression.createPostfixNotation("2-3+0*4-3/2+1"));
        assertEquals("1 ~2 +", expression.createPostfixNotation("(-1)+2"));
        assertEquals("3 1 ~+", expression.createPostfixNotation("3+(-1)"));
        assertEquals("1 ~2 *", expression.createPostfixNotation("(-1)*2"));
        assertEquals("1 ~~", expression.createPostfixNotation("-(-1)"));
        assertEquals("1 ~2 +~", expression.createPostfixNotation("-(-1 + 2)"));
        assertThrows(RuntimeException.class, () -> expression.createPostfixNotation("2 + 3)"));
        assertThrows(RuntimeException.class, () -> expression.createPostfixNotation("(2 + 3"));
        assertThrows(RuntimeException.class, () -> expression.createPostfixNotation("(2-3)*(4+2)-8+3)"));
        assertThrows(RuntimeException.class, () -> expression.createPostfixNotation("2++2"));
        assertThrows(RuntimeException.class, () -> expression.createPostfixNotation("2--2"));
        assertThrows(RuntimeException.class, () -> expression.createPostfixNotation("2**2"));
    }

    @Test
    void calculate() {
        Expression expression = new Expression();
        assertEquals(4, expression.calculate("2+2"));
        assertEquals(6, expression.calculate("2+2*2"));
        assertEquals(8, expression.calculate("(2+2)*2"));
        assertEquals(16, expression.calculate("(2+2)*(2+2)"));
        assertEquals(16, expression.calculate("((2+2)*(2+2))"));
        assertEquals(4, expression.calculate("2 + 2"));
        assertEquals(1, expression.calculate("2 + 2 - 3"));
        assertEquals(-1.5, expression.calculate("2-3+0*4-3/2+1"));
        assertEquals(1, expression.calculate("(-1)+2"));
        assertEquals(2, expression.calculate("3+(-1)"));
        assertEquals(-2, expression.calculate("(-1)*2"));
        assertEquals(1, expression.calculate("-(-1)"));
        assertEquals(-1, expression.calculate("-(-1 + 2)"));
        assertThrows(ArithmeticException.class, () -> expression.calculate("1/0"));
    }
}