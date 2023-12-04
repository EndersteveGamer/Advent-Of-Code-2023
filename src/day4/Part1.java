package day4;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Part1 {
    @Contract(pure = true)
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("src/day4/input.txt");
        Scanner sc = new Scanner(file);
        int sum = 0;
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            ArrayList<ArrayList<Integer>> processed = processLine(line);
            ArrayList<Integer> winning = processed.get(0);
            ArrayList<Integer> numbers = processed.get(1);
            int winningCount = 0;
            for (Integer number : numbers) {
                if (!winning.contains(number)) continue;
                winningCount++;
            }
            if (winningCount != 0) sum += (int) Math.pow(2, winningCount - 1);
        }
        System.out.println(sum);
    }

    @Contract(pure = true)
    protected static @NotNull ArrayList<ArrayList<Integer>> processLine(@NotNull String line) {
        line = line.replaceAll("  ", " ");
        line = line.replaceAll(" \\| ", "|");
        line = line.replaceAll(": ", ":");
        line = line.split(":")[1];
        ArrayList<ArrayList<Integer>> result = new ArrayList<>(2);
        result.add(new ArrayList<>()); result.add(new ArrayList<>());
        for (String winning : line.split("\\|")[0].split(" ")) {
            result.get(0).add(Integer.parseInt(winning));
        }
        for (String number : line.split("\\|")[1].split(" ")) {
            result.get(1).add(Integer.parseInt(number));
        }
        return result;
    }
}
