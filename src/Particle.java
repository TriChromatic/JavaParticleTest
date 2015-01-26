import java.awt.*;

public class Particle {
    /*Set vars*/
    private final double GRAVITY = 9.8;
    private final double ENTROPY_MOD = .5;

    /*Acceleration*/
    private double excelX;
    private double excelY;

    /*Mass and size*/
    private final double mass;
    private final int P_SIZE;

    /*Move mod vars*/
    private double moveModY;
    private double moveModX;

    /*Current position*/
    public Point currentPos;

    public Particle( Point startPoint, double sMass, int size ) {
        mass = sMass;
        currentPos = startPoint;
        P_SIZE = size;
        updateExcelY( 1 );
    }

    /**
     * Updates X Y positions based on location
     *
     * @param width  Width of render area
     * @param height Height of render area
     */
    public void updateParticle( int width, int height ) {
        /*Y Find*/
        /*Treats max y like the floor.*/
        final int floor = ( height ) - ( P_SIZE / 2 );

        if ( currentPos.y > floor ) {
            moveModY = -Math.abs( moveModY );
            excelY = -Math.abs( excelY );
            updateExcelY( -1 );
        }

        if ( currentPos.y < floor ) {
            updateExcelY( 1 );
        }

        /*X Find*/
        if ( currentPos.x > width - P_SIZE ) {
            moveModX = -Math.abs( moveModX );
            excelX = -Math.abs( excelX );
            updateExcelX( -1 );
        }

        if ( currentPos.x < 0 ) {
            moveModX = Math.abs( moveModX );
            excelX = Math.abs( excelX );
            updateExcelX( 1 );
        }

        currentPos = new Point( (int) ( currentPos.x + excelX ), (int) ( currentPos.y + excelY ) );
    }

    /**
     * Updates acceleration of Y
     *
     * @param mMod Movement modifier
     */
    private void updateExcelY( double mMod ) {
        moveModY = moveModY + ( mMod * ENTROPY_MOD );
        excelY = mass * GRAVITY + moveModY;
    }

    /**
     * Updates acceleration of X
     *
     * @param mMod Movement modifier
     */
    private void updateExcelX( double mMod ) {
        moveModX = moveModX + ( mMod * ENTROPY_MOD );
        excelX = mass + moveModX;
    }

    /**
     * Simulates particle collision and alters acceleration and movement modification based on this.
     *
     * @param toTest Particle to simulate a collision with
     */
    public void simulateCollision( Particle toTest ) {
        /*Particle deep copy*/
        Particle collision = new Particle( toTest.currentPos, toTest.mass, toTest.P_SIZE );

        /*X Calc*/
        final double posX = Math.abs( currentPos.x ) - Math.abs( collision.currentPos.x );
        excelX = ( excelX - toTest.excelX ) + posX;
        moveModX = ( moveModX - collision.moveModX ) + posX;

        if(moveModX > 0) {
            moveModX -= ENTROPY_MOD;
        } else {
            moveModX += ENTROPY_MOD;
        }

        /*Y Calc*/
        final double posY = Math.abs( currentPos.y ) - Math.abs( collision.currentPos.y );
        excelY = ( excelY - toTest.excelY ) + posY;
        moveModY = ( moveModY - collision.moveModY ) + posY;

        if(moveModY > 0) {
            moveModY -= ENTROPY_MOD;
        } else {
            moveModY += ENTROPY_MOD;
        }

    }
}