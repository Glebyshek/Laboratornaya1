package analyzer.parser;
import analyzer.model.Curse;
import analyzer.model.Mission;
import analyzer.model.Sorcerer;
import analyzer.model.Technique;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonMissionParser implements MissionParser {
    @Override
    public Mission parse(String content) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(content);
        Mission mission = new Mission();
        mission.setMissionId(root.get("missionId").asText());
        mission.setDate(root.get("date").asText());
        mission.setLocation(root.get("location").asText());
        mission.setOutcome(root.get("outcome").asText());
        mission.setDamageCost(root.get("damageCost").asLong());
        JsonNode curseNode = root.get("curse");
        Curse curse = new Curse();
        curse.setName(curseNode.get("name").asText());
        curse.setThreatLevel(curseNode.get("threatLevel").asText());
        mission.setCurse(curse);
        JsonNode sorcerersNode = root.get("sorcerers");
        for (JsonNode sNode : sorcerersNode) {
            Sorcerer sorcerer = new Sorcerer();
            sorcerer.setName(sNode.get("name").asText());
            sorcerer.setRank(sNode.get("rank").asText());
            mission.addSorcerer(sorcerer);
        }
        JsonNode techNode = root.get("techniques");
        for (JsonNode tNode : techNode) {
            Technique technique = new Technique();
            technique.setName(tNode.get("name").asText());
            technique.setType(tNode.get("type").asText());
            technique.setOwner(tNode.get("owner").asText());
            technique.setDamage(tNode.get("damage").asLong());
            mission.addTechnique(technique);
        }
        return mission;
    }
}
