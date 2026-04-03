package analyzer.display;
import analyzer.model.Mission;
import analyzer.model.Sorcerer;
import analyzer.model.Technique;

public class MissionFormatter {
    public static String format(Mission mission) {
        StringBuilder sb = new StringBuilder();
        sb.append("--- Отчёт о миссии ---\n");
        sb.append("\n");
        sb.append("  ID:          ").append(mission.getMissionId()).append("\n");
        sb.append("  Дата:        ").append(mission.getDate()).append("\n");
        sb.append("  Локация:     ").append(mission.getLocation()).append("\n");
        sb.append("  Результат:   ").append(mission.getOutcome()).append("\n");
        sb.append("  Ущерб:       ").append(mission.getDamageCost()).append("\n");
        sb.append("\n");
        sb.append("  Проклятие:\n");
        if (mission.getCurse() != null) {
            sb.append("    Название:      ").append(mission.getCurse().getName()).append("\n");
            sb.append("    Уровень угрозы: ").append(mission.getCurse().getThreatLevel()).append("\n");
        }
        sb.append("\n");
        sb.append("  Участники:\n");
        int i = 1;
        for (Sorcerer s : mission.getSorcerers()) {
            sb.append("    ").append(i).append(") ")
              .append(s.getName())
              .append(" - ранг: ").append(s.getRank())
              .append("\n");
            i++;
        }
        sb.append("\n");
        sb.append("  Техники:\n");
        int j = 1;
        for (Technique t : mission.getTechniques()) {
            sb.append("    ").append(j).append(") ").append(t.getName()).append("\n");
            sb.append("       Тип:      ").append(t.getType()).append("\n");
            sb.append("       Владелец: ").append(t.getOwner()).append("\n");
            sb.append("       Урон:     ").append(t.getDamage()).append("\n");
            j++;
        }
        sb.append("\n");
        sb.append("--------------------------\n");
        return sb.toString();
    }
}
