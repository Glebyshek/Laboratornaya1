package analyzer.parser;
import analyzer.model.Curse;
import analyzer.model.Mission;
import analyzer.model.Sorcerer;
import analyzer.model.Technique;
import java.util.HashMap;
import java.util.Map;

public class TxtMissionParser implements MissionParser {
    @Override
    public Mission parse(String content) throws Exception {
        Map<String, String> data = new HashMap<>();
        String[] lines = content.split("\\r?\\n");
        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty()) continue;
            int colonIndex = line.indexOf(':');
            if (colonIndex == -1) continue;
            String key = line.substring(0, colonIndex).trim();
            String value = line.substring(colonIndex + 1).trim();
            data.put(key, value);
        }
        Mission mission = new Mission();
        mission.setMissionId(data.getOrDefault("missionId", ""));
        mission.setDate(data.getOrDefault("date", ""));
        mission.setLocation(data.getOrDefault("location", ""));
        mission.setOutcome(data.getOrDefault("outcome", ""));
        String damageCostStr = data.getOrDefault("damageCost", "0");
        mission.setDamageCost(Long.parseLong(damageCostStr));
        Curse curse = new Curse();
        curse.setName(data.getOrDefault("curse.name", ""));
        curse.setThreatLevel(data.getOrDefault("curse.threatLevel", ""));
        mission.setCurse(curse);
        for (int i = 0; ; i++) {
            String nameKey = "sorcerer[" + i + "].name";
            String rankKey = "sorcerer[" + i + "].rank";
            if (!data.containsKey(nameKey)) break; // Больше магов нет
            Sorcerer sorcerer = new Sorcerer();
            sorcerer.setName(data.getOrDefault(nameKey, ""));
            sorcerer.setRank(data.getOrDefault(rankKey, ""));
            mission.addSorcerer(sorcerer);
        }
        for (int i = 0; ; i++) {
            String nameKey = "technique[" + i + "].name";
            String typeKey = "technique[" + i + "].type";
            String ownerKey = "technique[" + i + "].owner";
            String damageKey = "technique[" + i + "].damage";
            if (!data.containsKey(nameKey)) break; // Больше техник нет
            Technique technique = new Technique();
            technique.setName(data.getOrDefault(nameKey, ""));
            technique.setType(data.getOrDefault(typeKey, ""));
            technique.setOwner(data.getOrDefault(ownerKey, ""));
            String dmgStr = data.getOrDefault(damageKey, "0");
            technique.setDamage(Long.parseLong(dmgStr));
            mission.addTechnique(technique);
        }
        return mission;
    }
}
