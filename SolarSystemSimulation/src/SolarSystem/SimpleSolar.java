package SolarSystem;

import javafx.scene.image.Image;

/**
 * class for simple solar system, initially with sun and earth
 * @author Hugo O.D.
 *
 */
public class SimpleSolar {
        
	private double sunX, sunY, sunSize;						// position of sun & size sun should be drawn
	private double earthSize;
        private double earthX, earthY;
        private double marsX, marsY, marsSize;
	private double earthAngle;						// angle of earth .. for calculating its position
	private double earthOrbitSize = 0.3;			// defines relevant sizes (<1 so that positions in range -0.5 .. +0.5
	private double marsAngle;
        private double marsOrbitSize = 0.4;
        private Image earth;							// images of earth and sun
	private Image sun;
        private Image mars;

	/**
	 * construct simple solar system
	 */
    public SimpleSolar() {
    	earth = new Image(getClass().getResourceAsStream("earth.png"));
    	sun = new Image(getClass().getResourceAsStream("sun.png"));
        mars = new Image(getClass().getResourceAsStream("mars.png"));
    	sunX = 0.5;															// set position of sun
    	sunY = 0.5;
        sunSize = 0.2;
        earthSize = 0.1;
        marsSize = 0.08;
    	earthAngle = 0.0;													// initialise earth
        marsAngle = 0.0;
    }
    
	/**
	 * update position of planet(s) at specified angle 
	 * @param angle		angle (time dependent) of planet(s)
	 */
	public void updateSystem (double angle) {
            earthAngle = angle;
            marsAngle = angle/2;
            earthX = (earthOrbitSize*Math.cos(earthAngle));
            earthY = (earthOrbitSize*Math.sin(earthAngle));
            marsX = (marsOrbitSize*Math.cos(marsAngle));
            marsY = (marsOrbitSize*Math.sin(marsAngle));
            // set angle of earth appropriately
	}
	
	/** 
	 * set sun at position passed
	 * @param x		x position, in canvas coordinates
	 * @param y
	 */
	public void setSystem(MyCanvas mc, double x, double y) {
		sunX = x/mc.getXCanvasSize();
                sunY = y/mc.getXCanvasSize();
                // note x,y in range 0.. canvassize
	}

	/**
	 * drawImage into canvas, at position x,y relative to sun, but scale the x,y and sz before drawing
	 * @param mc	canvas
	 * @param i		image
	 * @param x		x position		in range -0.5..0.5
	 * @param y		y position
	 * @param sz	size
	 */
	public void drawImage (MyCanvas mc, Image i, double x, double y, double sz) {
		int cs = mc.getXCanvasSize();
		mc.drawImage(i, (x+sunX)*cs, (y+sunY)*cs, sz*cs);		// add sun's position to positions then * canvas size
        }
	
	/**
	 * draw system  into specified canvas
	 * @param mc		canvas
	 */
	public void drawSystem (MyCanvas mc) {
                mc.clearCanvas();					// first clear canvas 
		drawImage(mc, sun, 0, 0, sunSize);				// draw sun at 0,0
		drawImage(mc, earth, earthX, earthY, earthSize);
                // call drawImage to draw earth at position set by earth's angle and orbit size
                drawImage(mc, mars, marsX, marsY, marsSize);
        }

        public double getEarthAngle(){
            return earthAngle;
        }
        
	/**
	 * return information about planets as a string
	 */
	public String toString(MyCanvas mc) {
          int cs = mc.getXCanvasSize();
	  return "Earth at: "+ Math.round(earthAngle) + "\nMars at: " + Math.round(marsAngle);
	}
}
