package day2;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Part1 {
    @Contract(pure = true)
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("src/day2/input.txt");
        Scanner sc = new Scanner(file);
        int sum = 0;
        int gameIndex = 0;
        games: while (sc.hasNextLine()) {
            gameIndex++;
            String line = sc.nextLine();
            line = formatLine(line);
            for (String rolls : line.split(";")) {
                String[] cubes = rolls.split(",");
                for (String cube : cubes) if (!isPossible(cube)) continue games;
            }
            sum += gameIndex;
        }
        System.out.println(sum);
    }

    protected static String formatLine(String line) {
        line = line.replaceAll(": ", ":");
        line = line.replaceAll("; ", ";");
        line = line.replaceAll(", ", ",");
        return line.split(":")[1];
    }

    private static boolean isPossible(@NotNull String cube) {
        String[] split = cube.split(" ");
        int cubeNum = Integer.parseInt(split[0]);
        return switch (split[1]) {
            case "green" -> cubeNum <= 13;
            case "blue" -> cubeNum <= 14;
            default -> cubeNum <= 12;
        };
    }
}
