package differentiator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import differentiator.Parser.Expression;


/** Symbolic differentiator */
public class Differentiator {
    /**
     * Differentiates the passed expression string with respect to variable
     * and returns its derivative as a string.  If the expression or variable
     * is null, behavior is undefined.  If the expression is not parsable,
     * throws an exception.
     * @param expression The expression.
     * @param variable The variable to differentiate by.
     * @return The expression's derivative.
     */
    public String evaluate(String expression, String variable) {
        Lexer lex = new Lexer(expression);
        Parser parser = new Parser(lex);
        Expression exp;
        exp = parser.parse();
        String output = differentiate(exp, variable);
        return output;
        
    }
    
    /**
     * Differentiates a given expression and returns its derivative as a string
     * @param expression The expression.
     * @param variable The variable to differentiate by.
     * @return The expression's derivative
     */
    public static String differentiate(Parser.Expression exp, final String variable){
        return exp.accept(new Parser.Expression.Visitor<String>() {
            // This is the d(var)/dx case
            public String differentiate(Parser.Variable var){
                String expVar = var.toString();
                String output;
                if (expVar.equals(variable)){
                    output = "1";
                    return output;
                }
                else{
                    output = "0";
                    return output;
                }
            }
            // dc/dx case
            public String differentiate(Parser.Number num){
                String output = "0";
                return output;
            }
            // d(u+v)/dx case
            public String differentiate(Parser.Addition add){
                Expression leftExpr;
                Expression rightExpr;
                String outLeft;
                String outRight;
                leftExpr = add.left;
                rightExpr = add.right;
                outLeft = Differentiator.differentiate(leftExpr, variable);
                outRight = Differentiator.differentiate(rightExpr, variable);
                return "(" + outLeft + " " + "+" + " " + outRight+ ")";
            }
            // d(u*v)/dx case
            public String differentiate(Parser.Multiplication mul){
                Expression leftExpr;
                Expression rightExpr;
                String outLeft;
                String outRight;
                leftExpr = mul.left;
                rightExpr = mul.right;
                String rightDiff = Differentiator.differentiate(rightExpr, variable);
                String leftDiff = Differentiator.differentiate(leftExpr, variable);
                outLeft = "(" + leftExpr.toString() + " " + "*" + " " + rightDiff + ")";
                outRight = "(" + rightExpr.toString() + " " + "*" + " " + leftDiff + ")";
                return "(" + outLeft + " " + "+" + " " + outRight + ")";
            }
            
        });
    }

    /**
     * Repeatedly reads expressions from the console, and outputs the results of
     * evaluating them. Inputting an empty line will terminate the program.
     * @param args unused
     */
    public static void main(String[] args) throws IOException {
        String result;

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String expression;
        do {
            // display prompt
            System.out.print("> ");
            // read input
            expression = in.readLine();
            // terminate if input empty
            if (!expression.equals("")) {
                try {
                    Differentiator diff = new Differentiator();
                    result = diff.evaluate(expression, "x");
                    System.out.println(result);
                } catch (RuntimeException re) {
                    System.err.println("Error: " + re.getMessage());
                } 
            }
        } while (!expression.equals(""));
    }
}
