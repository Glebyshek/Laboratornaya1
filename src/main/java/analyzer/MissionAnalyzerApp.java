package analyzer;
import analyzer.display.MissionFormatter;
import analyzer.model.Mission;
import analyzer.parser.MissionParser;
import analyzer.util.FormatDetector;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Scanner;

public class MissionAnalyzerApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Анализатор миссий магов ===");
        System.out.println();
        String filePath;
        if (args.length > 0) {
            filePath = args[0];
        } else {
            System.out.print("Введите путь к файлу миссии: ");
            filePath = scanner.nextLine().trim();
        }
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("Ошибка: файл не найден - " + filePath);
            return;
        }
        try {
            String content = new String(
                Files.readAllBytes(file.toPath()),
                StandardCharsets.UTF_8
            );
            FormatDetector.Format format = FormatDetector.detect(file.getName(), content);
            System.out.println("Формат файла: " + format);
            System.out.println();
            MissionParser parser = FormatDetector.getParser(format);
            Mission mission = parser.parse(content);
            String report = MissionFormatter.format(mission);
            System.out.println(report);
        } catch (Exception e) {
            System.out.println("Ошибка при обработке файла: " + e.getMessage());
        }
    }
}
