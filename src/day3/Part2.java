package day3;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Part2 {
    @Contract(pure = true)
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("src/day3/input.txt");
        Scanner sc = new Scanner(file);
        int sum = 0;
        ArrayList<String> lines = new ArrayList<>();
        while (sc.hasNextLine()) lines.add(sc.nextLine());
        for (int lineNum = 0; lineNum < lines.size(); lineNum++) {
            String currentLine = lines.get(lineNum);
            for (int charNum = 0; charNum < currentLine.length(); charNum++) {
                if (currentLine.charAt(charNum) != '*') continue;
                sum += getGearRatio(lines, lineNum, charNum);
            }
        }
        System.out.println(sum);
    }

    @Contract(pure = true)
    private static int getGearRatio(@NotNull ArrayList<String> lines, int lineNum, int charNum) {
        int count = 0;
        int ratio = 1;
        String currentLine = lines.get(lineNum);
        if (charNum > 0 && Part1.isNumber(lines.get(lineNum).charAt(charNum - 1))) {
            count++; ratio *= getNumber(currentLine, charNum - 1);
        }
        if (charNum < currentLine.length() - 1 && Part1.isNumber(currentLine.charAt(charNum + 1))) {
            count++; ratio *= getNumber(currentLine, charNum + 1);
        }
        if (lineNum > 0) {
            int gearCount = getLineGearNumberCount(lines.get(lineNum - 1), charNum);
            count += gearCount;
            ratio *= getLineRatio(lines.get(lineNum - 1), charNum, gearCount);
        }
        if (lineNum < lines.size() - 1) {
            int gearCount= getLineGearNumberCount(lines.get(lineNum + 1), charNum);
            count += gearCount;
            ratio *= getLineRatio(lines.get(lineNum + 1), charNum, gearCount);
        }
        return count == 2 ? ratio : 0;
    }

    private static int getLineRatio(String line, int charNum, int gearCount) {
        int ratio = 1;
        if (gearCount == 2) {
            ratio *= getNumber(line, charNum - 1);
            ratio *= getNumber(line, charNum + 1);
        }
        else {
            for (int i = charNum - 1; i <= charNum + 1; i++) if (Part1.isNumber(line.charAt(i))) {
                ratio *= getNumber(line, i);
                break;
            }
        }
        return ratio;
    }

    private static int getLineGearNumberCount(@NotNull String line, int gearIndex) {
        boolean first = gearIndex > 0 && Part1.isNumber(line.charAt(gearIndex - 1));
        boolean middle = Part1.isNumber(line.charAt(gearIndex));
        boolean last = gearIndex < line.length() - 1 && Part1.isNumber(line.charAt(gearIndex + 1));
        if (first && !middle && last) return 2;
        else if (first || middle || last) return 1;
        return 0;
    }

    private static int getNumber(@NotNull String line, int charNum) {
        StringBuilder string = new StringBuilder(String.valueOf(line.charAt(charNum)));
        for (int i = charNum - 1; i >= 0 && Part1.isNumber(line.charAt(i)); i--) string.append(line.charAt(i));
        string.reverse();
        for (int i = charNum + 1; i < line.length() - 1 && Part1.isNumber(line.charAt(i)); i++)
            string.append(line.charAt(i));
        return Integer.parseInt(string.toString());
    }
}
