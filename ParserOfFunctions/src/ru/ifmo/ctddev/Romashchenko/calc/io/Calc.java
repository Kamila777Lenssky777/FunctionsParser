/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ifmo.ctddev.Romashchenko.calc.io;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author Андрей
 */
public class Calc {

    public static void main(String args[]) throws IOException {
        Function func = new Function(new File("inputfile.txt"));
        func.write(new File("out.txt"));
    }
}
