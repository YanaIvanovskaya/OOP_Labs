package Lab1_Task2.code;

public class Bin2Dec {

    private static final String ERROR_ARGUMENT = "1 argument expected <bin number>";
    private static final String ERROR_NUMBER = "Bin number is incorrect - expected <= 32 bit";

    public static void main(String[] args) {
        String binNumber = getArgumentOrNull(args);

        if (binNumber == null) {
            System.out.println(ERROR_ARGUMENT);
            return;
        }

        if (!isValidBinNumber(binNumber)) {
            System.out.println(ERROR_NUMBER);
            return;
        }

        System.out.println(convertToDecNumber(binNumber));
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


    static String getArgumentOrNull(String[] args) {
        if (args.length != 1) {
            return null;
        }
        return args[0];
    }

    static boolean isValidBinNumber(String number) {
        boolean is32bitOrLess = number.length() <= 32;
        boolean isCorrect = true;

        for (int i = 0; i < number.length(); i++) {
            char digit = number.charAt(i);
            if (!(digit == '1' || digit == '0')) {
                isCorrect = false;
                break;
            }
        }

        return is32bitOrLess && isCorrect;
    }

}
