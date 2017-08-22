package algs.optimization;

import java.util.Arrays;

public class Svenn {

    public static double[] startSvenn(double x0, double h, Function.Expression expression, Function.TypeLocalization type) {
        Function f = new Function(expression);

        double[] arg = new double[]{x0};
        double[] result = new double[]{x0 - h, x0 + h};
        double f0 = f.calculate(arg);
        arg[0] = result[0];
        double f1 = f.calculate(arg);
        arg[0] = result[1];
        double f2 = f.calculate(arg);

        double x = x0;
        int k = 1;

        switch (type) {
            case MIN_LOCALIZATION:

                if (f1 <= f0 && f0 <= f2) {
                    h = -h;
                    arg[0] = result[0];
                } else if (f1 >= f0 && f0 <= f2) {
                    return result;
                }

                break;

            case MAX_LOCALIZATION:

                if (f1 >= f0 && f0 >= f2) {
                    h = -h;
                    arg[0] = result[0];
                } else if (f1 <= f0 && f0 >= f2) {
                    return result;
                }

                break;
        }

        do {
            if (k > 1000) return null;

            f0 = f.calculate(arg);
            result[0] = x;
            x = arg[0];
            arg[0] = arg[0] + (Math.pow(2, k)) * h; // may be XOR(^) =))
            f1 = f.calculate(arg);
            result[1] = arg[0];
            k++;
        } while ((f1 >= f0 && type == Function.TypeLocalization.MAX_LOCALIZATION)
                || (f1 <= f0 && type == Function.TypeLocalization.MIN_LOCALIZATION));

        Arrays.sort(result);
        return result;
    }
}