import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ExpressionTest {
    @Test
    void createPostfixNotation() {
        assertEquals("2 2 +", new Expression().createPostfixNotation("2+2"));
        assertEquals("2 2 2 *+", new Expression().createPostfixNotation("2+2*2"));
        assertEquals("2 2 +2 *", new Expression().createPostfixNotation("(2+2)*2"));
        assertEquals("2 2 +2 2 +*", new Expression().createPostfixNotation("(2+2)*(2+2)"));
        assertEquals("2 2 +2 2 +*", new Expression().createPostfixNotation("((2+2)*(2+2))"));
        assertEquals("2 2 +", new Expression().createPostfixNotation("2 + 2"));
        assertEquals("2 2 +3 -", new Expression().createPostfixNotation("2 + 2 - 3"));
        assertEquals("2 3 -0 4 *+3 2 /-1 +", new Expression().createPostfixNotation("2-3+0*4-3/2+1"));
        assertEquals("1 -2 +", new Expression().createPostfixNotation("(-1)+2"));
        assertEquals("3 1 -+", new Expression().createPostfixNotation("3+(-1)"));
        assertEquals("1 -2 *", new Expression().createPostfixNotation("(-1)*2"));
        assertThrows(RuntimeException.class, () -> new Expression().createPostfixNotation("2 + 3)"));
        assertThrows(RuntimeException.class, () -> new Expression().createPostfixNotation("(2 + 3"));
        assertThrows(RuntimeException.class, () -> new Expression().createPostfixNotation("(2-3)*(4+2)-8+3)"));
        assertThrows(RuntimeException.class, () -> new Expression().createPostfixNotation("2++2"));
        assertThrows(RuntimeException.class, () -> new Expression().createPostfixNotation("2--2"));
        assertThrows(RuntimeException.class, () -> new Expression().createPostfixNotation("2**2"));
    }
}