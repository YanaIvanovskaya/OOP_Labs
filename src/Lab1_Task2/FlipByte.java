package Lab1_Task2;

public class FlipByte {

    public static void main(String[] args) {

        byte number = 6;//Integer.parseInt(args[0]);
        String binary = Integer.toBinaryString(number);
        System.out.println(binary);
//
//        System.out.println("number << 1 " + Integer.toBinaryString(number << 1));
//        System.out.println("number >> 1 " + Integer.toBinaryString(number >> 1));

//        System.out.println(0b110);
        for (int i = 0; i < 8; i++) {
            number >>= 1;
            System.out.println("number >>= 1  " + Integer.toBinaryString(number));
            number ^= (byte) ~number;
            System.out.println("number ~  " + Integer.highestOneBit(number));
            System.out.println("");
        }

    }


    static boolean isValidNumberArgument(String[] args) {
        if (args.length != 1) {
            return false;
        }
        return args[0].matches("([0-9]{3})");
    }

}
