package analyzer.util;
import analyzer.parser.JsonMissionParser;
import analyzer.parser.MissionParser;
import analyzer.parser.TxtMissionParser;
import analyzer.parser.XmlMissionParser;

public class FormatDetector {
    public enum Format {
        TXT, JSON, XML, UNKNOWN
    }
    public static Format detectByExtension(String fileName) {
        String lower = fileName.toLowerCase();
        if (lower.endsWith(".json")) return Format.JSON;
        if (lower.endsWith(".xml"))  return Format.XML;
        if (lower.endsWith(".txt"))  return Format.TXT;
        return Format.UNKNOWN;
    }
    public static Format detectByContent(String content) {
        String trimmed = content.trim();
        if (trimmed.startsWith("{")) return Format.JSON;
        if (trimmed.startsWith("<")) return Format.XML;
        if (trimmed.contains(":"))  return Format.TXT;
        return Format.UNKNOWN;
    }
    public static Format detect(String fileName, String content) {
        Format byExt = detectByExtension(fileName);
        if (byExt != Format.UNKNOWN) return byExt;
        return detectByContent(content);
    }
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
