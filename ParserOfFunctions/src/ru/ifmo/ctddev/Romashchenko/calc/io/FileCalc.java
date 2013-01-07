/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ifmo.ctddev.Romashchenko.calc.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import ru.ifmo.ctddev.Romashchenko.calc.evaluation.IEvaluation;
import ru.ifmo.ctddev.Romashchenko.calc.exeptions.EvaluationException;
import ru.ifmo.ctddev.Romashchenko.calc.exeptions.ParserException;
import ru.ifmo.ctddev.Romashchenko.calc.parseroffunction.ParserOfFunctions;
import ru.ifmo.ctddev.Romashchenko.calc.reversePolishRecord.RPR;

/**
 *
 * @author Андрей
 */
public class FileCalc {

    public static void main(String args[]) throws IOException, EvaluationException {
        String inputfile = "inputfile.txt";
        String resultfile = "resultfile.txt";
        if (args.length == 2) {
            inputfile = args[0];
            resultfile = args[1];
        } else {
            System.out.println("There aren't paarametrs of command line\n");
        }
        System.out.println("Inputfile : " + inputfile + " and resultfile : " + resultfile + "\n");
        Writer writer = null;
        List<IEvaluation> functions = FileParser.parseFunctions(new FileReader(inputfile));
        try {
            writer = new FileWriter(resultfile);
            writer.append("x\t");
            for (int i = 0; i < functions.size(); i++) {
                writer.append(String.format("%20s", "f" + i + "\t"));

            }
            for (int i = 0; i <= 10; i++) {
                FileParser.writeValues(writer, functions, i);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    static class FileParser {

        public static List<IEvaluation> parseFunctions(Reader reader) throws EvaluationException, IOException {
            List<IEvaluation> resultlist = null;
            BufferedReader bufreader = null;
            try {
                bufreader = new BufferedReader(reader);
                resultlist = new ArrayList<IEvaluation>();
                String line = null;
                while ((line = bufreader.readLine()) != null) {
                    try {
                        resultlist.add(new RPR(ParserOfFunctions.getTokens(line)).getFormula());
                    } catch (ParserException ex) {
                        resultlist.add(null);
                        System.out.println("In input file one of functions (  " + line + " ) isn't correct");
                        System.out.println("It has been missed");
                    }
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            } finally {
                if (bufreader != null) {
                    bufreader.close();
                }
            }
            return resultlist;
        }

        public static void writeValues(Writer writer, List<IEvaluation> functions, double x) throws IOException {
            writer.append("\n" + x + "\t");
            for (int i = 0; i < functions.size(); i++) {
                try {
                    writer.append(String.format("%20s", functions.get(i).evaluate(x).toString()) + "\t");
                } catch (EvaluationException ex) {
                    writer.append(String.format("%20s", ex.getMessage() + "\t"));
                } catch (NullPointerException ex) {
                    writer.append(String.format("%20s", "-" + "\t"));
                }
            }

        }
    }
}