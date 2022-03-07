package Lab1_Task2.code;

public class Bin2Dec {

    public static void main(String[] args) {
        final String errorArgument = "1 argument expected <bin number>";
        final String errorNumber = "Bin number is incorrect - expected <= 32 bit";

        String binNumber = args.length != 1 ? null : args[0];

        if (binNumber == null) {
            System.out.println(errorArgument);
            return;
        }

        if (isIncorrectBinNumber(binNumber)) {
            System.out.println(errorNumber);
        } else {
            System.out.println(convertToDecNumber(binNumber));
        }
    }

    static int convertToDecNumber(String binNumber) {
        int[] digits = convertToIntArray(binNumber);

        int decNumber = 0;

        for (int i = 0; i < digits.length; i++) {
            decNumber += digits[i] * Math.pow(2, digits.length - i - 1);
        }

        return decNumber;
    }

    static int[] convertToIntArray(String number) {
        int[] result = new int[number.length()];

        for (int i = 0; i < result.length; i++) {
            String ch = String.valueOf(number.charAt(i));
            result[i] = Integer.parseInt(ch);
        }

        return result;
    }

    static boolean isIncorrectBinNumber(String number) {
        boolean is32bitOrLess = number.length() <= 32;
        boolean isCorrect = true;

        for (int i = 0; i < number.length(); i++) {
            char digit = number.charAt(i);
            if (digit != '1' && digit != '0') {
                isCorrect = false;
                break;
            }
        }

        return !(is32bitOrLess && isCorrect);
    }

}
