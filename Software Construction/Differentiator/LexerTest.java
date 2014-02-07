package differentiator;

import static org.junit.Assert.*;

import org.junit.Test;

public class LexerTest {
    
    // Partitioning the Input space into both valid and invalid inputs that will throw exceptions, starting with simple to more complex inputs.
    @Test
    public void testEquals(){
        // Simple test to check (x + 2) returns an array containing the tokens OPEN_PARENS, VARIABLE, CLOSE_PARENS in order
        String string = "(x + 2)";
        Token[] expectedOutput = {new Token("("), new Token("x"), new Token("+"), new Token("2"), new Token(")"), new Token("")};
        String[] expectedValues = new String[6];
        Token.Type[] expectedTypes = new Token.Type[6];
        Lexer lexer = new Lexer(string);
        Token[] output = lexer.Tokenize();
        String[] outputValues = new String[6];
        Token.Type[] outputTypes = new Token.Type[6];
        
        for (int i = 0; i < output.length; i++){
            expectedValues[i] = expectedOutput[i].getValue();
            expectedTypes[i] = expectedOutput[i].getType();
            outputValues[i] = output[i].getValue();
            outputTypes[i] = output[i].getType();
        }
        assertArrayEquals(expectedValues, outputValues);
        assertArrayEquals(expectedTypes, outputTypes);
    }
    
    @Test
    public void testOperators(){
        // Test to check operators are handled correctly
        String string = "+*";
        Token[] expectedOutput = {new Token("+"), new Token("*"), new Token("")};
        String[] expectedValues = new String[3];
        Token.Type[] expectedTypes = new Token.Type[3];
        Lexer lexer = new Lexer(string);
        Token[] output = lexer.Tokenize();
        String[] outputValues = new String[3];
        Token.Type[] outputTypes = new Token.Type[3];
        
        for (int i = 0; i < output.length; i++){
            expectedValues[i] = expectedOutput[i].getValue();
            expectedTypes[i] = expectedOutput[i].getType();
            outputValues[i] = output[i].getValue();
            outputTypes[i] = output[i].getType();
        }
        assertArrayEquals(expectedValues, outputValues);
        assertArrayEquals(expectedTypes, outputTypes);
    }
    
    @Test
    public void testNumbers(){
        // Tests if numbers are handled correctly, i.e. floats and ints are both of numbers
        String string = "2 3 1.0";
        Token[] expectedOutput = {new Token("2"), new Token("3"), new Token("1.0"), new Token("")};
        String[] expectedValues = new String[4];
        Token.Type[] expectedTypes = new Token.Type[4];
        Lexer lexer = new Lexer(string);
        Token[] output = lexer.Tokenize();
        String[] outputValues = new String[4];
        Token.Type[] outputTypes = new Token.Type[4];
        
        for (int i = 0; i < output.length; i++){
            expectedValues[i] = expectedOutput[i].getValue();
            expectedTypes[i] = expectedOutput[i].getType();
            outputValues[i] = output[i].getValue();
            outputTypes[i] = output[i].getType();
        }
        assertArrayEquals(expectedValues, outputValues);
        assertArrayEquals(expectedTypes, outputTypes);
    }
    
    @Test
    public void longVariables(){
        // Tests if variables longer than one char are handled correctly
        String string = "abc + xyz";
        Token[] expectedOutput = {new Token("abc"), new Token("+"), new Token("xyz"), new Token("")};
        String[] expectedValues = new String[4];
        Token.Type[] expectedTypes = new Token.Type[4];
        Lexer lexer = new Lexer(string);
        Token[] output = lexer.Tokenize();
        String[] outputValues = new String[4];
        Token.Type[] outputTypes = new Token.Type[4];
        
        for (int i = 0; i < output.length; i++){
            expectedValues[i] = expectedOutput[i].getValue();
            expectedTypes[i] = expectedOutput[i].getType();
            outputValues[i] = output[i].getValue();
            outputTypes[i] = output[i].getType();
        }
        assertArrayEquals(expectedValues, outputValues);
        assertArrayEquals(expectedTypes, outputTypes);
    }
    
    @Test
    public void removeWhiteSpace(){
        // Test to check lexer handles whitespace correctly, i.e. uses it as a limiter and ignores it
        String string = "(   x      *      y    )";
        Token[] expectedOutput = {new Token("("), new Token("x"), new Token("*"), new Token("y"), new Token(")"), new Token("")};
        String[] expectedValues = new String[6];
        Token.Type[] expectedTypes = new Token.Type[6];
        Lexer lexer = new Lexer(string);
        Token[] output = lexer.Tokenize();
        String[] outputValues = new String[6];
        Token.Type[] outputTypes = new Token.Type[6];
        
        for (int i = 0; i < output.length; i++){
            expectedValues[i] = expectedOutput[i].getValue();
            expectedTypes[i] = expectedOutput[i].getType();
            outputValues[i] = output[i].getValue();
            outputTypes[i] = output[i].getType();
        }
        assertArrayEquals(expectedValues, outputValues);
        assertArrayEquals(expectedTypes, outputTypes);
    }
    
    @Test
    public void bigTest(){
        String string = "(( abc + 2) * (x * 3.0))";
        Token[] expectedOutput = {new Token("("), new Token("("), new Token("abc"), new Token("+"), new Token("2"),
                new Token(")"), new Token("*"), new Token("("), new Token("x"), new Token("*"), new Token("3.0"), new Token(")"), 
                new Token(")"), new Token("")};
        String[] expectedValues = new String[14];
        Token.Type[] expectedTypes = new Token.Type[14];
        Lexer lexer = new Lexer(string);
        Token[] output = lexer.Tokenize();
        String[] outputValues = new String[14];
        Token.Type[] outputTypes = new Token.Type[14];
        
        for (int i = 0; i < output.length; i++){
            expectedValues[i] = expectedOutput[i].getValue();
            expectedTypes[i] = expectedOutput[i].getType();
            outputValues[i] = output[i].getValue();
            outputTypes[i] = output[i].getType();
        }
        assertArrayEquals(expectedValues, outputValues);
        assertArrayEquals(expectedTypes, outputTypes);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void unsupportedChar(){
        String string = "((abc + xyz!))";
        Lexer lexer = new Lexer(string);
        lexer.Tokenize();
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void noToken(){
        String string = "!%   @@";
        Lexer lexer = new Lexer(string);
        lexer.Tokenize();
    }
    
}

