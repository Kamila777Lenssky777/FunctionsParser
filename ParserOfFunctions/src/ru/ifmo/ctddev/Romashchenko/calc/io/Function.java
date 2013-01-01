/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ifmo.ctddev.Romashchenko.calc.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import ru.ifmo.ctddev.Romashchenko.calc.evaluation.IEvaluation;
import ru.ifmo.ctddev.Romashchenko.calc.exeptions.*;
import ru.ifmo.ctddev.Romashchenko.calc.parseroffunction.ParserOfFunctions;
import ru.ifmo.ctddev.Romashchenko.calc.reversePolishRecord.RPR;

/**
 *
 * @author Андрей
 */
public class Function {

    private BufferedReader reader;
    private PrintWriter writer;
    private IEvaluation function;
    private String strfunction;

    public Function(File inputfile) throws IOException {
        try {
            reader = new BufferedReader(new FileReader(inputfile));
            strfunction = reader.readLine();
            if (strfunction != null) {
                try {
                    function = new RPR(ParserOfFunctions.getTokens(strfunction)).getFormula();
                } catch (EvaluationException | ParserException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    public void write(File putputfile) {

        try {
            writer = new PrintWriter(putputfile);
            if (function == null) {
                writer.write("Function hasn't been read");
                return;
            }
            writer.write("Tabulation of function " + strfunction + "\n");
            for (int i = 0; i < 10; i++) {
                try {
                    double res = function.evaluate((double) i);
                    writer.write(i + " \t" + res + "\n");
                } catch (EvaluationException ex) {
                    writer.write(i + " \t" + ex.getMessage() + "\n");
                }

            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
}
