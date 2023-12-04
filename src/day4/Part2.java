package day4;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Part2 {
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("src/day4/input.txt");
        Scanner sc = new Scanner(file);
        ArrayList<String> lines = new ArrayList<>();
        while (sc.hasNextLine()) lines.add(sc.nextLine());
        int sum = 0;
        HashMap<Integer, Integer> cache = new HashMap<>();
        for (int i = 0; i < lines.size(); i++) sum += getCardCount(lines, cache, i);
        System.out.println(sum);
    }

    private static int getCardCount(@NotNull ArrayList<String> lines, HashMap<Integer, Integer> cache, int cardIndex) {
        if (cardIndex >= lines.size()) return 0;
        if (cache.containsKey(cardIndex)) return cache.get(cardIndex);
        int count = 1;
        ArrayList<ArrayList<Integer>> processed = Part1.processLine(lines.get(cardIndex));
        int winningCount = 0;
        for (Integer number : processed.get(1)) if (processed.get(0).contains(number)) winningCount++;
        for (int i = 0; i < winningCount; i++) count += getCardCount(lines, cache, cardIndex + i + 1);
        cache.put(cardIndex, count);
        return count;
    }
}
