/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dksm.sudoku.models;

/**
 *
 * @author nelson
 */
public class ZonePosition {

    private int zone; /*
     * [0,8]
     */

    private int poz; 

    /*
     * [0,8]
     */

    public ZonePosition() {
    }

    public ZonePosition(int zone, int poz) {
        if(zone < 0 || poz < 0 || zone > 8 || poz > 8)
            throw new RuntimeException("zone or position should in [0, 8]");
        this.zone = zone;
        this.poz = poz;
    }

    public int getPoz() {
        return poz;
    }

    public void setPoz(int poz) {
        this.poz = poz;
    }

    public int getZone() {
        return zone;
    }

    public void setZone(int zone) {
        this.zone = zone;
    }

    @Override
    public String toString() {
        return "ZonePosition{" + "zone=" + zone + ", poz=" + poz + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ZonePosition other = (ZonePosition) obj;
        if (this.zone != other.zone) {
            return false;
        }
        if (this.poz != other.poz) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + this.zone;
        hash = 47 * hash + this.poz;
        return hash;
    }
    
}
