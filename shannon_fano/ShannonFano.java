package algs.shannon_fano;

import algs.Symbol;

import java.util.ArrayList;
import java.util.Collections;

public class ShannonFano {

    public static ArrayList<Symbol> startShannonFano(ArrayList<Symbol> weightsList) {
        ArrayList<Symbol> result = new ArrayList<>(weightsList);
        Collections.sort(result);

        dividingAndCodingShannonFano(result);

        return result;
    }

    private static void dividingAndCodingShannonFano(ArrayList<Symbol> weightsList) {
        if (weightsList.size() == 1 || weightsList.size() == 0) return;

        double sum1 = 0;
        double sum2 = 0;
        if (weightsList.size() % 2 != 0) {
            sum2 = weightsList.get(weightsList.size() - 1).getWeight();
        }

        int endIndex = 0;
        for (int i = 0; i < weightsList.size() / 2; i++) {
            sum1 += weightsList.get(i).getWeight();

            if (weightsList.size() % 2 != 0) {
                sum2 += weightsList.get(weightsList.size() - i - 2).getWeight();
            } else
                sum2 += weightsList.get(weightsList.size() - i - 1).getWeight();

            endIndex = i;
        }

        double oldSub = 0;
        double newSub = 0;
        while (sum2 > sum1) {
            oldSub = Math.abs(sum1 - sum2);
            sum1 += weightsList.get(++endIndex).getWeight();
            sum2 -= weightsList.get(endIndex).getWeight();
            newSub = Math.abs(sum1 - sum2);
        }
        if (oldSub < newSub) --endIndex;

        for (int i = 0; i < weightsList.size(); i++) {
            Symbol s = weightsList.get(i);

            if (i <= endIndex) {
                s.setCode(s.getCode().concat("1"));
            } else {
                s.setCode(s.getCode().concat("0"));
            }
        }

        dividingAndCodingShannonFano(new ArrayList<>(weightsList.subList(0, ++endIndex)));
        dividingAndCodingShannonFano(new ArrayList<>(weightsList.subList(endIndex, weightsList.size())));
    }
}
