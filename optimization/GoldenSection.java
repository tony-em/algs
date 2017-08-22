package algs.optimization;

import java.util.Arrays;

public class GoldenSection {

    public static double startGoldenSection(double[] interval, double eps, Function.Expression expression, Function.TypeLocalization type) {
        Function f = new Function(expression);
        double t1 = (Math.sqrt(5) - 1) / 2,
                t2 = 1 - t1;

        Arrays.sort(interval);

        double x0 = interval[0],
                x3 = interval[1], x1 = 0, x2 = 0, f1 = 0, f2 = 0, l = 0;
        double[] arg = new double[1];

        boolean flag1 = true, flag2 = true;
        do {

            if (flag1 || flag2) {
                if (flag1) {
                    x1 = x0 + (t2 * (x3 - x0));
                    arg[0] = x1;
                    f1 = f.calculate(arg);
                    flag1 = false;
                }

                if (flag2) {
                    x2 = x0 + (t1 * (x3 - x0));
                    arg[0] = x2;
                    f2 = f.calculate(new double[]{x2});
                    flag2 = false;
                }
            }

            if ((f1 > f2 && type == Function.TypeLocalization.MIN_LOCALIZATION)
                    || (f1 < f2 && type == Function.TypeLocalization.MAX_LOCALIZATION)) {
                l = x3 - x1;
                x0 = x1;
                x1 = x2;
                f1 = f2;
                flag2 = true;
            } else if ((f1 <= f2 && type == Function.TypeLocalization.MIN_LOCALIZATION)
                    || (f1 >= f2 && type == Function.TypeLocalization.MAX_LOCALIZATION)) {
                l = x2 - x0;
                x3 = x2;
                x2 = x1;
                f2 = f1;
                flag1 = true;
            }

        } while (l > eps);

        return (x1 + x2) / 2;
    }
}