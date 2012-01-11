/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dksm.sudoku.ctrls;

import com.dksm.sudoku.Sudoku;
import com.dksm.sudoku.models.GridPosition;
import com.dksm.sudoku.models.NumPosition;
import com.dksm.sudoku.models.ZonePosition;
import java.util.*;

/**
 *
 * @author nelson
 */
public class CommonUtil {

    public static enum Type {

        /**
         * zone [0,8]
         */
        ZONE,
        /**
         * row [1, 9]
         */
        ROW,
        /**
         * column [1,9]
         */
        COLUMN,
        /**
         * ok
         */
        OK;
    }

    /**
     * getNumPositionByZonePosition
     *
     * @param ZonePosition
     * @return NumPosition
     */
    public static NumPosition getNumPositionByZonePosition(ZonePosition zonePosition) {
        for (ZonePosition zp : Sudoku.numPozMap.keySet()) {
            if (zp.equals(zonePosition)) {
                return Sudoku.numPozMap.get(zp);
            }
        }
        return null;
    }

    /**
     *
     * @param zone in [0-8]
     * @return NumPositionZoneList
     */
    public static List<NumPosition> getNumPositionZoneList(int zone) {
        List<NumPosition> numZoneList = new ArrayList<NumPosition>();
        for (ZonePosition zp : Sudoku.numPozMap.keySet()) {
            if (zp.getZone() == zone) {
                numZoneList.add(Sudoku.numPozMap.get(zp));
            }
        }
        return numZoneList;
    }

    /**
     *
     * @param row in [1-9]
     * @return NumPositionZoneList
     */
    public static List<NumPosition> getNumPositionRowList(int row) {
        List<NumPosition> numZoneList = new ArrayList<NumPosition>();
        for (ZonePosition zp : Sudoku.numPozMap.keySet()) {
            GridPosition gp = zonePoz2GridPoz(zp);
            if (gp.getRow() == row) {
                numZoneList.add(Sudoku.numPozMap.get(zp));
            }
        }
        return numZoneList;
    }

    /**
     *
     * @param col in [1-9]
     * @return NumPositionZoneList
     */
    public static List<NumPosition> getNumPositionColList(int col) {
        List<NumPosition> numZoneList = new ArrayList<NumPosition>();
        for (ZonePosition zp : Sudoku.numPozMap.keySet()) {
            GridPosition gp = zonePoz2GridPoz(zp);
            if (gp.getCol() == col) {
                numZoneList.add(Sudoku.numPozMap.get(zp));
            }
        }
        return numZoneList;
    }

    /**
     * ZonePosition --> GridPosition
     *
     * @param zonePosition
     * @return GridPosition
     */
    public static GridPosition zonePoz2GridPoz(ZonePosition zonePosition) {
        GridPosition gridPosition = new GridPosition();
        gridPosition.setRow((zonePosition.getZone() / 3) * 3 + zonePosition.getPoz() / 3 + 1);
        gridPosition.setCol((zonePosition.getZone() % 3) * 3 + zonePosition.getPoz() % 3 + 1);
        return gridPosition;
    }

    /**
     * GridPosition --> ZonePosition
     *
     * @param gridPosition
     * @return ZonePosition
     */
    public static ZonePosition gridPoz2ZonePoz(GridPosition gridPosition) {
        ZonePosition zonePosition = new ZonePosition();
        zonePosition.setZone(((gridPosition.getRow() - 1) / 3) * 3 + (gridPosition.getCol() - 1) / 3);
        zonePosition.setPoz(((gridPosition.getRow() - 1) % 3) * 3 + (gridPosition.getCol() - 1) % 3);
        return zonePosition;
    }

    private static List<NumPosition> get(Type vt, int param) {
        List<NumPosition> numZoneList = null;
        switch (vt) {
            case ROW:
                numZoneList = CommonUtil.getNumPositionRowList(param);
                break;
            case COLUMN:
                numZoneList = CommonUtil.getNumPositionColList(param);
                break;
            case ZONE:
            default:
                numZoneList = CommonUtil.getNumPositionZoneList(param);
                break;
        }
        return numZoneList;
    }

    /**
     * validate param and vt has repeat num. if have , set num bg to red.
     *
     * @param param
     * @param vt
     */
    public static boolean validateHasRepeat1Of9(int param, Type vt) {
        List<NumPosition> numZoneList = get(vt, param);
        boolean flag = false;
        int maxConNum = 1; // max = 9
        for (int i = 0; i < 9 && maxConNum < 9; i++) {
            NumPosition numNPI = numZoneList.get(i);
            int numi = numNPI.getNum();
            for (int j = i + 1; j < 9; j++) {
                NumPosition numNPJ = numZoneList.get(j);
                if (numi == numNPJ.getNum()) {
                    numNPI.getConflictZoneSet().add(numNPJ.getZonePosition());
                    maxConNum++;
                    flag = true;
                }
            }
        }
        return flag;
    }

    /**
     * validate param and vt is right.
     *
     * @param param
     * @param vt
     * @return true if the type has 1-9, else false.
     */
    public static boolean validateHas1Of9(int param, Type vt) {
        List<NumPosition> numZoneList = get(vt, param);

        Integer[] numArr = new Integer[9];
        for (int i = 0; i < 9; i++) {
            NumPosition numNPI = numZoneList.get(i);
            int numi = numNPI.getNum();
            numArr[i] = numi;
        }
        Set<Integer> numsSet = new HashSet(Arrays.asList(Sudoku.NUMS));
        Set<Integer> numSet = new HashSet(Arrays.asList(numArr));
        
        return numsSet.containsAll(numSet);
    }

    /**
     * validate NumPosition is right.
     *
     * @param np
     * @return true if num in this zone position is right.
     */
    public static Type isNumPozRight(NumPosition np) {
        //1. validate zone
        Type t = Type.ZONE;
        if (!validateHas1Of9(np.getZonePosition().getZone(), t)) {
            return t;
        }

        //2. validate row
        GridPosition gp = zonePoz2GridPoz(np.getZonePosition());
        t = Type.ROW;
        if (!validateHas1Of9(gp.getRow(), t)) {
            return t;
        }

        //3. validate col
        t = Type.COLUMN;
        if (!validateHas1Of9(gp.getCol(), t)) {
            return t;
        }
        //right
        return Type.OK;
    }

    /**
     * isNumPositionRight
     *
     * @param np
     * @param npList
     * @return true if np in this list is only
     */
    private static boolean isNumPositionRight(NumPosition np, List<NumPosition> npList) {
        for (NumPosition numPosition : npList) {
            if (numPosition.getZonePosition().equals(np.getZonePosition())) {
                continue;
            }
            if (np.getNum() == numPosition.getNum()) {
                return false;
            }
        }
        return true;
    }

    public static void generateAnswer() {
        for (int i = 1; i < 10; i++) {//loop row
            List<NumPosition> npList = getNumPositionRowList(i);
            for (NumPosition numPosition : npList) {//loop row-col's num
                if (Type.OK == isNumPozRight(numPosition)) {//right
                    continue;//next
                } else {
                    int j = 1, num = numPosition.getNum();
                    boolean flag = false;
                    //validate num zone, row , col
                    while (j < 9) {
                        j++;
                        if (j == num) {
                            continue;
                        }
                        numPosition.setNum(j);
                        if (Type.OK == isNumPozRight(numPosition)) {
                            flag = true;
                            break;
                        }
                    }
                    if (!flag) {
                        numPosition.setNum(num);
                    }
                }
            }
        }
    }

    public static void generateAnswer2() {
        for (int i = 1; i < 10; i++) {//loop row 1-9
            List<NumPosition> npList = getNumPositionRowList(i);
            for (NumPosition numPosition : npList) {//loop row-col's num
                Type typeT = null;
                while ((typeT = isNumPozRight(numPosition)) != Type.OK) {
                    ZonePosition zp = numPosition.getZonePosition();
                    GridPosition gp = zonePoz2GridPoz(zp);
                    switch (typeT) {
                        case ROW:
                            adjustNumPoz(gp.getRow(), typeT);
                            break;
                        case COLUMN:
                            adjustNumPoz(gp.getCol(), typeT);
                            break;
                        case ZONE:
                            adjustNumPoz(zp.getZone(), typeT);
                            break;
                    }
                }
            }
        }
    }

    public static void adjustNumPoz(int param, Type type) {
        if (validateHasRepeat1Of9(param, type)) {

            //get used num
            Set<Integer> numUsedSet = new HashSet<Integer>();
            Iterable<NumPosition> npList = get(type, param);
            for (NumPosition np : npList) {//loop NumPosition
                numUsedSet.add(np.getNum());
            }
            //get unused num
            Set<Integer> numUnsedSet = new HashSet(Arrays.asList(Sudoku.NUMS));
            numUnsedSet.removeAll(numUsedSet);

            for (NumPosition numPosition : npList) {//loop NumPosition
                for (ZonePosition zpT : numPosition.getConflictZoneSet()) {
                    NumPosition npT = getNumPositionByZonePosition(zpT);
                    for (Iterator it = numUnsedSet.iterator(); it.hasNext();) {
                        Integer numUn = (Integer) it.next();
                        npT.setNum(numUn);
                        break;
                    }
                    //delete from numUnsedSet
                    if (numUnsedSet.contains(npT.getNum())) {
                        numUnsedSet.remove(npT.getNum());
                    }
//                        npT.getConflictZoneSet().clear();
                }
                numPosition.getConflictZoneSet().clear();
            }

        }
    }

    public static void consoleOutSudoku() {
        for (int i = 1; i < 10; i++) {
            System.out.println(getNumPositionRowList(i));
        }
    }

    public static void main(String[] a) {
        ZonePosition zonePosition = new ZonePosition(0, 8);
        System.out.println(zonePosition);
        GridPosition gridPosition = zonePoz2GridPoz(zonePosition);
        System.out.println(gridPosition);
        zonePosition = gridPoz2ZonePoz(gridPosition);
        System.out.println(zonePosition);
        consoleOutSudoku();
        System.out.println("generate......Answer");
        generateAnswer2();
        System.out.println("generateAnswer");
        consoleOutSudoku();
    }
}
