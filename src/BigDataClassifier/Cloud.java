/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.BigDataClassifier;

import weka.core.DenseInstance;
import weka.core.Instance;

/**
 *
 * @author lamogha
 */
public class Cloud {
    
    private double focalPoint;
    private double zoneOfInfluence;
    private int point;
    private Instance instance;
    
    public Cloud(double focalPoint2, double zoneOfInfluence2, int point2, DenseInstance instance2){
        focalPoint = focalPoint2;
        zoneOfInfluence = zoneOfInfluence2;
        point = point2;
        instance = instance2; 
    }

    /**
     * Get the value of the focal point of the cloud
     *
     * @return the value of focalPoint
     */
    public double getFocalPoint() {
        return focalPoint;
    }
    
      /**
     * Get the value of ZI of the cloud
     *
     * @return the value of zoneOfInfluence
     */
    public double getZoneOfInfluence() {
        return zoneOfInfluence;
    }
    
      /**
     * Get the value of the cloud points
     *
     * @return the value of point
     */
    public int getPoint() {
        return point;
    }
    
       /**
     * Get the dense instance 
     *
     * @return the instance
     */
    public DenseInstance getDenseInstance() {
        return (DenseInstance) instance;
    }

    /**
     * Set the value of focal point
     *
     * @param focalPoint new value of focalPoint
     */
    public void setFocalPoint(double focalPoint) {
        this.focalPoint = focalPoint;
    }
    
     /**
     * Set the value of ZI
     *
     * @param zoneOfInfluence new value of focalPoint
     */
    public void setZoneOfInfluence(double zoneOfInfluence) {
        this.zoneOfInfluence = zoneOfInfluence;
    }
    
     /**
     * Set the value of points
     *
     * @param point new value of focalPoint
     */
    public void setPoint(int point) {
        this.point = point;
    }
    
     /**
     * Set the value of the instance
     *
     * @param instance new value of focalPoint
     */
    public void setDenseInstance(DenseInstance instance) {
        this.instance = instance;
    }

    
}
