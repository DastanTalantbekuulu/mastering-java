package com.mastering;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class FilterMega {

    public static void main(String[] args) throws IOException {
        Path path = Path.of("C:\\Users\\dastan\\Documents\\mega\\new\\mega24_555_2.csv");

        try (Stream<String> lines = Files.lines(path)) {
            List<String> list = lines.skip(1)
                    .map(line -> line.split(",")[0])
                    .filter(FilterMega::filter)
                    .toList();
//            Path.of("C:\\Users\\dastan\\Documents\\mega\\new\\favorites.csv");
            list.forEach(System.out::println);
            System.out.println(list.size());
        }
    }

    static Set<Character> set = Set.of('0', '1', '4', '5');

    //    static Set<Character> set = Set.of('0', '2', '4', '5', '6', '8');
//    static Set<Character> set = Set.of('0', '4', '5');
    static boolean filter(String num) {
        var flag = 0;
        char a1 = num.charAt(6);
        char a2 = num.charAt(7);
        char b1 = num.charAt(8);
        char b2 = num.charAt(9);
        char c1 = num.charAt(10);
        char c2 = num.charAt(11);
//        var charset = new HashSet<>(Arrays.asList(a1, a2, b1, b2, c1, c2));
//        if (!set.containsAll(charset)) return false;
//        return (c1 =='5' && c2=='5') && (a1 == a2 && set.contains(a1) || b1 == b2 && set.contains(b1));
//        return (set.contains(a1) && set.contains(b1) && set.contains(c1)) && c2 - b2 == 1 && b2 - a2 == 1;
//        return c1 - b1 == 1 && b1 - a1 == 1;// && c2 - b2 == 1 && b2 - a2 == 1;
//        return  a2 =='5' && b2=='5' && c2=='5';
//        return a2==b1 && set.contains(a1) && b2 == c1 && set.contains(b2);
//        return a2=='0' && b2=='0' && c1 == c2;// && c1=='1';

        if (a1 == a2 && set.contains(a1)) flag++;
        if (b1 == b2 && set.contains(b1)) flag++;
        if (c1 == c2 && set.contains(c1)) flag++;
//        var zero = 0;
//        if (a1 == '0') zero++;
//        if (a2 == '0') zero++;
//        if (b1 == '0') zero++;
//        if (b2 == '0') zero++;
//        if (c1 == '0') zero++;
//        if (c2 == '0') zero++;
//        if (zero >= 3) {
//            return true;
//        }
        if (flag >= 2) {
            return true;
        }
        return false;
    }
}
