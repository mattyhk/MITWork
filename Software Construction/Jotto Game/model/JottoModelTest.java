package model;
import org.junit.Test;
import static org.junit.Assert.*;

// Testing for jottoModel

// Testing for makeGuess - compare the returned value with the expected value for different inputs:
// Valid input - expect guess [in common] [in position]
// A guess that has non alphabet characters or asterisk - expect error 2: Invalid guess. Length of guess != 5 or guess is not a dictionary word.
// A guess thats length > 5 - expect error 2: Invalid guess. Length of guess != 5 or guess is not a dictionary word.
// A guess that is not a word - expect error 2: Invalid guess. Length of guess != 5 or guess is not a dictionary word.
// A valid guess containing an asterisk - expect guess [in common] [in position]
// An guess longer than length 5 containing an asterisk - expect error 2: Invalid guess. Length of guess != 5 or guess is not a dictionary word.
// A guess containing more than 1 asterisk - expect guess [in common] [in position]

public class JottoModelTest{
    
    /**
     * Testing valid guess
     */
    @Test
    public void validGuess(){
        String output = JottoModel.makeGuess("crazy", 16952);
        String expected = "guess 3 1";
        assertTrue(output.equals(expected));
    }
    
    /**
     * Testing ill formated guess
     */
    @Test
    public void illFormatedGuess(){
        String output = JottoModel.makeGuess("cr/zy", 16952);
        String expected = "error 2: Invalid guess. Length of guess != 5 or guess is not a dictionary word.";
        assertTrue(output.equals(expected));
    }
    
    /**
     * Testing |guess| > 5
     */
    @Test
    public void tooLongGuess(){
        String output = JottoModel.makeGuess("elephant", 16952);
        String expected = "error 2: Invalid guess. Length of guess != 5 or guess is not a dictionary word.";
        assertTrue(output.equals(expected));
    }
    
    /**
     * Testing guess != word
     */
    @Test
    public void nonWordGuess(){
        String output = JottoModel.makeGuess("brzts", 16952);
        String expected = "error 2: Invalid guess. Length of guess != 5 or guess is not a dictionary word.";
        assertTrue(output.equals(expected));
    }
    
    /**
     * Testing valid guess with asterisk
     */
    @Test
    public void validWithAsteriskGuess(){
        String output = JottoModel.makeGuess("*bean", 16952);
        String expected = "guess 1 0";
        assertTrue(output.equals(expected));
    }
    
    /**
     * Testing guess with asterisk but too long
     */
    @Test
    public void invalidWithAsteriskGuess(){
        String output = JottoModel.makeGuess("fin*ls", 16952);
        String expected = "error 2: Invalid guess. Length of guess != 5 or guess is not a dictionary word.";
        assertTrue(output.equals(expected));
    }
    
    /**
     * Testing guess with asterisk but too long
     */
    @Test
    public void invalidWithMultipleAsteriskGuess(){
        String output = JottoModel.makeGuess("fi**s", 16952);
        System.out.println(output);
        String expected = "guess 0 0";
        assertTrue(output.equals(expected));
    }
}