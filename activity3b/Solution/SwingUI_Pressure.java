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
 * Swing UI class used for displaying the information from the
 * associated weather station object.
 *
 * This is an extension of JFrame, the outermost container in
 * a Swing application.
 *
 * Also implements Observer so we can receive update notifications
 * from the attached WeatherStation_Pressure.
 */

import java.awt.Font ;
import java.awt.GridLayout ;

import javax.swing.JFrame ;
import javax.swing.JLabel ;
import javax.swing.JPanel ;

import java.util.Observable ;
import java.util.Observer ;

import java.util.Map ;
import java.util.HashMap ;

public class SwingUI_Pressure extends JFrame implements Observer {
    /*
     * Map holding the names of various measures and
     * the corresponding labels.
     */
    private final Map<String, JLabel> measures =
        new HashMap<String, JLabel>() ;

    /*
     * The weather station we are observing.
     */
    private final WeatherStation_Pressure station ;

    /*
     * A Font object contains information on the font to be used to
     * render text.
     */
    private static int POINT_SIZE = 36 ;
    private static Font labelFont =
        new Font(Font.SERIF, Font.PLAIN, POINT_SIZE) ;

    /*
     * Create and populate the SwingUI_Pressure JFrame with panels and labels to
     * show the temperatures.
     */
    public SwingUI_Pressure(WeatherStation_Pressure station) {
        super("Weather Station") ;

        /*
         * Remember our weather station and register ourselves
         * as an observer.
         */
        this.station = station ;
        this.station.addObserver(this) ;

        /*
         * WeatherStation_Pressure frame is a grid of 1 row by an indefinite
         * number of columns.
         */
        this.setLayout(new GridLayout(1,0)) ;

        /*
         * Set up display panels
         * Each Panel is a 2 rows by 1 column grid of labels, with the
         * name in the first row and the measurement in the second row.

         */
        this.add( createPanel("Kelvin") ) ;
        this.add( createPanel("Celsius") ) ;
        this.add( createPanel("Fahrenheit") ) ;
        this.add( createPanel("Inches") ) ;
        this.add( createPanel("Millibars") ) ;

         /*
         * Set up the frame's default close operation pack its elements,
         * and make the frame visible.
         */
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ;
        this.pack() ;
        this.setVisible(true) ;
    }

    /*
     * Create a panel with two vertical labels for a measurement.
     *      The top label is the measurement name (surrounded by spaces).
     *      The bottom label is a place holder for the most recent
     *          measurement value.
     *      The bottom label is registered in the <measures> map with
     *          the measurement name as the key.
     *      Returns the constructed panel for placement by the caller.
     */
    private JPanel createPanel(String name)  {
        JPanel panel = new JPanel(new GridLayout(2,1)) ;
        JLabel label ;

        label = createLabel(" " + name + " ") ;
        panel.add(label) ;

        label = createLabel("") ;
        panel.add(label) ;
        measures.put(name, label) ;

        return panel ;
    }

    /*
     * Create a Label with the initial value <title>, set its
     * layout and font, and return a reference to the label.
     */
    private JLabel createLabel(String title) {
        JLabel label = new JLabel(title) ;

        label.setHorizontalAlignment(JLabel.CENTER) ;
        label.setVerticalAlignment(JLabel.TOP) ;
        label.setFont(labelFont) ;

        return label ;
    }

    /*
     * Called when WeatherStation_Pressure gets another reading.
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
         * Retrieve measurements and save them in the
         * labels.
         */
        setMeasurement("Kelvin", station.getKelvin()) ;
        setMeasurement("Celsius", station.getCelsius()) ;
        setMeasurement("Fahrenheit", station.getFahrenheit()) ;
        setMeasurement("Inches", station.getInches()) ;
        setMeasurement("Millibars", station.getMillibars()) ;
    }

    /*
     * Set the label holding the Kelvin temperature.
     * Now private as it is set from inside the Observer.
     */
    private void setMeasurement(String name, double measurement) {
        JLabel label = measures.get(name) ;
        label.setText(String.format("%7.2f", measurement)) ;
    }

    /*
     * Start the application.
     */
    public static void main(String[] args) {
        WeatherStation_Pressure ws = new WeatherStation_Pressure() ;
        Thread thread = new Thread(ws) ;
        SwingUI_Pressure ui = new SwingUI_Pressure(ws) ;

        thread.start() ;
    }
}
