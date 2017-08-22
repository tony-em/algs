package algs.optimization;

import java.util.ArrayList;
import java.util.Arrays;

public class Fibonacci {

    public static double startFibonacci(double[] interval, double eps, Function.Expression expression, Function.TypeLocalization type) {
        Function f = new Function(expression);
        Arrays.sort(interval);
        ArrayList<Double> nums = getFibonacciNumbers(interval, eps);
        int n = nums.size() - 1;

        boolean flag1 = true, flag2 = true;
        double x1 = 0, x2 = 0, f1 = 0, f2 = 0, a = interval[0], b = interval[1];
        double[] arg = new double[1];
        int k = 0;
        do {

            if (flag1 || flag2) {
                if (flag1) {
                    x1 = a + ((nums.get(n - k - 2) / nums.get(n - k)) * (b - a));
                    arg[0] = x1;
                    f1 = f.calculate(arg);
                    flag1 = false;
                }

                if (flag2) {
                    x2 = a + ((nums.get(n - k - 1) / nums.get(n - k)) * (b - a));
                    arg[0] = x2;
                    f2 = f.calculate(arg);
                    flag2 = false;
                }
            }

            if ((f1 > f2 && type == Function.TypeLocalization.MIN_LOCALIZATION)
                    || (f1 < f2 && type == Function.TypeLocalization.MAX_LOCALIZATION)) {
                a = x1;
                x1 = x2;
                f1 = f2;
                flag2 = true;
            } else if ((f1 <= f2 && type == Function.TypeLocalization.MIN_LOCALIZATION)
                    || (f1 >= f2 && type == Function.TypeLocalization.MAX_LOCALIZATION)) {
                b = x2;
                x2 = x1;
                f2 = f1;
                flag1 = true;
            }

            k++;
        } while (k + 1 < n - 2);

        return (a + b) / 2;
    }

    private static ArrayList<Double> getFibonacciNumbers(double[] interval, double eps) {
        ArrayList<Double> list = new ArrayList<>();
        list.add(1.0);
        list.add(1.0);

        int i = list.size();
        while (Math.abs(interval[1] - interval[0]) / list.get(i - 1) > eps) {
            i = list.size();
            list.add(list.get(i - 1) + list.get(i - 2));
        }

        return list;
    }
}
