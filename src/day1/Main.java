package day1;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final ArrayList<String> numbers = new ArrayList<>(List.of(
                    "zero",
                    "one",
                    "two",
                    "three",
                    "four",
                    "five",
                    "six",
                    "seven",
                    "eight",
                    "nine"
    ));

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("C:\\Users\\theop\\OneDrive\\Bureau\\Pour coder" +
                "\\Fichiers Java\\Advent Of Code\\src\\day1\\input.txt");
        Scanner sc = new Scanner(file);
        int sum = 0;
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            for (int i = 0; i < line.length(); i++) {
                int num = getNumber(i, line);
                if (num == -1) continue;
                sum += num * 10;
                break;
            }
            for (int i = line.length() - 1; i >= 0; i--) {
                int num = getNumber(i, line);
                if (num == -1) continue;
                sum += num;
                break;
            }
        }
        System.out.println(sum);
    }

    private static int getNumber(int index, @NotNull String str) {
        return charToInt(str.charAt(index)) != -1 ? charToInt(str.charAt(index)) : getStringNumber(index, str);
    }

    @Contract(pure = true)
    private static int charToInt(char chr) {return chr < '0' || chr > '9' ? -1 : chr - '0';}

    private static int getStringNumber(int index, String str) {
        for (int i = 0; i < numbers.size(); i++) {
            try {
                String sub = str.substring(index, index + numbers.get(i).length());
                if (sub.equals(numbers.get(i))) return i;
            }
            catch (IndexOutOfBoundsException ignored) {}
        }
        return -1;
    }
}
