package com.example.p2so.model;

import java.util.Map;

/**
 * Representa uma entrada no diagrama de tempo da execução.
 * Cada entrada corresponde a um intervalo de tempo onde um processo específico estava executando.
 */
public class TimelineEntry {
    private int startTime;                      // Tempo de início do intervalo
    private int endTime;                        // Tempo de fim do intervalo
    private int processId;                      // ID do processo executando (0 se CPU ociosa)
    private Map<Integer, String> processStates; // Estado de todos os processos neste momento

    /**
     * Construtor completo
     */
    public TimelineEntry(int startTime, int endTime, int processId, Map<Integer, String> processStates) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.processId = processId;
        this.processStates = processStates;
    }

    // Getters e Setters
    public int getStartTime() { return startTime; }
    public void setStartTime(int startTime) { this.startTime = startTime; }

    public int getEndTime() { return endTime; }
    public void setEndTime(int endTime) { this.endTime = endTime; }

    public int getProcessId() { return processId; }
    public void setProcessId(int processId) { this.processId = processId; }

    public Map<Integer, String> getProcessStates() { return processStates; }
    public void setProcessStates(Map<Integer, String> processStates) {
        this.processStates = processStates;
    }
}
