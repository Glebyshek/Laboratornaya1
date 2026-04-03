package analyzer.parser;
import analyzer.model.Mission;

public interface MissionParser {
    Mission parse(String content) throws Exception;
}
