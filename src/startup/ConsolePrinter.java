/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package startup;

/**
 *
 * @author marian
 */
public class ConsolePrinter implements IPrinter {

    @Override
    public void Print(String text) {
        System.out.print(text);
    }

    @Override
    public void PrintLn(String text) {
        System.out.println(text);
    }
    
}
