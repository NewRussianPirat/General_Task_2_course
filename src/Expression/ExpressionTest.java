package Expression;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ExpressionTest {
    @Test
    void createPostfixNotation() {
        assertEquals("2 2 +", Expression.createPostfixNotation("2+2"));
        assertEquals("2 2 2 *+", Expression.createPostfixNotation("2+2*2"));
        assertEquals("2 2 +2 *", Expression.createPostfixNotation("(2+2)*2"));
        assertEquals("2 2 +2 2 +*", Expression.createPostfixNotation("(2+2)*(2+2)"));
        assertEquals("2 2 +2 2 +*", Expression.createPostfixNotation("((2+2)*(2+2))"));
        assertEquals("2 2 +", Expression.createPostfixNotation("2 + 2"));
        assertEquals("2 2 +3 -", Expression.createPostfixNotation("2 + 2 - 3"));
        assertEquals("2 3 -0 4 *+3 2 /-1 +", Expression.createPostfixNotation("2-3+0*4-3/2+1"));
        assertEquals("1 ~2 +", Expression.createPostfixNotation("(-1)+2"));
        assertEquals("3 1 ~+", Expression.createPostfixNotation("3+(-1)"));
        assertEquals("1 ~2 *", Expression.createPostfixNotation("(-1)*2"));
        assertEquals("1 ~~", Expression.createPostfixNotation("-(-1)"));
        assertEquals("1 ~2 +~", Expression.createPostfixNotation("-(-1 + 2)"));
        assertThrows(RuntimeException.class, () -> Expression.createPostfixNotation("2 + 3)"));
        assertThrows(RuntimeException.class, () -> Expression.createPostfixNotation("(2 + 3"));
        assertThrows(RuntimeException.class, () -> Expression.createPostfixNotation("(2-3)*(4+2)-8+3)"));
        assertThrows(RuntimeException.class, () -> Expression.createPostfixNotation("2++2"));
        assertThrows(RuntimeException.class, () -> Expression.createPostfixNotation("2--2"));
        assertThrows(RuntimeException.class, () -> Expression.createPostfixNotation("2**2"));
    }

    @Test
    void calculate() {
        assertEquals(4, Expression.calculate("2+2"));
        assertEquals(6, Expression.calculate("2+2*2"));
        assertEquals(8, Expression.calculate("(2+2)*2"));
        assertEquals(16, Expression.calculate("(2+2)*(2+2)"));
        assertEquals(16, Expression.calculate("((2+2)*(2+2))"));
        assertEquals(4, Expression.calculate("2 + 2"));
        assertEquals(1, Expression.calculate("2 + 2 - 3"));
        assertEquals(-1.5, Expression.calculate("2-3+0*4-3/2+1"));
        assertEquals(1, Expression.calculate("(-1)+2"));
        assertEquals(2, Expression.calculate("3+(-1)"));
        assertEquals(-2, Expression.calculate("(-1)*2"));
        assertEquals(1, Expression.calculate("-(-1)"));
        assertEquals(-1, Expression.calculate("-(-1 + 2)"));
        assertThrows(RuntimeException.class, () -> Expression.calculate("1/0"));
        assertThrows(RuntimeException.class, () -> Expression.calculate("+"));
        assertThrows(RuntimeException.class, () -> Expression.calculate("1 +"));
        assertThrows(RuntimeException.class, () -> Expression.calculate("-"));
    }
}