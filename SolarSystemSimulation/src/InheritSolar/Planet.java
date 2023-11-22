/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InheritSolar;

import SolarSystem.MyCanvas;
import javafx.scene.image.Image;

/**
 *
 * @author hugo
 */
public class Planet {
        private String name;
        private Image picture;
        protected double orbitSize;
        protected double orbitSpeed;
        protected double planetSize;
        protected double planetAngle;

    public Planet(String name, Image picture, double orbitSize, double orbitSpeed, double planetSize) {
        this.name = name;
        this.picture = picture;
        this.orbitSize = orbitSize;
        this.orbitSpeed = orbitSpeed;
        this.planetSize = planetSize;
    }
        
    public void updatePos(double angle){
        planetAngle = angle*orbitSpeed;
    }
    
    public double getXPos(){
        return orbitSize*Math.cos(planetAngle);
    }
    
    public double getYPos(){
        return orbitSize*Math.sin(planetAngle);
    }
    
    public void drawPlanet(SolarSystem system, MyCanvas mc){
        int cs = mc.getXCanvasSize();
        system.drawImage(mc, picture, getXPos(), getYPos(), planetSize);
    }
    
        public String toString() {
	  return name + " at: "+ Math.round(planetAngle);
    }    
        
}
