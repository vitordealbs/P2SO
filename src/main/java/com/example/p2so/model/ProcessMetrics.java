package com.example.p2so.model;

/**
 * Métricas individuais de um processo após a execução
 */
public class ProcessMetrics {
    private int processId;
    private int arrivalTime;
    private int burstTime;
    private int completionTime;
    private int turnaroundTime;
    private int waitingTime;
    private int responseTime;

    public ProcessMetrics(int processId, int arrivalTime, int burstTime,
                         int completionTime, int turnaroundTime,
                         int waitingTime, int responseTime) {
        this.processId = processId;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.completionTime = completionTime;
        this.turnaroundTime = turnaroundTime;
        this.waitingTime = waitingTime;
        this.responseTime = responseTime;
    }

    // Getters e Setters
    public int getProcessId() { return processId; }
    public void setProcessId(int processId) { this.processId = processId; }

    public int getArrivalTime() { return arrivalTime; }
    public void setArrivalTime(int arrivalTime) { this.arrivalTime = arrivalTime; }

    public int getBurstTime() { return burstTime; }
    public void setBurstTime(int burstTime) { this.burstTime = burstTime; }

    public int getCompletionTime() { return completionTime; }
    public void setCompletionTime(int completionTime) { this.completionTime = completionTime; }

    public int getTurnaroundTime() { return turnaroundTime; }
    public void setTurnaroundTime(int turnaroundTime) { this.turnaroundTime = turnaroundTime; }

    public int getWaitingTime() { return waitingTime; }
    public void setWaitingTime(int waitingTime) { this.waitingTime = waitingTime; }

    public int getResponseTime() { return responseTime; }
    public void setResponseTime(int responseTime) { this.responseTime = responseTime; }
}
