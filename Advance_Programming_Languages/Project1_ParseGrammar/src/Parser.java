/*
 * File: Parser.java
 * Author: Allison McDonald
 * Date: 6/16/2020
 * Purpose: CMSC 330 Project 1 Parser class
 */

import java.awt.*;
import java.io.*;
import javax.swing.*;

public class Parser {
    
    private Token token;
    private final Output frame;
    private JPanel panel;
    private ButtonGroup radioButtonGroup;
    private final Lexer lexer;
    
    public Parser(Lexer lexer, Output frame) {
        this.lexer = lexer;
        this.frame = frame;
    }
    
    public boolean parseGUI() throws IOException {
        token = lexer.getNextToken();
        if(token == Token.WINDOW) {
            panel = new JPanel();
            frame.setVisible(true);
            token = lexer.getNextToken();
            if(token == Token.STRING) {
                String title = lexer.getString();
                frame.setTitle(title);
                token = lexer.getNextToken();
                if(token == Token.LEFT_PARENTHESES) {
                    token = lexer.getNextToken();
                    if(token == Token.NUMBER) {
                        double width = lexer.getNumber();
                        token = lexer.getNextToken();
                        if(token == Token.COMMA) {
                            token = lexer.getNextToken();
                            if(token == Token.NUMBER) {
                                double height = lexer.getNumber();
                                frame.setSize((int) width, (int) height);
                                frame.add(panel);
                                token = lexer.getNextToken();
                                if(token == Token.RIGHT_PARENTHESES) {
                                    token = lexer.getNextToken();
                                    if(parseLayout(token, panel)) {
                                        token = lexer.getNextToken();
                                        if(parseWidgets(token, panel)) {
                                            if(token == Token.END) {
                                                token = lexer.getNextToken();
                                                if(token == Token.PERIOD) {
                                                    return false;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    
    private boolean parseLayout(Token token, JPanel panel) throws IOException {
        if(token == Token.LAYOUT) {
            token = lexer.getNextToken();
            if(parseLayoutType(token, panel)) {
                token = lexer.getNextToken();
                if(token == Token.COLON) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean parseLayoutType(Token token, JPanel panel) throws IOException {
        if(token == Token.FLOW) {
            panel.setLayout(new FlowLayout());
            return true;
        } else if(token == Token.GRID) {
            double rows = 0;
            double cols = 0;
            double hgap = 0;
            double vgap = 0;
            token = lexer.getNextToken();
            if(token == Token.LEFT_PARENTHESES) {
                token = lexer.getNextToken();
                if(token == Token.NUMBER) {
                    rows = lexer.getNumber();
                    token = lexer.getNextToken();
                    if(token == Token.COMMA) {
                        token = lexer.getNextToken();
                        if(token == Token.NUMBER) {
                            cols = lexer.getNumber();
                            token = lexer.getNextToken();
                            if(token == Token.COMMA) {
                                token = lexer.getNextToken();
                                if(token == Token.NUMBER) {
                                    hgap = lexer.getNumber();
                                    token = lexer.getNextToken();
                                    if(token == Token.COMMA) {
                                        token = lexer.getNextToken();
                                        if(token == Token.NUMBER) {
                                            vgap = lexer.getNumber();
                                            panel.setLayout(new GridLayout((int) rows, (int) cols, (int) hgap, (int) vgap));
                                            token = lexer.getNextToken();
                                            if(token == Token.RIGHT_PARENTHESES) {
                                                return true;
                                            }
                                        }
                                    }
                                }
                            } else if(token == Token.RIGHT_PARENTHESES) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    
    private boolean parseWidgets(Token token, JPanel panel) throws IOException {
        if(parseWidget(token, panel)) {
            token = lexer.getNextToken();
            if(parseWidgets(token, panel)) {
                return true;
            }
            return true;
        }
        return false;
    }
    
    private boolean parseWidget(Token token, JPanel panel) throws IOException {
        if(token == Token.BUTTON) {
            token = lexer.getNextToken();
            if(token == Token.STRING) {
                String title = lexer.getString();
                panel.add(new JButton(title));
                token = lexer.getNextToken();
                if(token == Token.SEMICOLON) {
                    return true;
                }
            }
        } else if(token == Token.GROUP) {
            radioButtonGroup = new ButtonGroup();
            token = lexer.getNextToken();
            if(parseRadioButtons(token, panel)) {
                if(token == Token.END) {
                    token = lexer.getNextToken();
                    if(token == Token.SEMICOLON) {
                        return true;
                    }
                }
            }
            
        } else if(token == Token.LABEL) {
            token = lexer.getNextToken();
            if(token == Token.STRING) {
                String title = lexer.getString();
                panel.add(new JLabel(title));
                token = lexer.getNextToken();
                if(token == Token.SEMICOLON) {
                    return true;
                }
            }
            
        } else if(token == Token.PANEL) {
            panel.add(panel = new JPanel());
            token = lexer.getNextToken();
            if(parseLayout(token, panel)) {
                token = lexer.getNextToken();
                if(parseWidgets(token, panel)) {
                    token = lexer.getNextToken();
                    if(token == Token.END) {
                        token = lexer.getNextToken();
                        if(token == Token.SEMICOLON) {
                            return true;
                        }
                    }
                }
            }
        } else if(token == Token.TEXTFIELD) {
            token = lexer.getNextToken();
            if(token == Token.NUMBER) {
                double cols = lexer.getNumber();
                panel.add(new JTextField((int) cols));
                token = lexer.getNextToken();
                if(token == Token.SEMICOLON) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean parseRadioButtons(Token token, JPanel panel) throws IOException {
        if(parseRadioButton(token, panel)) {
            token = lexer.getNextToken();
            if(parseRadioButtons(token, panel)) {
                return true;
            }
            return true;
        }
        return false;
    }
    
    private boolean parseRadioButton(Token token, JPanel panel) throws IOException {
        if(token == Token.RADIO) {
            token = lexer.getNextToken();
            if(token == Token.STRING) {
                String title = lexer.getString();
                JRadioButton radioButton = new JRadioButton(title);
                radioButtonGroup.add(radioButton);
                panel.add(radioButton);
                token = lexer.getNextToken();
                if(token == Token.SEMICOLON) {
                    return true;
                }
            }
        }
        return false;
    }
    
}
