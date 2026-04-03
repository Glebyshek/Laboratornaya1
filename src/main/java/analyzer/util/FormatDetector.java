package analyzer.util;

import analyzer.parser.JsonMissionParser;
import analyzer.parser.MissionParser;
import analyzer.parser.TxtMissionParser;
import analyzer.parser.XmlMissionParser;

/**
 * Утилита для определения формата файла миссии.
 *
 * Определяет формат двумя способами:
 * 1. По расширению файла (.txt, .json, .xml)
 * 2. По содержимому файла (анализ первых символов)
 *
 * Это позволяет корректно обрабатывать даже файлы
 * без расширения или с неправильным расширением.
 */
public class FormatDetector {

    /**
     * Перечисление поддерживаемых форматов.
     */
    public enum Format {
        TXT, JSON, XML, UNKNOWN
    }

    /**
     * Определяет формат по расширению имени файла.
     *
     * @param fileName имя файла (например, "mission.json")
     * @return определённый формат
     */
    public static Format detectByExtension(String fileName) {
        String lower = fileName.toLowerCase();
        if (lower.endsWith(".json")) return Format.JSON;
        if (lower.endsWith(".xml"))  return Format.XML;
        if (lower.endsWith(".txt"))  return Format.TXT;
        return Format.UNKNOWN;
    }

    /**
     * Определяет формат по содержимому файла.
     * Анализирует первый непробельный символ:
     *   '{' → JSON
     *   '<' → XML
     *   иначе → TXT (ключ: значение)
     *
     * @param content содержимое файла
     * @return определённый формат
     */
    public static Format detectByContent(String content) {
        String trimmed = content.trim();
        if (trimmed.startsWith("{")) return Format.JSON;
        if (trimmed.startsWith("<")) return Format.XML;
        if (trimmed.contains(":"))  return Format.TXT;
        return Format.UNKNOWN;
    }

    /**
     * Определяет формат, используя оба подхода.
     * Сначала проверяет расширение, при неудаче — содержимое.
     *
     * @param fileName имя файла
     * @param content  содержимое файла
     * @return определённый формат
     */
    public static Format detect(String fileName, String content) {
        Format byExt = detectByExtension(fileName);
        if (byExt != Format.UNKNOWN) return byExt;
        return detectByContent(content);
    }

    /**
     * Возвращает подходящий парсер для заданного формата.
     *
     * @param format формат файла
     * @return парсер, реализующий интерфейс MissionParser
     * @throws IllegalArgumentException если формат не поддерживается
     */
    public static MissionParser getParser(Format format) {
        switch (format) {
            case JSON: return new JsonMissionParser();
            case XML:  return new XmlMissionParser();
            case TXT:  return new TxtMissionParser();
            default:
                throw new IllegalArgumentException(
                    "Неизвестный формат файла. Поддерживаются: TXT, JSON, XML."
                );
        }
    }
}
