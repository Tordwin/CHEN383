/*
 * Initial Author
 *      Michael J. Lutz
 *
 * Other Contributers
 *
 * Acknowledgements
 */
 
/*
 * The TextUI class is an observer of the WeatherStation that,
 * when it receives an update message, prints the temperature
 * in Celsius and Kelvin.
 *
 * The main method for the text based monitoring application
 * is here as well.
 */

import java.util.Observer ;
import java.util.Observable ;
@SuppressWarnings("deprecation") // Suppressing deprecated observer

public class TextUI implements Observer {
    private final WeatherStation station ;

    /*
     * Remember the station we're attached to and
     * add ourselves as an observer.
     */
    public TextUI(WeatherStation station) {
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
         */
        System.out.printf(
                "Reading:   %6.2f C %6.2f F %6.2f K%n",
                station.getCelsius(), station.getFahrenheit(), station.getKelvin()) ;
        System.out.printf(
                "Pressure:  %6.2f inches %6.2f mbar%n",
                station.getPressureMercury(), station.getPressureMilibars()) ;
        System.out.println();
    }

    /*
     * Start the application.
     */
    public static void main(String[] args) {
        WeatherStation ws = new WeatherStation() ;
        Thread thread = new Thread(ws) ;
        new TextUI(ws) ;
        thread.start() ;
    }
}
