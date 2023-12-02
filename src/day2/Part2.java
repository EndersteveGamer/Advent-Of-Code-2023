package day2;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Part2 {
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("src/day2/input.txt");
        Scanner sc = new Scanner(file);
        int sum = 0;
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            line = Part1.formatLine(line);
            ArrayList<Integer> cubeNum = new ArrayList<>(3);
            for (int i = 0; i < 3; i++) cubeNum.add(0);
            for (String rolls : line.split(";")) {
                for (String cube : rolls.split(",")) {
                    String[] split = cube.split(" ");
                    int colorIndex = getColorIndex(split[1]);
                    cubeNum.set(colorIndex, Math.max(cubeNum.get(colorIndex), Integer.parseInt(split[0])));
                }
            }
            sum += cubeNum.get(0) * cubeNum.get(1) * cubeNum.get(2);
        }
        System.out.println(sum);
    }

    private static int getColorIndex(@NotNull String color) {
        return color.equals("red") ? 0 : color.equals("green") ? 1 : 2;
    }
}
