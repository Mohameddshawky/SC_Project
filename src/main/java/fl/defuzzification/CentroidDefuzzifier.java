package src.main.java.fl.defuzzification;

import src.main.java.fl.membership.MembershipFunction;


public class CentroidDefuzzifier implements Defuzzifier {


    @Override
    public double defuzzify(MembershipFunction membershipFunction, double minValue, double maxValue) {
        return 0;
    }

    @Override
    public String getName() {
        return "";
    }
}

