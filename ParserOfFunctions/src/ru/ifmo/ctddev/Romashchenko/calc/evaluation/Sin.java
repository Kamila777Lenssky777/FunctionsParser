package ru.ifmo.ctddev.Romashchenko.calc.evaluation;


import ru.ifmo.ctddev.Romashchenko.calc.evaluation.Cos;
import ru.ifmo.ctddev.Romashchenko.calc.evaluation.IEvaluation;
import ru.ifmo.ctddev.Romashchenko.calc.evaluation.Times;
import ru.ifmo.ctddev.Romashchenko.calc.exeptions.EvaluationException;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Андрей
 */
public class Sin implements IEvaluation {

    private IEvaluation operand;

    public Sin(IEvaluation operand) {
        this.operand = operand;
    }

    @Override
    public Double evaluate(Double num) throws EvaluationException {
        return Math.sin(operand.evaluate(num));
    }

    @Override
    public IEvaluation derivative() {
        return new Times(new Cos(operand), operand.derivative());

    }
    public static Boolean is(String s) {
        return s.contains("sin");
    }
}
