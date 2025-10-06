package com.example.p2so.model;

/**
 * Representa um processo no sistema de escalonamento.
 * Contém todas as informações necessárias para simular o escalonamento de processos.
 */
public class ProcessTask {
    private int id;                    // ID único do processo (P1, P2, P3...)
    private int arrivalTime;           // Instante de criação do processo
    private int burstTime;             // Duração total em segundos
    private int remainingTime;         // Tempo restante de execução
    private int priority;              // Prioridade estática (quanto menor, maior prioridade)
    private int currentPriority;       // Prioridade atual (para envelhecimento)
    private int waitingTime;           // Tempo de espera total
    private int turnaroundTime;        // Tempo de vida total (tt)
    private int responseTime;          // Tempo de primeira resposta
    private int completionTime;        // Tempo de conclusão
    private boolean hasStarted;        // Flag para verificar se já começou a executar
    private ProcessStatus status;      // Status atual do processo

    /**
     * Construtor principal do processo
     * @param id ID do processo
     * @param arrivalTime Instante de criação
     * @param burstTime Duração em segundos
     * @param priority Prioridade estática
     */
    public ProcessTask(int id, int arrivalTime, int burstTime, int priority) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
        this.priority = priority;
        this.currentPriority = priority;
        this.waitingTime = 0;
        this.turnaroundTime = 0;
        this.responseTime = -1;
        this.completionTime = 0;
        this.hasStarted = false;
        this.status = ProcessStatus.READY;
    }

    /**
     * Construtor de cópia para criar uma cópia independente do processo
     * @param other Processo a ser copiado
     */
    public ProcessTask(ProcessTask other) {
        this.id = other.id;
        this.arrivalTime = other.arrivalTime;
        this.burstTime = other.burstTime;
        this.remainingTime = other.remainingTime;
        this.priority = other.priority;
        this.currentPriority = other.currentPriority;
        this.waitingTime = other.waitingTime;
        this.turnaroundTime = other.turnaroundTime;
        this.responseTime = other.responseTime;
        this.completionTime = other.completionTime;
        this.hasStarted = other.hasStarted;
        this.status = other.status;
    }

    /**
     * Executa o processo por uma unidade de tempo
     */
    public void execute() {
        if (!hasStarted) {
            hasStarted = true;
        }
        remainingTime--;
        status = ProcessStatus.RUNNING;
    }

    /**
     * Verifica se o processo está completo
     * @return true se o processo terminou de executar
     */
    public boolean isComplete() {
        return remainingTime == 0;
    }

    /**
     * Aplica envelhecimento ao processo (diminui a prioridade numérica)
     * @param agingRate Taxa de envelhecimento
     */
    public void age(int agingRate) {
        currentPriority = Math.max(1, currentPriority - agingRate);
    }

    /**
     * Reseta a prioridade atual para a prioridade estática
     */
    public void resetPriority() {
        currentPriority = priority;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getArrivalTime() { return arrivalTime; }
    public void setArrivalTime(int arrivalTime) { this.arrivalTime = arrivalTime; }

    public int getBurstTime() { return burstTime; }
    public void setBurstTime(int burstTime) { this.burstTime = burstTime; }

    public int getRemainingTime() { return remainingTime; }
    public void setRemainingTime(int remainingTime) { this.remainingTime = remainingTime; }

    public int getPriority() { return priority; }
    public void setPriority(int priority) { this.priority = priority; }

    public int getCurrentPriority() { return currentPriority; }
    public void setCurrentPriority(int currentPriority) { this.currentPriority = currentPriority; }

    public int getWaitingTime() { return waitingTime; }
    public void setWaitingTime(int waitingTime) { this.waitingTime = waitingTime; }

    public int getTurnaroundTime() { return turnaroundTime; }
    public void setTurnaroundTime(int turnaroundTime) { this.turnaroundTime = turnaroundTime; }

    public int getResponseTime() { return responseTime; }
    public void setResponseTime(int responseTime) { this.responseTime = responseTime; }

    public int getCompletionTime() { return completionTime; }
    public void setCompletionTime(int completionTime) { this.completionTime = completionTime; }

    public boolean hasStarted() { return hasStarted; }
    public void setHasStarted(boolean hasStarted) { this.hasStarted = hasStarted; }

    public ProcessStatus getStatus() { return status; }
    public void setStatus(ProcessStatus status) { this.status = status; }

    @Override
    public String toString() {
        return String.format("P%d[arrival=%d, burst=%d, priority=%d]",
                           id, arrivalTime, burstTime, priority);
    }
}
