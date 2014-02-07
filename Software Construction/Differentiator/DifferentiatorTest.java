package differentiator;

import static org.junit.Assert.*;

import org.junit.Test;

public class DifferentiatorTest {
    
    // Partitioning the input space, checking that both valid and invalid inputs are handled correctly.
    // Begins with simple inputs and progresses to more valid inputs
    // There is a bug in my parser though so tests fail.

    @Test
    public void testOneVar() {
        String input = "(x)";
        String var = "x";
        String expected = "1";
        Differentiator diff = new Differentiator();
        assertEquals(expected, diff.evaluate(input, var));
    }
    
    @Test
    public void testOneNum() {
        String input = "(1)";
        String var = "x";
        String expected = "0";
        Differentiator diff = new Differentiator();
        assertEquals(expected, diff.evaluate(input, var));
    }
    
    @Test
    public void testAddMultVar() {
        String input = "(x + y)";
        String var = "x";
        String expected = "(1 + 0)";
        Differentiator diff = new Differentiator();
        assertEquals(expected, diff.evaluate(input, var));
    }
    
    @Test
    public void testMulMultVar() {
        String input = "(x * y)";
        String var = "x";
        String expected = "((x * 0) + (y * 1))";
        Differentiator diff = new Differentiator();
        assertEquals(expected, diff.evaluate(input, var));
    }
    
    @Test
    public void testMulNum() {
        String input = "(2 * 3)";
        String var = "x";
        String expected = "((2 * 0) + (3 * 0))";
        Differentiator diff = new Differentiator();
        assertEquals(expected, diff.evaluate(input, var));
    }
    
    @Test
    public void testMulNumVar() {
        String input = "(x * 2)";
        String var = "x";
        String expected = "((x * 0) + (2 * 1))";
        Differentiator diff = new Differentiator();
        assertEquals(expected, diff.evaluate(input, var));
    }
    
    @Test
    public void testLeftNested(){
        String input = "(x * (x * 2))";
        String var = "x";
        String expected = "((x * ((x * 0) + (2 * 1))) + ((x * 2) * 1))";
        Differentiator diff = new Differentiator();
        assertEquals(expected, diff.evaluate(input, var));
    }
    
    @Test
    public void testRightNested(){
        String input = "((x * x) * 2)";
        String var = "x";
        String expected = "(((x * x) * 0) + (2 * ((x * 1) + (x * 1))))";
        Differentiator diff = new Differentiator();
        assertEquals(expected, diff.evaluate(input, var));
    }
    
    @Test
    public void testDoubleNested(){
        String input = "((x * x) + (2 * y))";
        String var = "x";
        String expected = "(((x * 1) + (x * 1)) + ((2 * 0) + (y * 0)))";
        Differentiator diff = new Differentiator();
        assertEquals(expected, diff.evaluate(input, var));
    }
    
    @Test
    public void testNum() {
        String input = "(2 + 3)";
        String var = "x";
        String expected = "(0 + 0)";
        Differentiator diff = new Differentiator();
        assertEquals(expected, diff.evaluate(input, var));
    }
    
    @Test
    public void testFloat() {
        String input = "(2.0 + 3.5)";
        String var = "x";
        String expected = "(0 + 0)";
        Differentiator diff = new Differentiator();
        assertEquals(expected, diff.evaluate(input, var));
    }
    
    @Test
    public void testAdditionNum() {
        String input = "((2.0 + 3.5) + ((x * 2) * 3.5))";
        String var = "x";
        String expected = "((0 + 0) + (((x * 2) * 0) + (3.5 * ((x * 0) + (2 * 1)))))";
        Differentiator diff = new Differentiator();
        assertEquals(expected, diff.evaluate(input, var));
    }
    
    @Test
    public void testLotsOfNumAndVar() {
        String input = "((y * x) + (((3 * 2) * (x * 4.0)) + z))";
        String var = "x";
        String expected = "(((y * 1) + (x * 0)) + (((3 * 2) * ((x * 0) + (4.0 * 1)) + ((x * 4.0) * ((3 * 0) + (2 * 0)))) + 0))";
        Differentiator diff = new Differentiator();
        System.out.println(diff.evaluate(input, var));
        assertEquals(expected, diff.evaluate(input, var));
    }
    
    @Test(expected = InvalidInputException.class)
    public void unParsableParens() {
        String input = "((2 + 3)";
        String var = "x";
        Differentiator diff = new Differentiator();
        diff.evaluate(input, var);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void unParsableInput() {
        String input = "(! + 3)";
        String var = "x";
        Differentiator diff = new Differentiator();
        diff.evaluate(input, var);
    }
    
    @Test(expected = InvalidInputException.class)
    public void unParsableNoParens() {
        String input = "2 + 3)";
        String var = "x";
        Differentiator diff = new Differentiator();
        diff.evaluate(input, var);
    }

}
