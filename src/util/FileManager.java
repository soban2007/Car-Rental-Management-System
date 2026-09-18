package util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    public static List<String> readLines(String filePath) {

        List<String> lines = new ArrayList<>();

        File file = new File(filePath);

        if (!file.exists()) {

            try {

                file.getParentFile().mkdirs();

                file.createNewFile();

            } catch (IOException e) {

                e.printStackTrace();

            }

            return lines;

        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = br.readLine()) != null) {

                if (!line.trim().isEmpty()) {

                    lines.add(line);

                }

            }

        } catch (IOException e) {

            e.printStackTrace();

        }

        return lines;

    }

    public static void writeLines(String filePath, List<String> lines) {

        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath, false))) {

            for (String line : lines) {

                pw.println(line);

            }

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

    // NEW METHOD
    public static void appendLine(String filePath, String line) {

        File file = new File(filePath);

        try {

            if (!file.exists()) {

                file.getParentFile().mkdirs();

                file.createNewFile();

            }

            PrintWriter pw = new PrintWriter(new FileWriter(file, true));

            pw.println(line);

            pw.close();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

}