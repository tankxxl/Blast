package com.blast.app.utils;

import com.blast.Round;

/**
 * Created by rgz on 2014/8/1.
 */
public class MyCalcUtil {

    public double stoneBridge(double A, double B, double R) {
        return Round.round(1.3D * A * B * Math.pow(R, 3.0D), 2);
    }
    public double steelBridgeLine(double h, double f) {
        return Round.round(10D * h * f, 2);
    }
    public double steelBridgeGroup(double L) {
        if (L <= 15D) {
            return Round.round(L + 4, 2);
        }
        if (L > 15D) {
            return Round.round(1.25D * L + 4, 2);
        }
        return 0.0D;
    }

    public double steelBridgeGroupContactless(double R) {
        return Round.round(20D * Math.pow(R, 2.0D), 2);
    }

    public double trussBridgeSix(double F) {
        return Round.round(25D * F, 2);
    }

    public double trussBridgeFour(double L) {
        return Round.round(0.25D * L + 10, 2);
    }

    public double trussBridgeOne(double A, double B, double R) {
        if (R > 2.5D)
            return 0.0D;
        return this.stoneBridge(A, B, R);
    }

}
