package day5;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Part1 {
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("src/day5/input.txt");
        Scanner sc = new Scanner(file);
        ArrayList<String> lines = new ArrayList<>();
        while (sc.hasNextLine()) lines.add(sc.nextLine());
        ArrayList<Long> seeds = getSeeds(lines);
        System.out.println(getMinLocation(seeds, lines));
    }

    protected static long getMinLocation(@NotNull ArrayList<Long> seeds, ArrayList<String> lines) {
        ArrayList<ArrayList<String>> stringMaps = getMaps(lines);
        ArrayList<ArrayList<MapRecord>> maps = getMapRecords(stringMaps);
        ArrayList<Long> locations = new ArrayList<>();
        for (Long seed : seeds) {
            for (ArrayList<MapRecord> map : maps) {
                seed = applyMaps(seed, map);
            }
            locations.add(seed);
        }
        long min = locations.get(0);
        for (int i = 1; i < locations.size(); i++) if (locations.get(i) < min) min = locations.get(i);
        return min;
    }

    protected static @NotNull ArrayList<Long> getSeeds(@NotNull ArrayList<String> lines) {
        lines.remove(1);
        String seedsStr = lines.remove(0);
        ArrayList<Long> seeds = new ArrayList<>();
        seedsStr = seedsStr.replaceAll(": ", ":");
        seedsStr = seedsStr.split(":")[1];
        for (String seed : seedsStr.split(" ")) seeds.add(Long.parseLong(seed));
        return seeds;
    }

    private static @NotNull ArrayList<ArrayList<String>> getMaps(@NotNull ArrayList<String> lines) {
        lines.removeIf(l -> l.contains(":"));
        ArrayList<ArrayList<String>> maps = new ArrayList<>();
        int mapIndex = 0;
        int lineIndex = 0;
        while (lineIndex < lines.size()) {
            if (mapIndex == maps.size()) maps.add(new ArrayList<>());
            if (lines.get(lineIndex).isEmpty()) {
                mapIndex++;
                lineIndex++;
                continue;
            }
            maps.get(mapIndex).add(lines.get(lineIndex));
            lineIndex++;
        }
        return maps;
    }

    private static @NotNull ArrayList<ArrayList<MapRecord>> getMapRecords(@NotNull ArrayList<ArrayList<String>> maps) {
        ArrayList<ArrayList<MapRecord>> mapRecords = new ArrayList<>();
        for (ArrayList<String> map : maps) {
            ArrayList<MapRecord> records = new ArrayList<>();
            for (String line : map) {
                String[] split = line.split(" ");
                records.add(new MapRecord(split));
            }
            mapRecords.add(records);
        }
        return mapRecords;
    }

    @Contract(pure = true)
    private static @NotNull MapResult applyMapRecord(long num, @NotNull MapRecord record) {
        if (num < record.sourcePoint) return new MapResult(num, false);
        if (num >= record.sourcePoint + record.length) return new MapResult(num, false);
        if (record.destPoint > record.sourcePoint) return new MapResult(num + (record.destPoint - record.sourcePoint), true);
        else return new MapResult(num - (record.sourcePoint - record.destPoint), true);
    }

    @Contract(pure = true)
    private static long applyMaps(long num, @NotNull ArrayList<MapRecord> map) {
        for (MapRecord record : map) {
            MapResult result = applyMapRecord(num, record);
            num = result.result;
            if (result.applied) break;
        }
        return num;
    }

    private static class MapRecord {
        long destPoint;
        long sourcePoint;
        long length;

        @Contract(pure = true)
        public MapRecord(long destPoint, long sourcePoint, long length) {
            this.destPoint = destPoint;
            this.sourcePoint = sourcePoint;
            this.length = length;
        }

        public MapRecord(String @NotNull [] split) {
            this.destPoint = Long.parseLong(split[0]);
            this.sourcePoint = Long.parseLong(split[1]);
            this.length = Long.parseLong(split[2]);
        }

        @Contract(pure = true)
        @Override
        public @NotNull String toString() {
            return this.destPoint + " " + this.sourcePoint + " " + this.length;
        }
    }

    private record MapResult(long result, boolean applied){}
}
