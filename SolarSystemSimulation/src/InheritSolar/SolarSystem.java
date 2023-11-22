/**
 *
 */
package InheritSolar;

import SolarSystem.MyCanvas;
import java.util.ArrayList;

import javafx.scene.image.Image;

/**
 * @author Hugo O.D.
 *
 */
public class SolarSystem {

    private final ArrayList<Planet> planets;
    private double sunX, sunY, sunSize;				// positions of sunPic
    private Image sunPic;								// imnge of sunPic
    private Planet mercury;
    private Planet venus;
    private Planet earth;
    private Planet mars;
    private Planet phobos;
    private Planet deimos;
    private Satellite moon;
    /**
     * constructor for setting up solar system
     */
    public SolarSystem() {

        planets = new ArrayList<>();
        mercury = new Planet("Mercury", new Image(getClass().getResourceAsStream("mercury.png")), 0.25, 2, 0.05);
        venus = new Planet("Venus", new Image(getClass().getResourceAsStream("venus.jpeg")), 0.3, 1.7, 0.07);
        earth = new Planet("Earth", new Image(getClass().getResourceAsStream("earth.png")), 0.25, 1, 0.1);
        mars = new Planet("Mars", new Image (getClass().getResourceAsStream("mars.png")), 0.4, 2, 0.08);
        phobos = new Satellite(mars, "Phobos", new Image (getClass().getResourceAsStream("phobos.jpeg")), 0.08, 3, 0.05);
        deimos = new Satellite(mars, "Deimos", new Image (getClass().getResourceAsStream("deimos.jpeg")), 0.1, 3, 0.05);
        moon = new Satellite(earth, "Moon", new Image (getClass().getResourceAsStream("moon.jpeg")), 0.08, 3, 0.05);
        planets.add(mercury);
        planets.add(venus);
        planets.add(earth);
        planets.add(mars);      
        planets.add(moon);
        planets.add(phobos);
        planets.add(deimos);
        sunPic = new Image(getClass().getResourceAsStream("sun.png"));
        sunX = 0.5;
        sunY = 0.5;
        sunSize = 0.2;
    }

    /**
     * Calculate the position of each object in system
     *
     * @param angle	indication of time/angle
     */
    public void updateSystem(double angle) {
        for (Planet planet : planets) {
            planet.updatePos(angle);
        }
    }   
    
    public void setSystem(MyCanvas mc, double x, double y){
        sunX = x/mc.getXCanvasSize();
        sunY = y/mc.getXCanvasSize();
    }

    /**
     * draw the system into the given viewer
     *
     * @param mc
     * @param satOn
     */
    public void drawSystem(MyCanvas mc, boolean satOn) {
        drawImage(mc, sunPic, 0, 0, sunSize); // draw Sun,
        for (Planet planet : planets) {
            if (satOn || !(planet instanceof Satellite)) {
                planet.drawPlanet(this, mc);
            }
        }
       
    }

    /**
     * drawImage into canvas, at position x,y relative to sunPic, but scale the x,y
 and sz before drawing
     *
     * @param mc	canvas
     * @param i	image
     * @param x	x position	in range -0.5..0.5
     * @param y	y position
     * @param sz	size
     */
    public void drawImage(MyCanvas mc, Image i, double x, double y, double sz) {
        int cs = mc.getXCanvasSize();
        mc.drawImage(i, (x + sunX) * cs, (y + sunY) * cs, sz * cs);		// add 0.5 to positions then * canvas size
    }

    /**
     * return String with info of planet(s) in system
     * @return 
     */
    @Override
    public String toString() {
        String res = "";
        for (Planet planet : planets) {
            res += planet.toString() + " \n";
                    }
        return res;
    }

}
