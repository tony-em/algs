package algs.optimization;

import java.util.Arrays;

public class Dichotomy {

    public static double startDichotomy(double[] interval, double delta, double eps, Function.Expression expression, Function.TypeLocalization type) {
        Function f = new Function(expression);
        Arrays.sort(interval);

        if (delta >= eps) return 0;

        double x1, x2, a, b;
        while (Math.abs(interval[0] - interval[1]) > eps) {
            x1 = (interval[0] + interval[1] - delta) / 2;
            x2 = (interval[0] + interval[1] + delta) / 2;

            double[] arg = new double[]{interval[0]};
            a = arg[0];
            double f1 = f.calculate(arg);
            arg[0] = interval[1];
            b = arg[0];
            double f2 = f.calculate(arg);

            if (f1 < f2) {
                if (type == Function.TypeLocalization.MIN_LOCALIZATION) {
                    interval[0] = a;
                    interval[1] = x2;
                } else {
                    interval[0] = x1;
                    interval[1] = b;
                }
            } else if (f1 > f2) {
                if (type == Function.TypeLocalization.MIN_LOCALIZATION) {
                    interval[0] = x1;
                    interval[1] = b;
                } else {
                    interval[0] = a;
                    interval[1] = x2;
                }
            }
        }

        return (interval[0] + interval[1]) / 2;
    }
}
