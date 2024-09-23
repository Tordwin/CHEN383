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
 * Class for a simple computer based weather station that samples the current
 * temperature and barometric pressure every second.
 *
 * The temperature sensor reports the Kelvin temperature as a 16-bit number
 * to the nearest1/100th of a degree.
 * The barometer reports the pressure in inches of mercury as a double
 * precision number.
 *
 * This class is implements Runnable so that it can be embedded in a Thread
 * which runs the periodic sensing.
 *
 * The class also extends Observable so that it can notify registered
 * objects whenever its state changes. Convenience functions are provided
 * to access the temperature in different schemes (Celsius, Kelvin, etc.)
 */
import java.util.Observable ;

public class WeatherStation_Pressure extends Observable implements Runnable {

    private final KelvinTempSensor temp_sensor ; // Temperature sensor.
    private final Barometer barometer ;          // Pressure sensor.

    private final long PERIOD = 1000 ;          // 1 sec = 1000 ms
    private final int K_TO_C = -27315 ;         // Kelvin to Celsius
    private final double I_TO_M = 33.864 ;      // Inches to millibar
    private final double C_TO_F = (9.0 / 5.0) ; // Celsius to Fahr. factor

    private int currentTemperature ;
    private double currentPressure ;

    /*
     * Create the temperature and barometric pressure sensors and
     * retrieve the initial readings.
     */
    public WeatherStation_Pressure() {
        temp_sensor = new KelvinTempSensor() ;
        barometer = new Barometer() ;

        currentTemperature = temp_sensor.reading() ;
        currentPressure = barometer.pressure() ;
    }

    /*
     * The "run" method called by the enclosing Thread object when started.
     * Repeatedly sleeps a second, acquires the current temperature from its
     * temp_sensor, and notifies registered Observers of the change.
     */
    public void run() {
        while( true ) {
            try {
                Thread.sleep(PERIOD) ;
            } catch (Exception e) {}    // ignore exceptions

            /*
             * Get next readings and notify any Observers.
             */
            synchronized(this) {
                currentTemperature = temp_sensor.reading() ;
                currentPressure = barometer.pressure() ;
            }
            setChanged() ;
            notifyObservers() ;
        }
    }

    /*
     * Return the current temperature in degrees celsius as a
     * double precision number.
     */
    public synchronized double getCelsius() {
        return (currentTemperature + K_TO_C) / 100.0 ;
    }

    /*
     * Return the current temperature in degrees Fahrenheit as a
     * double precision number.
     */
    public synchronized double getFahrenheit() {
        return getCelsius() * C_TO_F + 32.0 ;
    }

    /*
     * Return the current temperature in degrees Kelvin
     */
    public synchronized double getKelvin() {
        return currentTemperature / 100.0 ;
    }

    /*
     * Return the current pressure in inches of mercury.
     */
    public synchronized double getInches() {
        return currentPressure ;
    }

    /*
     * Return the current pressure in millibars.
     */
    public synchronized double getMillibars() {
        return currentPressure * I_TO_M ;
    }
}
