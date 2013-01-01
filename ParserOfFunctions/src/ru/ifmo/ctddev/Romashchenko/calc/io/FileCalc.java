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
    
    private BufferedReader reader;
    private PrintWriter writer;
    private List<IEvaluation> functions;
    
    public static void main(String args[]) throws IOException {
        FileCalc fcalc = new FileCalc(new File("inputfile.txt"));
        fcalc.write(new File("resultfile.txt"));
    }
    
    public FileCalc(File inputfile) throws IOException {
        try {
            reader = new BufferedReader(new FileReader(inputfile));
            functions = new ArrayList<IEvaluation>();
            String strfunction;
            while ((strfunction = reader.readLine()) != null) {
                try {
                    IEvaluation evaluation = new RPR(ParserOfFunctions.getTokens(strfunction)).getFormula();
                    functions.add(evaluation);
                } catch (EvaluationException | ParserException ex) {
                    functions.add(null);
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
    
    public void write(File outputfile) {
        try {
            writer = new PrintWriter(outputfile);
            if (functions.isEmpty()) {
                writer.write("functions are absent in  the file");
                return;
            }
            writer.write("x\t");
            for (int i = 0; i < functions.size(); i++) {
                writer.write(String.format("%20s", ("f" + i)) + "\t");
            }
            for (int i = 0; i <= 10; i++) {
                writer.write("\n" + i + "\t");
                for (int k = 0; k < functions.size(); k++) {
                    try {
                        //String number = "" + functions.get(k).evaluate((double) i);
                        //number = number.replaceAll("(?<=.{20}).+", "");
                        writer.write(String.format("%20s",functions.get(k).evaluate((double) i).toString()) + "\t");
                    } catch (EvaluationException ex) {
                        writer.write(ex.getMessage());
                    } catch (NullPointerException ex) {
                        writer.write("-");
                    }
                    
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
