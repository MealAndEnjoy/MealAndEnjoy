package entity;

import java.io.Serializable;

/**
 * Created by Lenovo1 on 2018/6/18.
 */

public class Num implements Serializable {
    private int littleNum;
    private int middleNum;
    private int largeNum;

    public int getLittleNum() {
        return littleNum;
    }

    public void setLittleNum(int littleNum) {
        this.littleNum = littleNum;
    }

    public int getMiddleNum() {
        return middleNum;
    }

    public void setMiddleNum(int middleNum) {
        this.middleNum = middleNum;
    }

    public int getLargeNum() {
        return largeNum;
    }

    public void setLargeNum(int largeNum) {
        this.largeNum = largeNum;
    }
}
