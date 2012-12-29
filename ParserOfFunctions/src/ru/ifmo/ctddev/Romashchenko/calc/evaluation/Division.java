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
public class Division implements IEvaluation {

    private IEvaluation operand1;
    private IEvaluation operand2;
    public static final Integer PRIORITY = 2;

    public Division(IEvaluation e1, IEvaluation e2) {
        this.operand1 = e1;
        this.operand2 = e2;

    }

    @Override
    public Double evaluate(Double num) throws EvaluationException {
        Double res = null;
        Double op2 = null;
        op2 = operand2.evaluate(num);
        if (op2 - 0 < 0.0001) {
            throw new EvaluationException("division by zero");
        } else {
            res = operand1.evaluate(num) / op2;
        }
        return res;
    }

    public static Boolean is(String s) {
        return s.equals("/");
    }

    @Override
    public IEvaluation derivative() {
        IEvaluation a1 = new Times(operand1.derivative(), operand2);
        IEvaluation a2 = new Times(operand1, operand2.derivative());
        return new Division(new Minus(a1, a2), new Times(operand2, operand2));
    }
}
