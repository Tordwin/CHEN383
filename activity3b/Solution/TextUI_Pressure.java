/*
 * SOURCE KEPT FROM STUDENTS.
 * Used only for executable jar file so show output.
 */

/*
 * Initial Author
 *      Michael J. Lutz
 *
 * Other Contributers
 *
 * Acknowledgements
 */

/*
 * The TextUI_Pressure class is an observer of the WeatherStation that,
 * when it receives an update message, prints the temperature
 * in Celsius, Kelvin and Fahrenheit and the barometric pressure
 * in inches and millibars.
 *
 * The main method for the text based monitoring application
 * is here as well.
 */
import java.util.Observer ;
import java.util.Observable ;

public class TextUI_Pressure implements Observer {
    private final WeatherStation_Pressure station ;

    /*
     * Remember the station we're attached to and
     * add ourselves as an observer.
     */
    public TextUI_Pressure(WeatherStation_Pressure station) {
        this.station = station ;
        this.station.addObserver(this) ;
    }

    /*
     * Called when WeatherStation gets another reading.
     * The Observable should be the station; the Object
     * argument is ignored.
     */
    public void update(Observable obs, Object ignore) {
        /*
         * Check for spurious updates from unrelated objects.
         */
        if( station != obs ) {
            return ;
        }
        /*
         * Retrieve and print the temperatures.
         * Retrieve and print the pressures.
         * Print blank line.
         */
        System.out.printf(
                "Temperature: %6.2f C %6.2f F %6.2f K%n",
                station.getCelsius(),
                station.getFahrenheit(),
                station.getKelvin()) ;

        System.out.printf(
                "Pressure:    %6.2f inches %7.2f mbar%n",
                station.getInches(), station.getMillibars()) ;

        System.out.println() ;
    }

    /*
     * Start the application.
     */
    public static void main(String[] args) {
        WeatherStation_Pressure ws = new WeatherStation_Pressure() ;
        Thread thread = new Thread(ws) ;
        TextUI_Pressure ui = new TextUI_Pressure(ws) ;

        thread.start() ;
    }
}
