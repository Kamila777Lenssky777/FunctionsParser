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
public class Plus implements IEvaluation {

    private IEvaluation operand1;
    private IEvaluation operand2;
    public static final Integer PRIORITY = 1;

    public Plus(IEvaluation e1, IEvaluation e2) {
        this.operand1 = e1;
        this.operand2 = e2;
    }

    @Override
    public Double evaluate(Double num) throws EvaluationException {
        Double op1 = operand1.evaluate(num);
        Double op2 = operand2.evaluate(num);
        Double k = op1 + op2;
        return k;
    }

    public static Boolean is(String s) {
        return s.equals("+");
    }
     @Override
    public IEvaluation derivative() {
        return new Plus(operand1.derivative(), operand2.derivative());
    }
}
