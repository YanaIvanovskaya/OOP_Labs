package Lab1_Task2.code;

public class Bin2Dec {

    public static void main(String[] args) {
        String binNumber = args.length != 1 ? null : args[0];

        try {
            System.out.println(convertToDecNumber(binNumber));
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
    }

    static int convertToDecNumber(String binNumber) throws IllegalArgumentException {
        final String errorArgument = "1 argument expected <bin number>";
        final String errorNumber = "Bin number is incorrect - expected <= 32 bit";

        if (binNumber == null) {
            throw new IllegalArgumentException(errorArgument);
        }
        if (!isCorrectBinNumber(binNumber)) {
            throw new IllegalArgumentException(errorNumber);
        }

        int decNumber = 0;

        for (char ch : binNumber.toCharArray()) {
            int digit = ch == '0' ? 0 : 1;
            decNumber = (decNumber << 1) | digit;
        }

        return decNumber;
    }

    static boolean isCorrectBinNumber(String number) {
        boolean is32bitOrLess = number.length() <= 32;
        boolean patternMatch = number.matches("[1|0]+");
        return patternMatch && is32bitOrLess;
    }

}
