import javax.swing.*;

/**
 * Driver
 * Created by TriChromatic aka Dylan Eicher on 12/12/14.
 */
public class Driver {
    public static void main( String[] args ) {
        JFrame test = new JFrame();
        test.add( new MainPanel() );
        test.pack();
        test.setVisible( true );
        test.setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );
    }
}
