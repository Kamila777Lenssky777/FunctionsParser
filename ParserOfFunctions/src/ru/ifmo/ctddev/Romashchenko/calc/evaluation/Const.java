/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ifmo.ctddev.Romashchenko.calc.evaluation;

/**
 *
 * @author Андрей
 */
public class Const implements IEvaluation {

    private Double constanta;
    private static final Integer PRIORITY = 0;

    public Const(Double constanta) {
        this.constanta = constanta;
    }

    @Override
    public Double evaluate(Double num) {
        return constanta;
    }

    public static Boolean is(String s) {
        try {
            Double i = Double.parseDouble(s);
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }

    @Override
    public IEvaluation derivative() {
        return new Const(0.0);
    }
}
