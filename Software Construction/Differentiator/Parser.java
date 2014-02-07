// Variable ::= [a-zA-Z]+
// Number ::= [0-9]+([.][0-9]+)?
// Multiplication ::= *
// Addition ::= +
// OpenParen ::= (
// CloseParen ::= )
// Operator ::= Multiplication | Addition
// Expression ::= Variable | Number | Expression Operator Expression | OpenParen Expression CloseParen

package differentiator;

/**
 * The parser gets a bunch of tokens from the lexer and determines what
 * expression was written by the user.
 */
public class Parser {
    private final Token[] arrayOfTokens;
    private int index = -1;
    private Expression left;
    private Expression exp;
    private int balance = 0;
    /**
     * Creates a new parser over the lexer.  This parser will use the passed
     * lexer to get tokens--which it will then parse.
     * @param lexer The lexer.
     */
    public Parser(Lexer lexer) {
        this.arrayOfTokens = lexer.Tokenize();
        }
    /**
     * Increments the current index and fetches the next token to be parsed
     * @return token The next token to be parsed
     */
    public Token getNext(){
        index++;
        return arrayOfTokens[index];
    }
    
    /**
     * Allows for the next token in the list to be seen without incrementing the index
     * @return token The next token in the list
     */
    public Token seeNext(){
        return arrayOfTokens[index + 1];
    }
    
    /**
     * Converts a token into a valid expression. For example a number token becomes a number expression, a multiply token becomes a multiply expression.
     * 
     * Valid expression are:
     * ((( x ) + ( y ))), ( x * y), ((x*y) + (a*b))
     * @param token The token to be evaluated. This token is not mutated.
     * @return expression A valid expression corresponding to the evaluated token.
     * @throws InvalidInputException if an invalid token sequence is found, 
     * which corresponds to a string having passed in that does not form a valid expression or has no tokens
     */
    public Expression parse() {
        Token token = getNext();
        Token.Type tokenValue = token.getType();
        Token.Type nextTokenValue = seeNext().getType();
        
        // if the token is a NUMBER, return a number expression if its not the first element
        if (tokenValue.equals(Token.Type.NUMBER)){
            Expression num = new Number(token);
            if (index != 0){
                return num;
            }
            else throw new InvalidInputException("First element is a number");
        }
        
        // if the token is a VARIABLE, return a variable expression if its not the first element
        else if (tokenValue.equals(Token.Type.VARIABLE)){
            Expression var = new Variable(token);
            if (index != 0){
                return var;
            }
            else throw new InvalidInputException("First element is a variable");
        }
        
        // if the token is a MULTIPLICATION, return a multiply expression if its not the first element
        else if (tokenValue.equals(Token.Type.MULTIPLICATION)){
            // Make sure the next token is the start of an expression, i.e. a number, a variable, or an open parens.
            // If it is, parse the next token and return it as the right side of the expression
            // Else throw an exception
            if (nextTokenValue.equals(Token.Type.NUMBER) || nextTokenValue.equals(Token.Type.VARIABLE) || nextTokenValue.equals(Token.Type.OPEN_PARENS)){
                if (index != 0){
                    return new Multiplication(left, parse());
                }
                else throw new InvalidInputException("First element is a multiplication");
            }
            else throw new InvalidInputException("A non expression follows a multiplication");
        }
        
     // if the token is an ADDITION, return an ADDITION expression
        else if (tokenValue.equals(Token.Type.ADDITION)){
            // Make sure the next token is the start of an expression, i.e. a number, a variable, or an open parens.
            // If it is, parse the next token and return it as the right side of the expression
            // Else throw an exception
            if (nextTokenValue.equals(Token.Type.NUMBER) || nextTokenValue.equals(Token.Type.VARIABLE) || nextTokenValue.equals(Token.Type.OPEN_PARENS)){
                if (index != 0){
                    return new Addition(left, parse());
                }
                else throw new InvalidInputException("First element is an addition");
            }
            else throw new InvalidInputException("A non expression follows an addition");
        }
        
        //An open parens marks the beginning of an expression
        else if (tokenValue.equals(Token.Type.OPEN_PARENS)){
            
            // Balance is used to check the balance of the expression, i.e. equal numbers of open and closed parens
            balance++;
            
            // Check to see if the next token is a variable, a number or an open parens. If not, it is an invalid sequence
            if (!(nextTokenValue.equals(Token.Type.NUMBER) || nextTokenValue.equals(Token.Type.VARIABLE) || nextTokenValue.equals(Token.Type.OPEN_PARENS))){
                throw new InvalidInputException("An invalid sequence follows an Open Parens");
            }
            
            // Else if the next token is a variable or number, check if the following token is an operator or a close parens 
            // If it is an operator, the variable or number becomes the left variable of an operator expression 
            // The operator expression is returned provided there is a matching close parens
            // Else if the token following the initial variable or number is a closed paren, the variable or number is returned as an expression.
            else if (nextTokenValue.equals(Token.Type.NUMBER) || nextTokenValue.equals(Token.Type.VARIABLE)){
                left = parse();
                Token.Type afterLeft = seeNext().getType();
                if (afterLeft.equals(Token.Type.MULTIPLICATION) || afterLeft.equals(Token.Type.ADDITION)){
                    exp = parse();
                    Token.Type afterExp = seeNext().getType();
                    
                    if (!(afterExp.equals(Token.Type.CLOSE_PARENS) || afterExp.equals(Token.Type.END))) throw new InvalidInputException("Missing close parens or too many");
                    
                    // Have not reached end, continue to parse
                    else if (afterExp.equals(Token.Type.CLOSE_PARENS)){
                        return parse();
                        }
                    // Have reached end
                    else{
                        if (balance == 0){
                            return exp;
                        }
                        else {throw new InvalidInputException("Unbalanced parentheses");}
                    }
                }
                else if (afterLeft.equals(Token.Type.CLOSE_PARENS)){
                    exp = left;
                    return parse();
                } else throw new InvalidInputException("An invalid sequence follows a variable or number");
            }
            
            // Else if the next token is another open parens, that becomes the left variable of a potential expression and check there is a matching close parens.
            else if (nextTokenValue.equals(Token.Type.OPEN_PARENS)){
                left = parse();
                Token.Type afterLeft = seeNext().getType();
                if (!(afterLeft.equals(Token.Type.CLOSE_PARENS) || (afterLeft.equals(Token.Type.END)))) throw new InvalidInputException("Missing close parens or too many");
                // Have not reached end, continue to parse
                else if(afterLeft.equals(Token.Type.CLOSE_PARENS)){
                    return parse();
                }
                // Have reached end
                else{
                    if (balance == 0){
                        return exp;
                    }
                    else {throw new InvalidInputException("Unbalanced parentheses");}
                }
            }
        }
        
        else if (tokenValue.equals(Token.Type.CLOSE_PARENS)){
            balance--;
            if (index == 0) throw new InvalidInputException("Close parens is first element");
            else{
                if (!(nextTokenValue.equals(Token.Type.MULTIPLICATION) || nextTokenValue.equals(Token.Type.ADDITION)
                        || nextTokenValue.equals(Token.Type.CLOSE_PARENS) || nextTokenValue.equals(Token.Type.END))) 
                        throw new InvalidInputException("illegal sequence following close parens");
                
                // End of the entire expression
                else if (nextTokenValue.equals(Token.Type.END)){
                    if (balance == 0){
                        return exp;
                    } else {throw new InvalidInputException("Unbalanced parentheses");}
                }
                // Beginning of a new expression, therefore whatever was in enclosed by this parens is the left side of an op expression
                else if (nextTokenValue.equals(Token.Type.MULTIPLICATION) || nextTokenValue.equals(Token.Type.ADDITION)){                  
                    left = exp;
                    exp = parse();
                    Token.Type afterExp = seeNext().getType();
                    if (!(afterExp.equals(Token.Type.CLOSE_PARENS) || afterExp.equals(Token.Type.END))) throw new InvalidInputException("Missing close parens or too many");
                    // Have not reached end, continue to parse
                    if (afterExp.equals(Token.Type.CLOSE_PARENS)){
                        return parse();
                        }
                    // Have reached end
                    else{
                        if (balance == 0){
                            return exp;
                        }
                        else {throw new InvalidInputException("Unbalanced parentheses");}
                    }
                }
                // It is another close parens - recurse
                else{
                    return parse();
                }
            }
        }
        
        else if (tokenValue.equals(Token.Type.END)){
            if (index == 0) throw new InvalidInputException("String had no tokens");
            else{
                // At the end
                return exp;
                }
        }
        // Should not get to this case
        throw new RuntimeException("Should not be here");
        
    }
    
    // NOTE: Remember to build a recursively-defined AST.
    
    // Create a class implementing an Expression for each expression expected
    public static interface Expression{
        public String toString();
        public interface Visitor<R>{
            public R differentiate(Variable var);
            public R differentiate(Number num);
            public R differentiate(Addition add);
            public R differentiate(Multiplication mul);
        }
        public <R> R accept(Visitor<R> v);
    }
    // Variable
    public static class Variable implements Expression{
        public final Token token;
        public Variable(Token token) {this.token = token;}
        
        @Override
        public String toString(){
            String value;
            value = this.token.getValue();
            return value;
        }
        
        public <R> R accept(Visitor<R> v) {return v.differentiate(this);}
    }
    // Number
    public static class Number implements Expression{
        public final Token token;
        public Number(Token token) {this.token = token;}
        
        @Override
        public String toString(){
            String value;
            value = this.token.getValue();
            return value;
        }
        
        public <R> R accept(Visitor<R> v) {return v.differentiate(this);}
    }
    // Addition
    public static class Addition implements Expression{
        public final Expression left;
        public final Expression right;
        public Addition(Expression left, Expression right) {this.left = left; this.right = right;}
        
        @Override
        public String toString(){
            return "(" + left.toString() + " " + "+" + " " + right.toString() + ")";
        }
        
        public <R> R accept(Visitor<R> v) {return v.differentiate(this);}
    }
    // Multiplication
    public static class Multiplication implements Expression{
        public final Expression left;
        public final Expression right;
        public Multiplication(Expression left, Expression right) {this.left = left; this.right = right;}
        
        @Override
        public String toString(){
            return "(" + left.toString() + " " + "*" + " " + right.toString() + ")";
        }
        
        public <R> R accept(Visitor<R> v) {return v.differentiate(this);}
    }
}
