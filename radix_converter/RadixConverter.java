package algs.radix_converter;

import sun.misc.Queue;
import java.util.Stack;

public abstract class RadixConverter {

    private static final String[] CHARS =
            {
                    "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
                    "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
            };

    private static final byte[] NUMS =
            {
                    10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22,
                    23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35
            };

    // не более 12
    private static final int EPS = 12;

    private static boolean negativeFlag;


    public static String convert(String number, int radixFrom, int radixTo) {
        if (radixFrom < 2 || radixTo < 2 || radixFrom > 36 || radixTo > 36)
            throw new NumberFormatException("The base should be between 2 and 36...");

        if (radixFrom == radixTo)
            return number;

        if (number.startsWith("-")) {
            number = number.replaceAll("-","");
            negativeFlag = true;
        }

        String[] numberParts = number.split("\\.");

        if (numberParts.length > 2)
            throw new NumberFormatException("Invalid number format or non-conformance number and radix...");

        String resultNum = parseNumber(numberParts, radixFrom, radixTo);

        if (negativeFlag) {
            resultNum = "-".concat(resultNum);
            negativeFlag = false;
        }

        return resultNum;
    }

    private static String parseNumber(String[] numParts, int radixFrom, int radixTo) {
        try {
            if (numParts.length == 1) {
                return parseDecIntPartTo(parseIntPartToDec(numParts[0], radixFrom), radixTo);
            } else {
                String result = parseDecIntPartTo(parseIntPartToDec(numParts[0], radixFrom), radixTo);

                try {
                    if (Integer.parseInt(numParts[1]) == 0)
                        return result;
                    else
                        return result.concat("." + parseDecFractPartTo(parseFractPartToDec(numParts[1], radixFrom), radixTo));
                } catch (Exception e) {
                    return result.concat("." + parseDecFractPartTo(parseFractPartToDec(numParts[1], radixFrom), radixTo));
                }
            }
        } catch (Exception e) {
            throw new NumberFormatException("Invalid number format or non-conformance number and radix...");
        }
    }


    // перевод целой части числа из десятичной в произвольную систему
    private static String parseDecIntPartTo(String decIntPart, int radixTo) throws Exception {
        if (radixTo == 10) return decIntPart;

        long num = Long.parseLong(decIntPart);

        Stack<String> stack = new Stack<>();
        long val;
        do {
            val = num / radixTo;
            stack.push(String.valueOf(num % radixTo));
            num = val;

        } while (val >= radixTo); // || val == 1

        if (val != 0)
            stack.push(String.valueOf(val));

        String[] digits = new String[stack.size()];
        for (int i = 0; i < digits.length; i++) {
            digits[i] = stack.pop();
        }

        if (!stack.isEmpty())
            throw new NullPointerException("Stack is not empty...");

        if (radixTo > 10)
            digits = replaceNumbersAtChars(digits, radixTo);

        return concat(digits);
    }

    // перевод дробной части числа из десятичной в произвольную систему
    private static String parseDecFractPartTo(String fractIntPart, int radixTo) throws Exception {
        double num = Double.parseDouble(fractIntPart);

        Queue<String> queue = new Queue<>();
        int i = 0;
        long val = (long) num;

        // можно повысить точность вычисления - для этого нужно оптимизировать или переделать цикл
        // и сразу же на каждой итерации если нужно парсить символ из целой части, а целую
        // часть произведения заменять на значение символа в СИ если это нужно
        // тогда нужно будет модифицировать и метод перевода в десятичную систему, а именно
        // сделать большую точность вычисления чтобы не терять большие дробные части
        while (i < EPS && num != val) {
            num *= radixTo;
            val = (long) num;

            if (val == radixTo) {
                queue.enqueue(String.valueOf(0));
            }
            else if (val < radixTo) {
                queue.enqueue(String.valueOf(val));
            }
            else {
                queue.enqueue(String.valueOf(val % radixTo));
            }

            i++;
        }

        String[] digits;
        try {
            digits = new String[i];
            for (int j = 0; j < digits.length; j++) {
                digits[j] = queue.dequeue();
            }
        } catch (Exception e) {
            throw new NullPointerException("Queue NullPointerException...");
        }

        if (radixTo > 10)
            digits = replaceNumbersAtChars(digits, radixTo);

        return concat(digits);
    }

    private static String[] replaceNumbersAtChars(String[] digits, int radixTo) {
        int endIndex = radixTo - 10 - 1;

        for (int i = 0; i <= endIndex; i++) {
            for (int j = 0; j < digits.length; j++) {
                if (digits[j].equalsIgnoreCase(String.valueOf(NUMS[i]))) {
                    digits[j] = CHARS[i];
                }
            }
        }

        return digits;
    }

    private static String concat(String[] strings) {
        String str = "";

        for (String s : strings) {
            str = str.concat(s);
        }

        return str;
    }


    // перевод целой части числа из произвольной системы в десятичную
    private static String parseIntPartToDec(String intPart, int radixFrom) throws Exception {
        String[] digits = intPart.split("");

        if (radixFrom > 10)
            digits = replaceCharsAtNumbers(digits, radixFrom);
        else if (radixFrom == 10)
            return intPart;

        long number = 0;
        for (int i = 0; i < digits.length; i++) {
            byte val = Byte.parseByte(digits[i]);

            if (val >= radixFrom)
                throw new NumberFormatException("Invalid number format or non-conformance number and radix...");

            number += Byte.parseByte(digits[i]) * Math.pow(radixFrom, digits.length - (i + 1));
        }

        return String.valueOf(number);
    }

    /// перевод дробной части числа из произвольной системы в десятичную
    private static String parseFractPartToDec(String fractPart, int radixFrom) throws Exception {
        String[] digits = fractPart.split("");

        if (radixFrom > 10)
            digits = replaceCharsAtNumbers(digits, radixFrom);
        else if (radixFrom == 10)
            return "0.".concat(fractPart);

        double number = 0;
        for (int i = 0; i < digits.length; i++) {
            byte val = Byte.parseByte(digits[i]);

            if (val >= radixFrom)
                throw new NumberFormatException("Invalid number format or non-conformance number and radix...");

            number += Byte.parseByte(digits[i]) * Math.pow(radixFrom, -(i + 1));
        }

        return String.valueOf(number);
    }

    private static String[] replaceCharsAtNumbers(String[] digits, int radixFrom) {
        int endIndex = radixFrom - 10 - 1;

        for (int i = 0; i <= endIndex; i++) {
            for (int j = 0; j < digits.length; j++) {
                if (digits[j].equalsIgnoreCase(CHARS[i])) {
                    digits[j] = String.valueOf(NUMS[i]);
                }
            }
        }

        return digits;
    }
}