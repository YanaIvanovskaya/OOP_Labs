package Lab1_Task2.code;

import java.util.Arrays;

public class Bin2Dec {

    public static void main(String[] args) {
        String input = "110010000"; //400
        int number = 0;
        System.out.println(Arrays.toString(convertToIntArray(input)));

    }

    static int[] convertToIntArray(String number) {
        int[] result = new int[number.length()];

        for (int i = 0; i < result.length; i++) {
            String ch = String.valueOf(number.charAt(i));
            result[i] = Integer.parseInt(ch);
        }

        return result;
    }


}
