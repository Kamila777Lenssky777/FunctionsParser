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

    @Test(expected = EvaluationException.class)
    public void testDivisionByZero() throws Exception {
        String line = "1/x";
        new RPR(ParserOfFunctions.getTokens(line)).getFormula().evaluate(0.0);
    }

    @Test
    public void testTwo() throws ParserException, EvaluationException {
        String line = "x*x*x*x*x/(x-2)";
        double expected = -1.0;
        double actual = new RPR(ParserOfFunctions.getTokens(line)).getFormula().evaluate(1.0);
        assertTrue("Values are different (expected: " + expected + ", actual: " + actual + ")", Math.abs(expected - actual) < 0.000001);
    }

    @Test
    public void testThree() throws ParserException, EvaluationException {
        double pi = 3.14159265;
        String line = "cos(x*3.14159265)";
        double expected = -1.0;
        double actual = new RPR(ParserOfFunctions.getTokens(line)).getFormula().evaluate(1.0);
        assertTrue("Values are different (expected: " + expected + ", actual: " + actual + ")", Math.abs(expected - actual) < 0.000001);
    }

    @Test
    public void testFour() throws ParserException, EvaluationException {
        double pi = 3.14159265;
        String line = "dif(cos(x*3.14159265))";
        double expected = 0.0;
        double actual = new RPR(ParserOfFunctions.getTokens(line)).getFormula().evaluate(10.0);
        assertTrue("Values are different (expected: " + expected + ", actual: " + actual + ")", Math.abs(expected - actual) < 0.000001);
    }

    @Test
    public void testFive() throws ParserException, EvaluationException {
        String line = "dif(x*x*x*x*x)";
        double expected = 50000.0;
        double actual = new RPR(ParserOfFunctions.getTokens(line)).getFormula().evaluate(10.0);
        assertTrue("Values are different (expected: " + expected + ", actual: " + actual + ")", Math.abs(expected - actual) < 0.000001);
    }

    @Test
    public void testSix() throws ParserException, EvaluationException {
        String line = "2*3";
        double expected = 6.0;
        double actual = new RPR(ParserOfFunctions.getTokens(line)).getFormula().evaluate(10.0);
        assertTrue("Values are different (expected: " + expected + ", actual: " + actual + ")", Math.abs(expected - actual) < 0.000001);
    }
}
