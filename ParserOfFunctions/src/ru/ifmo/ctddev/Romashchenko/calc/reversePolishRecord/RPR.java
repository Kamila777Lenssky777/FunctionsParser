package ru.ifmo.ctddev.Romashchenko.calc.reversePolishRecord;

import ru.ifmo.ctddev.Romashchenko.calc.exeptions.*;
import ru.ifmo.ctddev.Romashchenko.calc.evaluation.*;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author Кристина
 */
//reversed polish record -- algorithm for parsing function
public class RPR {

    //private Stack<String> outputline = new Stack<String>();   //stack for reversed record
    private IEvaluation formula;

    public enum Priority {

        LIT, ADD, MUL, LPAR, RPAR, FUN, DIF;
    }

    public RPR(List<String> list) throws ParserException, EvaluationException {
        Stack<String> outputline = fillOutputLine(list);
        formula = calculation(outputline);

    }

    public IEvaluation getFormula() {
        return formula;
    }

    //fills outputline
    public Stack<String> fillOutputLine(List<String> list) throws ParserException {
        Stack<String> outputline = new Stack<String>();
        Stack<String> stack = new Stack<String>();
        try {
            for (String elementOfList : list) {
                Priority priority = getPriority(elementOfList);
                if (priority == Priority.LIT) {
                    outputline.push(elementOfList);
                } else if (priority == Priority.LPAR) {
                    stack.push(elementOfList);
                } else if (priority == Priority.RPAR) {
                    while (!stack.isEmpty() && getPriority(stack.peek()) != Priority.LPAR) {
                        outputline.push(stack.pop());
                    }
                    stack.pop();
                } else {
                    while (!stack.isEmpty() && priority.compareTo(getPriority(stack.peek())) <= 0 && !getPriority(stack.peek()).equals(Priority.LPAR)) {
                        outputline.push(stack.pop());
                    }
                    stack.push(elementOfList);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ParserException("mistake in spliting into tokens");
        }

        while (!stack.isEmpty()) {
            outputline.push(stack.pop());
        }
        System.out.println("OutputLine " + outputline);
        return outputline;
    }

    //defines priority of the token
    private Priority getPriority(String ex) {
        if (ex.equals("*") || ex.equals("/")) {
            return Priority.MUL;
        } else if (ex.equals("-") || ex.equals("+")) {
            return Priority.ADD;
        } else if (ex.equals("(")) {
            return Priority.LPAR;
        } else if (ex.equals(")")) {
            return Priority.RPAR;
        } else if (ex.equals("cos") || ex.equals("sin")) {
            return Priority.FUN;
        } else if (ex.equals("dif")) {
            return Priority.DIF;
        } else {
            return Priority.LIT;
        }
    }

    //calculates the function in the point num with filled before outputline
    public IEvaluation calculation(Stack<String> outputline) throws EvaluationException, ParserException {
        Stack<IEvaluation> stack = new Stack<IEvaluation>();
        IEvaluation formula = null;
        for (String s : outputline) {
            IEvaluation r = null;
            if (Const.is(s)) {
                // r = Integer.parseInt(s);
                r = new Const(Double.parseDouble(s));
            } else if (Variable.is(s)) {
                //r = new Variable(s).evaluate(num);
                r = new Variable(s);
            } else if (Sin.is(s)) {
                r = new Sin(stack.pop());
            } else if (Cos.is(s)) {
                r = new Cos(stack.pop());
            } else if (s.contains("dif")) {
                r = stack.pop().derivative();
            } else {
                IEvaluation op1 = null;
                IEvaluation op2 = null;
                try {
                    op1 = stack.pop();
                    op2 = stack.pop();
                } catch (EmptyStackException ex) {
                    throw new ParserException("binary operators have been used incorrectly");
                }

                if (Minus.is(s)) {
                    //r = -new Minus(op1, op2).evaluate(num);
                    r = new Minus(op2, op1);
                } else if (Plus.is(s)) {
                    //r = new Plus(op1, op2).evaluate(num);
                    r = new Plus(op2, op1);
                } else if (Times.is(s)) {
                    r = new Times(op2, op1);
                } else if (Division.is(s)) {
                    r = new Division(op2, op1);
                } else {
                    throw new ParserException("unrecognized token");
                }
            }
            stack.push(r);
        }

        if (stack.isEmpty()
                || stack.size() > 1) {
            throw new ParserException("function has been entered incorrect!");
        }
        return stack.pop();
    }
}