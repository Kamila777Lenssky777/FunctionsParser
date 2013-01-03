/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ifmo.ctddev.Romashchenko.calc;

import static junit.framework.Assert.*;
import org.junit.Test;
import ru.ifmo.ctddev.Romashchenko.calc.exeptions.EvaluationException;
import ru.ifmo.ctddev.Romashchenko.calc.exeptions.ParserException;
import ru.ifmo.ctddev.Romashchenko.calc.parseroffunction.ParserOfFunctions;
import ru.ifmo.ctddev.Romashchenko.calc.reversePolishRecord.RPR;

/**
 *
 * @author Андрей
 */
public class CalcTest {

    @Test
    public void testOne() throws ParserException, EvaluationException {
        String line = "1/x";
        String message = "message";
        try {
            double res = new RPR(ParserOfFunctions.getTokens(line)).getFormula().evaluate(0.0);

        } catch (Exception ex) {
            message = ex.getMessage();
        }
        assertEquals("division by zero", message);
    }

    @Test
    public void testTwo() throws ParserException, EvaluationException {
        String line = "x*x*x*x*x/(x-2)";
        String message = "message";
        try {
            double res = new RPR(ParserOfFunctions.getTokens(line)).getFormula().evaluate(1.0);
            message = res + "";
        } catch (Exception ex) {
            message = ex.getMessage();
        }
        assertEquals("-1.0", message);
    }

    @Test
    public void testThree() throws ParserException, EvaluationException {
        double pi = 3.14159265;
        String line = "cos(x*3.14159265)";
        String message = "message";
        try {
            double res = new RPR(ParserOfFunctions.getTokens(line)).getFormula().evaluate(1.0);
            message = res + "";
        } catch (Exception ex) {
            message = ex.getMessage();
        }
        assertEquals("-1.0", message);
    }

    @Test
    public void testFour() throws ParserException, EvaluationException {
        double pi = 3.14159265;
        String line = "dif(cos(x*3.14159265))";
        String message = "message";
        try {
            double res = new RPR(ParserOfFunctions.getTokens(line)).getFormula().evaluate(10.0);
            message = res + "";
        } catch (Exception ex) {
            message = ex.getMessage();
        }
        assertEquals("1.1277667676601117E-7", message);
    }

    @Test
    public void testFive() throws ParserException, EvaluationException {
        String line = "dif(x*x*x*x*x)";
        String message = "message";
        try {
            double res = new RPR(ParserOfFunctions.getTokens(line)).getFormula().evaluate(10.0);
            message = res + "";
        } catch (Exception ex) {
            message = ex.getMessage();
        }
        assertEquals("50000.0", message);
    }

    @Test
    public void testSix() throws ParserException, EvaluationException {
        String line = "x*x*x";
        String message = "message";
        try {
            double res = new RPR(ParserOfFunctions.getTokens(line)).getFormula().evaluate(3.0);
            message = res + "";
        } catch (Exception ex) {
            message = ex.getMessage();
        }
        assertEquals("27.0", message);
    }
}
