package day3;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Part1 {
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("src/day3/input.txt");
        Scanner sc = new Scanner(file);
        int sum = 0;
        ArrayList<String> lines = new ArrayList<>();
        while (sc.hasNextLine()) lines.add(sc.nextLine());
        for (int i = 0; i < lines.size(); i++) {
            String currentLine = lines.get(i);
            characters: for (int j = 0; j < currentLine.length(); j++) {
                if (!isNumber(currentLine.charAt(j))) continue;
                int numLength = getNumberLength(currentLine, j);
                for (int currentChar = 0; currentChar < numLength; currentChar++) {
                    if (isNextToSymbol(lines, i, j + currentChar)) {
                        sum += getNumber(currentLine, j);
                        j += numLength;
                        continue characters;
                    }
                }
            }
        }
        System.out.println(sum);
    }

    @Contract(pure = true)
    protected static boolean isNumber(char chr) {return chr >= '0' && chr <= '9';}

    private static int getNumberLength(@NotNull String line, int index) {
        int length = 1;
        while (index + length < line.length() && isNumber(line.charAt(index + length))) length++;
        return length;
    }

    private static boolean isNextToSymbol(ArrayList<String> lines, int lineIndex, int charIndex) {
        for (int line = lineIndex - 1; line <= lineIndex + 1; line++) {
            if (line < 0 || line >= lines.size()) continue;
            for (int chr = charIndex - 1; chr <= charIndex + 1; chr++) {
                if (chr < 0 || chr >= lines.get(line).length()) continue;
                char currentChar = lines.get(line).charAt(chr);
                if (!isNumber(currentChar) && currentChar != '.') return true;
            }
        }
        return false;
    }

    private static int getNumber(String line, int charIndex) {
        StringBuilder string = new StringBuilder();
        int numLength = getNumberLength(line, charIndex);
        for (int i = 0; i < numLength; i++) string.append(line.charAt(charIndex + i));
        return Integer.parseInt(string.toString());
    }
}
