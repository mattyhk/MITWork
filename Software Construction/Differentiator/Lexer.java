package differentiator;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A lexer takes a string and splits it into tokens that are meaningful to a
 * parser.
 */
public class Lexer {
    private final String input;
    private int index = 0;
    private final Matcher matcher;
    private List<Token> tokenList;
    private int numTokens = 0;
    private StringBuilder copyInput;

    private static final Pattern TOKEN_REGEX = Pattern.compile("([a-zA-z]+)" // Variable
            + "|" + "([0-9]+(\\.[0-9]+)?)" // Number
            + "|" + "(\\()" // openParens
            + "|" + "(\\))" // closeParens
            + "|" + "(\\+)" // adder
            + "|" + "(\\*)" // multiply
    );

    public Lexer(String string) {
        this.input = string;
        this.matcher = TOKEN_REGEX.matcher(input);
        this.tokenList = new ArrayList<Token>();
    }

    /**
     * Converts a string into a sequence of tokens. Adds a final END token to
     * the end of the sequence.
     * 
     * @param string
     *            The string to tokenize. Assume string is not empty.
     * @return tokens The list of tokens found in order plus an End token
     * @throws IllegalArgumentException
     *             if the string passed in contains unsupported characters or if
     *             no tokens are found
     * */

    public Token[] Tokenize() throws IllegalArgumentException {
        // following string builder will be used to check the presence of
        // unsupported characters
        // creates a new string that concatenates with the token that was just
        // found, and at the end is checked with the original string
        // if the strings do not match (after whitespace is removed) an
        // exception is thrown as there are unsupported characters in the input

        copyInput = new StringBuilder();
        String inputNoWhiteSpace;
        while (index < input.length()) {
            if (!(matcher.find(index))) {
                break;
            }
            String value = matcher.group();
            copyInput.append(value);
            tokenList.add(new Token(value));
            numTokens++;
            index = matcher.end();
        }

        // Check copyInput is equal to input stripped of all whitespace
        inputNoWhiteSpace = input.replace(" ", "");
        if (!(inputNoWhiteSpace.equals(copyInput.toString()))) {
            throw new IllegalArgumentException("Illegal character passed in");
        }
        // Check for only whitespace being passed in
        if (numTokens == 0) {
            throw new IllegalArgumentException("No valid tokens found");
        }
        // Add an END token to be used in the parser
        tokenList.add(new Token(""));
        Token[] tokenArray = new Token[tokenList.size()];

        // Convert to an array that will be passed into the parser
        for (int i = 0; i < tokenList.size(); i++) {
            tokenArray[i] = tokenList.get(i);
        }

        return tokenArray;
    }
}
