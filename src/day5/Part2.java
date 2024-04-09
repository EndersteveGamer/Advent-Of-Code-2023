package day5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Part2 {
    public static void main(String[] args) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        File file = new File("src/day5/input.txt");
        Scanner sc = new Scanner(file);
        ArrayList<String> lines = new ArrayList<>();
        while (sc.hasNextLine()) lines.add(sc.nextLine());
        ArrayList<Long> seeds = Part1.getSeeds(lines);
        long min = Part1.getMinLocation(new ArrayList<>(List.of(seeds.get(0))), lines);
        for (int i = 0; i < seeds.size(); i += 2) {
            long start = seeds.get(i);
            long range = seeds.get(i + 1);
            for (long j = start; j <= start + range; j++) {
                ArrayList<Long> seed = new ArrayList<>();
                for (long k = j; k < j + 1000 && k < start + range; k++) seed.add(k);
                long tempMin = Part1.getMinLocation(seed, lines);
                min = Math.min(min, tempMin);
            }
        }
        System.out.println(min);
        System.out.println(System.currentTimeMillis() - startTime);
    }
}
