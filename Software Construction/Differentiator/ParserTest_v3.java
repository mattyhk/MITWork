package differentiator;

//import static org.easymock.EasyMock;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

import differentiator.Parser_v3.Addition;
import differentiator.Parser_v3.Expression;
import differentiator.Parser_v3.Multiplication;
import differentiator.Parser_v3.Variable;
import differentiator.Parser_v3.Number;


// Need to split the valid and invalid input spaces into the following tests:

public class ParserTest_v3{
    
    @Test
    public void simpleVarTest() {
        String input = "(x)";
        Lexer lexer = new Lexer(input);
        Parser_v3 Parser_v3 = new Parser_v3(lexer);
        Expression exp = Parser_v3.parse();
        Token var = new Token("x");
        Variable expected = new Variable(var);
        assertEquals(exp.toString(), expected.toString());
    }
    
    @Test
    public void simpleNumTest() {
        String input = "(2.0)";
        Lexer lexer = new Lexer(input);
        Parser_v3 Parser_v3 = new Parser_v3(lexer);
        Expression exp = Parser_v3.parse();
        Token num = new Token("2.0");
        Number expected = new Number(num);
        assertEquals(exp.toString(), expected.toString());
    }
    
    @Test
    public void simpleAddTest() {
        String input = "(x + 2.0)";
        Lexer lexer = new Lexer(input);
        Parser_v3 Parser_v3 = new Parser_v3(lexer);
        Expression exp = Parser_v3.parse();
        Token var = new Token("x");
        Token num = new Token("2.0");
        Variable expectVar = new Variable(var);
        Number expectNum = new Number(num);
        Addition expected = new Addition(expectVar, expectNum);
        assertEquals(exp.toString(), expected.toString());
    }
    
    @Test
    public void simpleMulTest() {
        String input = "(x * 2.0)";
        Lexer lexer = new Lexer(input);
        Parser_v3 Parser_v3 = new Parser_v3(lexer);
        Expression exp = Parser_v3.parse();
        Token var = new Token("x");
        Token num = new Token("2.0");
        Variable expectVar = new Variable(var);
        Number expectNum = new Number(num);
        Multiplication expected = new Multiplication(expectVar, expectNum);
        assertEquals(exp.toString(), expected.toString());
    }
    
    @Test
    public void nestedRightTest() {
        String input = "(x * (2.0 + y))";
        Lexer lexer = new Lexer(input);
        Parser_v3 Parser_v3 = new Parser_v3(lexer);
        Expression exp = Parser_v3.parse();
        
        Token varOne = new Token("x");
        Token num = new Token("2.0");
        Token varTwo = new Token("y");
        
        Variable expVarOne = new Variable(varOne);
        Number expNum = new Number(num);
        Variable expVarTwo = new Variable(varTwo);
        
        Multiplication expected = new Multiplication(expVarOne, new Addition(expNum, expVarTwo));
        
        assertEquals(exp.toString(), expected.toString());
    }
    
    @Test
    public void nestedLeftTest() {
        String input = "((x + 2.0) * y)";
        Lexer lexer = new Lexer(input);
        Parser_v3 Parser_v3 = new Parser_v3(lexer);
        Expression exp = Parser_v3.parse();
        
        Token varOne = new Token("x");
        Token num = new Token("2.0");
        Token varTwo = new Token("y");
        
        Variable expVarOne = new Variable(varOne);
        Number expNum = new Number(num);
        Variable expVarTwo = new Variable(varTwo);
        
        Multiplication expected = new Multiplication(new Addition(expVarOne, expNum), expVarTwo);

        
        assertEquals(exp.toString(), expected.toString());
    }
    
    @Test
    public void twoNestedTest() {
        String input = "((x + 2.0) * (y + 3.0))";
        Lexer lexer = new Lexer(input);
        Parser_v3 Parser_v3 = new Parser_v3(lexer);
        Expression exp = Parser_v3.parse();
        
        Token varOne = new Token("x");
        Token num = new Token("2.0");
        Token varTwo = new Token("y");
        Token numTwo = new Token("3.0");
        
        Variable expVarOne = new Variable(varOne);
        Number expNum = new Number(num);
        Variable expVarTwo = new Variable(varTwo);
        Number expNumTwo = new Number(numTwo);
        
        Multiplication expected = new Multiplication(new Addition(expVarOne, expNum), new Addition(expVarTwo, expNumTwo));

        
        assertEquals(exp.toString(), expected.toString());
    }
    
    @Test
    public void extraneousParens() {
        String input = "((((x + 2.0))) * (((y + 3.0))))";
        Lexer lexer = new Lexer(input);
        Parser_v3 Parser_v3 = new Parser_v3(lexer);
        Expression exp = Parser_v3.parse();
        
        Token varOne = new Token("x");
        Token num = new Token("2.0");
        Token varTwo = new Token("y");
        Token numTwo = new Token("3.0");
        
        Variable expVarOne = new Variable(varOne);
        Number expNum = new Number(num);
        Variable expVarTwo = new Variable(varTwo);
        Number expNumTwo = new Number(numTwo);
        
        Multiplication expected = new Multiplication(new Addition(expVarOne, expNum), new Addition(expVarTwo, expNumTwo));

        
        assertEquals(exp.toString(), expected.toString());
    }
    
    @Test
    public void mildyComplexTest(){
        String input = "((y * x) + (((3 * 2) * (x * 4.0)) + z))";
        Lexer lexer = new Lexer(input);
        Parser_v3 Parser_v3 = new Parser_v3(lexer);
        Expression exp = Parser_v3.parse();
        
        Addition expected = new Addition(new Multiplication(new Variable(new Token("y")), new Variable(new Token("x"))), 
                new Addition(new Multiplication(new Multiplication(new Number(new Token("3")), new Number(new Token("2"))), 
                        new Multiplication(new Variable(new Token("x")), new Number(new Token("4.0")))), new Variable(new Token("z"))));
        assertEquals(exp.toString(), expected.toString());
        
        
    }
    
    @Test(expected = InvalidInputException.class)
    public void simpleInvalidInput() {
        String input = "x";
        Lexer lexer = new Lexer(input);
        Parser_v3 Parser_v3 = new Parser_v3(lexer);
        Parser_v3.parse();
    }
    
    @Test(expected = InvalidInputException.class)
    public void simpleInvalidMulInput() {
        String input = "*";
        Lexer lexer = new Lexer(input);
        Parser_v3 Parser_v3 = new Parser_v3(lexer);
        Parser_v3.parse();
    }
    
    @Test(expected = InvalidInputException.class)
    public void simpleOpenParens(){
        String input = "(";
        Lexer lexer = new Lexer(input);
        Parser_v3 Parser_v3 = new Parser_v3(lexer);
        Parser_v3.parse();
    }
    
    @Test(expected = InvalidInputException.class)
    public void simpleNoCloseParens(){
        String input = "(x";
        Lexer lexer = new Lexer(input);
        Parser_v3 Parser_v3 = new Parser_v3(lexer);
        Parser_v3.parse();
    }
    
    @Test(expected = InvalidInputException.class)
    public void extraOpenParens(){
        String input = "((x)";
        Lexer lexer = new Lexer(input);
        Parser_v3 Parser_v3 = new Parser_v3(lexer);
        Parser_v3.parse();
    }
    
    @Test(expected = InvalidInputException.class)
    public void extraCloseParens(){
        String input = "(x))";
        Lexer lexer = new Lexer(input);
        Parser_v3 Parser_v3 = new Parser_v3(lexer);
        Parser_v3.parse();
    }
    
    @Test(expected = InvalidInputException.class)
    public void unbalancedParens(){
        String input = "((x + y) * (a + b)) + y)) ";
        Lexer lexer = new Lexer(input);
        Parser_v3 Parser_v3 = new Parser_v3(lexer);
        Parser_v3.parse();
    }
    
    @Test(expected = InvalidInputException.class)
    public void unbalancedParens2(){
        String input = "(((((x + y) * (a + b)) + y)) ";
        Lexer lexer = new Lexer(input);
        Parser_v3 Parser_v3 = new Parser_v3(lexer);
        Parser_v3.parse();
    }
    
    @Test(expected = InvalidInputException.class)
    public void missingOuter(){
        String input = "(x + 5) * (y + 3) ";
        Lexer lexer = new Lexer(input);
        Parser_v3 Parser_v3 = new Parser_v3(lexer);
        Parser_v3.parse();
    }
    
}