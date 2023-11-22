/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InheritSolar;

import javafx.scene.image.Image;

/**
 *
 * @author hugo
 */
public class Satellite extends Planet {
    
    private Planet parent;
    
    public Satellite(Planet planet, String name, Image picture, double orbitSize, double orbitSpeed, double planetSize) {
        super(name, picture, orbitSize, orbitSpeed, planetSize);
        this.parent = planet;       
    }

    @Override
    public double getXPos(){
        return parent.getXPos() + super.getXPos();
    }
    @Override
    public double getYPos(){
        return parent.getYPos() + super.getYPos();
    }
}
