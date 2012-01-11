/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dksm.sudoku.models;

/**
 *
 * @author nelson
 */
public class GridPosition {
    private int row; /*[1,9]*/
    private int col; /*[1,9]*/

    public GridPosition() {
    }

    /*1-9*/
    public GridPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }


    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    @Override
    public String toString() {
        return "GridPosition{" + "row=" + row + ", col=" + col + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GridPosition other = (GridPosition) obj;
        if (this.row != other.row) {
            return false;
        }
        if (this.col != other.col) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 61 * hash + this.row;
        hash = 61 * hash + this.col;
        return hash;
    }
    
}
