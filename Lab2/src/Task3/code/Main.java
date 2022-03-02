package Task3.code;

import java.util.*;

public class Main {

    //Вариант 4 – Генератор простых чисел – 100 баллов
    //Разработайте функцию std::set<int> GeneratePrimeNumbersSet(int upperBound),
    // возвращающую множество всех простых чисел, не превышающих значения upperBound.
    //С ее использованием разработайте программу, выводящую в стандартный поток вывода
    // элементы множества простых чисел, не превышающие значения, переданного приложению
    // через обязательный параметр командной строки.
    //Максимальное значение верхней границы множества принять равным 100 миллионам.
    //Время построения множества простых чисел в указанном диапазоне на компьютере с
    // 2GHz-процессором не должно превышать 10-12 секунд (программа будет запускаться в Release-конфигурации).
    //Примечание: наивный поиск простых чисел не позволит добиться указанной производительности.
    // Используйте «Решето Эратосфена». Для предварительного просеивания воспользуйтесь vector<bool>
    // (для хранения каждого элемента он использует 1 бит информации, т.к. на хранение признака простоты
    // 100 миллионов чисел потребуется всего 12,5 мегабайт памяти)
    //Для проверки программы используйте тот факт, что в диапазоне от 1 до 100000000 содержится 5761455
    // простых чисел.

    public static void main(String[] args) {

        int upperBound = 100000000;
//        HashSet<Integer> primes = generatePrimeNumbersSet(upperBound);
//        System.out.println(primes);
    }

    private static HashSet<Integer> generatePrimeNumbersSet(int upperBound) {
        long startTime = System.currentTimeMillis();
        HashSet<Integer> primes = new HashSet<>();
        List<Integer> list = new ArrayList<>(upperBound);

        for (int i = 2; i <= upperBound; i++) {
            list.add(i);
        }

        int counter = 2;
        int deletedNumbers = 1;

        Iterator<Integer> iter = list.iterator();
        while (deletedNumbers > 0) {
            deletedNumbers = 0;
            System.out.println(counter);
            while (iter.hasNext()) {
                int number = iter.next();
                if (number % counter == 0 && number != counter) {
                    list.set(list.indexOf(number), null);
                    System.out.println(number);
                    deletedNumbers++;
                }
            }
            counter++;
        }
        list = list.stream().filter(Objects::nonNull).toList();
        System.out.println(list);
        assert list.size() == 5761455;
        long sec = (System.currentTimeMillis() - startTime) / 1000;
        System.out.println("Time: " + sec);
        return primes;
    }


}
