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
public class Cos implements IEvaluation {

    private IEvaluation operand;

    public Cos(IEvaluation operand) {
        this.operand = operand;
    }

    @Override
    public Double evaluate(Double num) throws EvaluationException {
        return Math.cos(operand.evaluate(num));
    }

    @Override
    public IEvaluation derivative() {
        return new Times(new Minus(new Const(0.0), new Sin(operand)), operand.derivative());
    }

    public static Boolean is(String s) {
        return s.contains("cos");
    }
}
