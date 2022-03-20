package Lab1_Task2.code;

public class Bin2Dec {

    public static void main(String[] args) {
        String binNumber = args.length != 1 ? null : args[0];

        try {
            System.out.println(convertToNumber(binNumber));
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
    }

    static int convertToNumber(String binNumber) throws IllegalArgumentException {
        if (binNumber == null) {
            throw new IllegalArgumentException("1 argument expected <bin number>");
        }
        if (!isCorrectBinNumber(binNumber)) {
            throw new IllegalArgumentException("Bin number is incorrect - expected <= 32 bit");
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
