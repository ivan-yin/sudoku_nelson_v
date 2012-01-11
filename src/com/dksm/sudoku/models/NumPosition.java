/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dksm.sudoku.models;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author nelson
 * @version 1.0
 * @date 2012-01-10
 */
public class NumPosition {
    private int num;/*1-9*/
    private ZonePosition zonePosition;
    private Set<ZonePosition> conflictZoneSet = new HashSet<ZonePosition>();

    public Set<ZonePosition> getConflictZoneSet() {
        return conflictZoneSet;
    }

    public void setConflictZoneSet(Set<ZonePosition> conflictZoneSet) {
        this.conflictZoneSet = conflictZoneSet;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public ZonePosition getZonePosition() {
        return zonePosition;
    }

    public void setZonePosition(ZonePosition zonePosition) {
        this.zonePosition = zonePosition;
    }

    public NumPosition() {
    }

    public NumPosition(int num) {
        this.num = num;
    }

    public NumPosition(int num, ZonePosition zonePosition) {
        this.num = num;
        this.zonePosition = zonePosition;
    }

    @Override
    public String toString() {
        return num + " ";
    }
    
    
}
