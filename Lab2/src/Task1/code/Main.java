package Task1.code;

import java.text.DecimalFormat;
import java.util.Vector;

public class Main {

    public static void main(String[] args) {
        final String errorArgument =
                "Invalid sequence: expected <number> <number> <number> ...";

        Vector<Double> doubles = getDoubleVectorOrNull(args);

        if (doubles == null) {
            System.out.println(errorArgument);
            return;
        }

        Vector<Double> result = transformAs8Variant(doubles);
        printResult(result);
    }

    private static void printResult(Vector<Double> doubles) {
        DecimalFormat formatter = new DecimalFormat("#.###");

        doubles.sort(null);

        for (Double num : doubles) {
            System.out.print(formatter.format(num));
            if (!num.equals(doubles.lastElement())) System.out.print(" ");
        }
    }

    private static Vector<Double> getDoubleVectorOrNull(String[] args) {
        if (args.length == 0) return null;

        Vector<Double> list = new Vector<>();

        for (String arg : args) {
            try {
                double number = Double.parseDouble(arg);
                list.add(number);
            } catch (NumberFormatException ex) {
                return null;
            }
        }

        return list;
    }

    private static Vector<Double> transformAs8Variant(Vector<Double> doubles) {
        double sumOfPositiveNumbers = 0;

        for (double num : doubles) {
            if (num > 0) sumOfPositiveNumbers += num;
        }

        for (int i = 0; i < doubles.size(); i++) {
            if (i % 2 == 0)
                doubles.setElementAt(doubles.get(i) * 2, i);
            else
                doubles.setElementAt(doubles.get(i) - sumOfPositiveNumbers, i);
        }

        return doubles;
    }

}
