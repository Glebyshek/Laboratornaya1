package analyzer.parser;
import analyzer.model.Curse;
import analyzer.model.Mission;
import analyzer.model.Sorcerer;
import analyzer.model.Technique;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;

public class XmlMissionParser implements MissionParser {
    @Override
    public Mission parse(String content) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(content)));
        Element root = doc.getDocumentElement();
        root.normalize();
        Mission mission = new Mission();
        mission.setMissionId(getTextContent(root, "missionId"));
        mission.setDate(getTextContent(root, "date"));
        mission.setLocation(getTextContent(root, "location"));
        mission.setOutcome(getTextContent(root, "outcome"));
        mission.setDamageCost(Long.parseLong(getTextContent(root, "damageCost")));
        NodeList curseNodes = root.getElementsByTagName("curse");
        if (curseNodes.getLength() > 0) {
            Element curseEl = (Element) curseNodes.item(0);
            Curse curse = new Curse();
            curse.setName(getTextContent(curseEl, "name"));
            curse.setThreatLevel(getTextContent(curseEl, "threatLevel"));
            mission.setCurse(curse);
        }
        NodeList sorcererNodes = root.getElementsByTagName("sorcerer");
        for (int i = 0; i < sorcererNodes.getLength(); i++) {
            Element sEl = (Element) sorcererNodes.item(i);
            Sorcerer sorcerer = new Sorcerer();
            sorcerer.setName(getTextContent(sEl, "name"));
            sorcerer.setRank(getTextContent(sEl, "rank"));
            mission.addSorcerer(sorcerer);
        }
        NodeList techniqueNodes = root.getElementsByTagName("technique");
        for (int i = 0; i < techniqueNodes.getLength(); i++) {
            Element tEl = (Element) techniqueNodes.item(i);
            Technique technique = new Technique();
            technique.setName(getTextContent(tEl, "name"));
            technique.setType(getTextContent(tEl, "type"));
            technique.setOwner(getTextContent(tEl, "owner"));
            technique.setDamage(Long.parseLong(getTextContent(tEl, "damage")));
            mission.addTechnique(technique);
        }
        return mission;
    }
    private String getTextContent(Element parent, String tagName) {
        NodeList list = parent.getElementsByTagName(tagName);
        if (list.getLength() > 0) {
            return list.item(0).getTextContent().trim();
        }
        return "";
    }
}
