
import brauhaus.BrauKessel;
import brauhaus.IBrauKessel;
import brauhaus.startup.ConsolePrinter;
import brauhaus.startup.IPrinter;
import brauhaus.startup.StandardStartupOneKessel;
import gui.GStandardSteuerFenster;

/**
 *
 * @author marian
 */
public class Brauerei {
    public static void main(String[] args) throws Exception
    {
        IPrinter printer = new ConsolePrinter();
        StandardStartupOneKessel startup = new StandardStartupOneKessel(printer);
        IBrauKessel braukessel = startup.BuildBrauKessel();
        
        steuerungStarten(printer, braukessel);
    }

    private static void steuerungStarten(IPrinter printer, IBrauKessel braukessel) {
        printer.PrintLn("Starte Gui Steuerung....");
        GStandardSteuerFenster steuerFenster = new GStandardSteuerFenster(braukessel);
        ((BrauKessel)braukessel).addObserver(steuerFenster);
        steuerFenster.setVisible(true);
    }
}
