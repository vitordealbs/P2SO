package com.example.p2so.model;

import java.util.List;
import java.util.Map;

/**
 * Representa o resultado da execução de um algoritmo de escalonamento.
 * Contém todas as métricas e informações sobre a execução.
 */
public class SchedulerResult {
    private String algorithmName;           // Nome do algoritmo executado
    private double averageTurnaroundTime;   // Tempo médio de vida (tt)
    private double averageWaitingTime;      // Tempo médio de espera (tw)
    private int contextSwitches;            // Número de trocas de contexto
    private List<TimelineEntry> timeline;   // Diagrama de tempo da execução
    private Map<Integer, ProcessMetrics> processMetrics; // Métricas individuais de cada processo

    /**
     * Construtor completo
     */
    public SchedulerResult(String algorithmName, double averageTurnaroundTime,
                          double averageWaitingTime, int contextSwitches,
                          List<TimelineEntry> timeline, Map<Integer, ProcessMetrics> processMetrics) {
        this.algorithmName = algorithmName;
        this.averageTurnaroundTime = averageTurnaroundTime;
        this.averageWaitingTime = averageWaitingTime;
        this.contextSwitches = contextSwitches;
        this.timeline = timeline;
        this.processMetrics = processMetrics;
    }

    // Getters e Setters
    public String getAlgorithmName() { return algorithmName; }
    public void setAlgorithmName(String algorithmName) { this.algorithmName = algorithmName; }

    public double getAverageTurnaroundTime() { return averageTurnaroundTime; }
    public void setAverageTurnaroundTime(double averageTurnaroundTime) {
        this.averageTurnaroundTime = averageTurnaroundTime;
    }

    public double getAverageWaitingTime() { return averageWaitingTime; }
    public void setAverageWaitingTime(double averageWaitingTime) {
        this.averageWaitingTime = averageWaitingTime;
    }

    public int getContextSwitches() { return contextSwitches; }
    public void setContextSwitches(int contextSwitches) {
        this.contextSwitches = contextSwitches;
    }

    public List<TimelineEntry> getTimeline() { return timeline; }
    public void setTimeline(List<TimelineEntry> timeline) {
        this.timeline = timeline;
    }

    public Map<Integer, ProcessMetrics> getProcessMetrics() { return processMetrics; }
    public void setProcessMetrics(Map<Integer, ProcessMetrics> processMetrics) {
        this.processMetrics = processMetrics;
    }

    /**
     * Gera a representação textual do diagrama de tempo
     * @return String formatada com o diagrama de tempo
     */
    public String getTimelineDiagram() {
        if (timeline == null || timeline.isEmpty()) {
            return "Nenhuma execução registrada";
        }

        StringBuilder sb = new StringBuilder();

        // Cabeçalho
        sb.append(String.format("%-8s", "tempo"));
        int maxProcessId = timeline.stream()
            .mapToInt(TimelineEntry::getProcessId)
            .max()
            .orElse(0);

        for (int i = 1; i <= maxProcessId; i++) {
            sb.append(String.format(" P%-3d", i));
        }
        sb.append("\n");

        // Linhas do timeline
        for (TimelineEntry entry : timeline) {
            sb.append(String.format("%2d-%2d   ", entry.getStartTime(), entry.getEndTime()));

            for (int i = 1; i <= maxProcessId; i++) {
                if (i == entry.getProcessId()) {
                    sb.append(" ##  ");
                } else if (entry.getProcessStates().containsKey(i)) {
                    sb.append(" --  ");
                } else {
                    sb.append("     ");
                }
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
