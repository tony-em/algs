package algs.max_likelihood;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MaxLikelihood {

    public static ArrayList<String> getXORCodesCombinations(ArrayList<Integer> codes) {
        codes = getXORCodesCombinations(codes, codes.size());

        return correctCapacityCodes(codes, getCapacityCode(codes));
    }

    private static ArrayList<Integer> getXORCodesCombinations(ArrayList<Integer> codes, int n) {
        int size = codes.size();
        for (int i = 0; i < codes.size(); i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    continue;
                }

                int code = codes.get(i) ^ codes.get(j);
                if (!codes.contains(code)) codes.add(code);
            }
        }

        if (codes.size() == size) return codes;
        else return getXORCodesCombinations(codes, n);
    }

    public static ArrayList<String> codesAndCodeXOR(ArrayList<Integer> codes, int code) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int c : codes) {
            list.add(c ^ code);
        }

        return correctCapacityCodes(list, getCapacityCode(list));
    }

    private static ArrayList<String> correctCapacityCodes(ArrayList<Integer> codes, int capacity) {
        ArrayList<String> strCodes = new ArrayList<>();
        for (int code : codes) {
            String str = Integer.toBinaryString(code);

            if (str.length() < capacity) {
                int length = capacity - str.length();
                for (int i = 0; i < length; i++) {
                    str = "0".concat(str);
                }
            }

            strCodes.add(str);
        }

        return strCodes;
    }

    public static int getMinCodeDistance(ArrayList<String> codes) {
        ArrayList<String> list = new ArrayList<>(codes);
        list.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                String[] str1 = o1.split("");
                String[] str2 = o2.split("");

                int c1 = 0;
                int c2 = 0;
                for (int i = 0; i < str1.length; i++) {
                    if (str1[i].equals("1"))
                        c1++;
                    if (str2[i].equals("1"))
                        c2++;
                }

                if (c1 > c2) return 1;
                else if (c1 < c2) return -1;
                else return 0;
            }
        });

        int count = 0;
        String[] s = list.get(0).split("");
        for (int i = 0; i < s.length; i++) {
            if (s[i].equals("1"))
                count++;
        }

        System.out.println(list);

        return count;
    }

    public static int getCapacityCode(ArrayList<Integer> codes) {
        return Integer.toBinaryString(Collections.max(codes)).length();
    }
}
