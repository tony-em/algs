package algs.optimization;

import java.util.Arrays;

public abstract class Bisection {

    public static double startBisection(double[] interval, double eps, Function.Expression expression, Function.TypeLocalization type) {
        Function f = new Function(expression);
        Arrays.sort(interval);

        double x, x1, x2, l;
        x = (interval[0] + interval[1]) / 2;
        l = interval[0] + interval[1];

        do {
            x1 = interval[0] + (l / 4);
            x2 = interval[1] - (l / 4);

            double[] arg = new double[]{x1};
            double f1 = f.calculate(arg);
            arg[0] = x2;
            double f2 = f.calculate(arg);
            arg[0] = x;
            double fx = f.calculate(arg);


            if ((f1 < fx && type == Function.TypeLocalization.MIN_LOCALIZATION)
                    || (f1 > fx && type == Function.TypeLocalization.MAX_LOCALIZATION)) {
                interval[1] = x;
                x = x1;
            } else if ((f2 < fx && type == Function.TypeLocalization.MIN_LOCALIZATION)
                    || (f2 > fx && type == Function.TypeLocalization.MAX_LOCALIZATION)) {
                interval[0] = x;
                x = x2;
            } else {
                interval[0] = x1;
                interval[1] = x2;
            }

        } while ((l = interval[1] - interval[0]) > eps);

        return (interval[0] + interval[1]) / 2;
    }
}
