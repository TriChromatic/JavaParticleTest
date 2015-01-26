import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

/**
 * MainPanel and simulator.
 * Created by TriChromatic aka Dylan Eicher on 12/12/14.
 */
public class MainPanel extends JPanel implements MouseListener {

    private final ArrayList<Particle> particles = new ArrayList<>();
    private final int PANEL_SIZE = 700;
    private final int P_SIZE = 10;
    private final int P_AMOUNT = 1;

    MainPanel() {
        setPreferredSize( new Dimension( PANEL_SIZE, PANEL_SIZE ) );
        addMouseListener( this );

        /*Generates the starting break particle*/
        /*
        for ( int i = 0; i < P_AMOUNT; i++ ) {
            particles.add( new Particle( new Point( rand( 0, PANEL_SIZE ), rand( 0, 20 ) ),
                    Math.random() + .5,
                    P_SIZE ) );
        }
        */

        /*Generates a line of particles at the bottom of the screen*/
        /*
        for ( int i = 1; i < ( PANEL_SIZE / P_SIZE ); i++ ) {
            particles.add( new Particle( new Point( i * P_SIZE, PANEL_SIZE - P_SIZE / 2 ),
                    0.5,
                    P_SIZE ) );
        }
        */

        /*Start render*/
        Timer timer = new Timer( 60, new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                repaint();
            }
        } );

        timer.start();
    }

    /**
     * Renders particles and checks for collisions.
     *
     * @param gIn Graphics in
     */
    public void paintComponent( Graphics gIn ) {
        Graphics2D g = (Graphics2D) gIn;

        for ( Particle p : particles ) {
            /*Update position*/
            p.updateParticle( getWidth(), getHeight() );

            /*Collision check*/
            for ( Particle check : particles ) {
                if ( inDistance( p.currentPos, check.currentPos, P_SIZE - 1 ) && check != p ) {
                    p.simulateCollision( check );
                    check.simulateCollision( p );

                    int x = p.currentPos.x - ( P_SIZE / 2 );
                    int y = p.currentPos.y - ( P_SIZE / 2 );
                    g.fillOval( x, y, P_SIZE, P_SIZE );

                    int cX = p.currentPos.x - ( P_SIZE / 2 );
                    int cY = p.currentPos.y - ( P_SIZE / 2 );
                    g.fillOval( cX, cY, P_SIZE, P_SIZE );
                }
            }

            int drawLineX = p.currentPos.x;
            int drawLineY = p.currentPos.y;
            int x = p.currentPos.x - ( P_SIZE / 2 );
            int y = p.currentPos.y - ( P_SIZE / 2 );

            /*Draw the particle if it is within render view, else we draw a line to the position*/
            if ( p.currentPos.x < getWidth() && p.currentPos.y < getHeight() && p.currentPos.x > 0 && p.currentPos.y > 0 ) {
                g.drawOval( x, y, P_SIZE, P_SIZE );
            } else {
                g.drawLine( drawLineX, drawLineY, getWidth() / 2, getHeight() / 2 );
            }
        }

        gIn.dispose();
        g.dispose();
    }

    /**
     * Generates a bunch of particles at the edge of the screen.
     */
    private void particleCannon() {
        particles.add( new Particle( new Point( P_SIZE / 2, getHeight() / 2 ), .5, P_SIZE ) );
        particles.add( new Particle( new Point( P_SIZE, getHeight() / 2 ), .5, P_SIZE ) );
    }

    private boolean inDistance( Point Old, Point Current, int distance ) {
        int diffX = Math.abs( Old.x - Current.x );
        int diffY = Math.abs( Old.y - Current.y );
        return diffX <= distance && diffY <= distance;
    }

    public int rand( int min, int max ) {
        Random rand = new Random();
        int picked = rand.nextInt( ( max - min ) + 1 ) + min;
        return picked;
    }

    @Override
    public void mouseClicked( MouseEvent e ) {
        particles.add( new Particle( new Point( e.getX(), e.getY() ), Math.random() + .5, P_SIZE ) );
    }

    @Override
    public void mousePressed( MouseEvent e ) {

    }

    @Override
    public void mouseReleased( MouseEvent e ) {

    }

    @Override
    public void mouseEntered( MouseEvent e ) {

    }

    @Override
    public void mouseExited( MouseEvent e ) {

    }
}
