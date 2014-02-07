package differentiator;

import differentiator.Parser_v2.Addition;
import differentiator.Parser_v2.Expression;
import differentiator.Parser_v2.Multiplication;
import differentiator.Parser_v2.Number;
import differentiator.Parser_v2.Variable;
import differentiator.Parser_v2.Expression.Visitor;

public class Parser_v3 {
    
    Lexer lex;
    private final Token[] arrayOfTokens;
    private int index = -1;
    
    public Parser_v3(Lexer lexer) {
        lex = lexer;
        this.arrayOfTokens = lexer.Tokenize();
    }
    
    public Token getNext(){
        index++;
        return arrayOfTokens[index];
    }
    
    public Token seeNext(){
        return arrayOfTokens[index + 1];
    }
    
    public Expression parse(){
        Token tok = getNext();
        if (tok.getType().equals(Token.Type.NUMBER)) { return new Number(tok);}
        if (tok.getType().equals(Token.Type.VARIABLE)) { return new Variable(tok);}
        if (tok.getType().equals(Token.Type.OPEN_PARENS)){
            Expression left = parse();
            tok = getNext();
            if (tok.getType().equals(Token.Type.ADDITION) || tok.getType().equals(Token.Type.MULTIPLICATION)){
                Token.Type op = tok.getType();
                Expression right = parse();
                tok = getNext();
                if (tok.getType().equals(Token.Type.CLOSE_PARENS)){
                    if (op.equals(Token.Type.MULTIPLICATION)){return new Multiplication(left, right);}
                    if (op.equals(Token.Type.ADDITION)){return new Addition(left, right);}
                    
                }
            }
            if (tok.getType().equals(Token.Type.CLOSE_PARENS)){return left;}
            else{throw new RuntimeException("invalid input");}
        }
        throw new RuntimeException("invalid input");
    }
    
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