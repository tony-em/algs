package algs.optimization;

import java.util.Arrays;

public abstract class EvenSearch {

    public static double startEvenSearch(double[] interval, double h, Function.Expression expression, Function.TypeLocalization type) {
        Function f = new Function(expression);

        Arrays.sort(interval);
        double[] arg = new double[]{interval[0]};
        double f1, f0 = f.calculate(arg);

        do {
            f1 = f0;
            arg[0] = arg[0] + h;
            f0 = f.calculate(arg);
        } while ((f0 < f1 && type == Function.TypeLocalization.MIN_LOCALIZATION)
                || (f0 > f1 && type == Function.TypeLocalization.MAX_LOCALIZATION));

        return arg[0];
    }
}