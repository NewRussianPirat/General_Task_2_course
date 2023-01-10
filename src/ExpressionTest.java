import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ExpressionTest {
    @Test
    void createPostfixNotation() {
        assertEquals("2 2 +", new Expression().createPostfixNotation("2+2"));
        assertEquals("2 2 2 *+", new Expression().createPostfixNotation("2+2*2"));
        assertEquals("2 2 +2 *", new Expression().createPostfixNotation("(2+2)*2"));
    }
}