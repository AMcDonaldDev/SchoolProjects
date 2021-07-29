/*
 * File: Lexer.java
 * Author: Allison McDonald
 * Date: 6/16/2020
 * Purpose: CMSC 330 Project 1 Lexer class
 */

import java.io.*;

public class Lexer {
    private StreamTokenizer st;
    
    public Lexer(String fileName) throws FileNotFoundException {
        st = new StreamTokenizer(new FileReader(fileName));
        st.quoteChar('"');
        st.ordinaryChar('.');
    }
    
    public void printLexer() throws IOException {
        int token = st.nextToken();
        while(token != StreamTokenizer.TT_EOF) {
            switch (token) {
                case StreamTokenizer.TT_WORD:
                    System.out.println(st.sval);
                    System.out.println();
                    break;
                case StreamTokenizer.TT_NUMBER:
                    System.out.println(st.nval);
                    System.out.println();
                    break;
                case '"':
                    System.out.println(st.sval);
                    System.out.println();
                    break;
                default:
                    System.out.println("unknown");
                    System.out.println();
                    break;
            }
            token = st.nextToken();
        }
    }
    
    public Token getNextToken() throws IOException {
        int token = st.nextToken();
        while(token != StreamTokenizer.TT_EOF) {
            switch(token) {
                case '"':
                    return Token.STRING;
                case '(':
                    return Token.LEFT_PARENTHESES;
                case ',':
                    return Token.COMMA;
                case ')':
                    return Token.RIGHT_PARENTHESES;
                case ':':
                    return Token.COLON;
                case '.':
                    return Token.PERIOD;
                case ';':
                    return Token.SEMICOLON;
                case StreamTokenizer.TT_NUMBER:
                    return Token.NUMBER;
                case StreamTokenizer.TT_WORD:
                    for(Token t : Token.values()) {
                        if(t.name().equals(st.sval.toUpperCase())) {
                            return t;
                        }
                    }
                case StreamTokenizer.TT_EOF:
                    return Token.EOF;
                default:
                    return Token.PERIOD;
            }
        }
        return Token.EOF;
    }
    
    public String getString() {
        return st.sval;
    }
    
    public double getNumber() {
        return st.nval;
    }
    
}
