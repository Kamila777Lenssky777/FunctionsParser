/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ifmo.ctddev.Romashchenko.calc.evaluation;

import ru.ifmo.ctddev.Romashchenko.calc.exeptions.EvaluationException;

/**
 *
 * @author Андрей
 */
public class Minus implements IEvaluation {

    private IEvaluation operand1;
    private IEvaluation operand2;
    public static final Integer PRIORITY = 1;

    public Minus(IEvaluation e1, IEvaluation e2) {
        this.operand1 = e1;
        this.operand2 = e2;
    }

    @Override
    public Double evaluate(Double num) throws EvaluationException {
        return operand1.evaluate(num) - operand2.evaluate(num);
    }

    public static Boolean is(String s) {
        return s.equals("-");
    }

    @Override
    public IEvaluation derivative() {
        return new Minus(operand1.derivative(), operand2.derivative());
    }
}
