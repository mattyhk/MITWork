// My grammar will include: (, ), sequences of letters x y abc etc., ints 0 1 2 3 etc., floats 1.0 1.5 2.0 etc., +, *
// Token.types are: ClOSE_PARENS = (, OPEN_PARENS = ), VARIABLE = all sequences of letters sx, y, abc,
// NUMBERS = all ints and floats, ADDITION = +, MULTIPLICATION = *, END
package differentiator;

import java.util.regex.Pattern;

/**
 * A token is a lexical item that the parser uses.
 */
public class Token {
    /**
     * All the types of tokens that can be made.
     */
    public static enum Type {
        CLOSE_PARENS, OPEN_PARENS, VARIABLE, NUMBER, ADDITION, MULTIPLICATION, END
    }
    private Type type;
    private final String value;
    private final String[] regEx = {"([a-zA-z]+)",          // Variable
                                    "[0-9]+(\\.[0-9]+)?",   // Number
                                    "\\(",                  // openParens
                                    "\\)",                  // closeParens
                                    "\\+",                  // adder
                                    "\\*",                  // multiply
                                    ""};                    // end
    private final Type[] tokens = {Type.VARIABLE, Type.NUMBER, Type.OPEN_PARENS, Type.CLOSE_PARENS, Type.ADDITION, Type.MULTIPLICATION, Type.END};
   
    public Token(String string){
        this.value = string;
        // Checking to see which type of token it is
        for (int i = 0; i < regEx.length; i++){
            if (Pattern.matches(regEx[i], value)){
                this.type = tokens[i];
            }
        }
    }
    
    public Type getType() {
        return type;
    }
    
    public String getValue() {
        return value;
    }

}
    
