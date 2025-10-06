package com.example.p2so.scheduler;

import com.example.p2so.model.*;
import java.util.*;

/**
 * Classe base abstrata para todos os algoritmos de escalonamento.
 * Fornece funcionalidades comuns para cálculo de métricas e geração de resultados.
 */
public abstract class Scheduler {
    protected Configuration config;

    public Scheduler(Configuration config) {
        this.config = config;
    }

    /**
     * Método abstrato que deve ser implementado por cada algoritmo de escalonamento
     * @param processes Lista de processos a serem escalonados
     * @return Resultado do escalonamento com métricas e timeline
     */
    public abstract SchedulerResult schedule(List<ProcessTask> processes);

    /**
     * Retorna o nome do algoritmo
     */
    public abstract String getAlgorithmName();

    /**
     * Cria uma cópia profunda da lista de processos
     */
    protected List<ProcessTask> copyProcesses(List<ProcessTask> processes) {
        List<ProcessTask> copy = new ArrayList<>();
        for (ProcessTask p : processes) {
            copy.add(new ProcessTask(p));
        }
        return copy;
    }

    /**
     * Calcula as métricas finais dos processos
     */
    protected SchedulerResult buildResult(List<ProcessTask> processes, List<TimelineEntry> timeline,
                                         int contextSwitches) {
        Map<Integer, ProcessMetrics> metricsMap = new HashMap<>();
        double totalTurnaround = 0;
        double totalWaiting = 0;

        for (ProcessTask p : processes) {
            // Turnaround time = completion time - arrival time
            p.setTurnaroundTime(p.getCompletionTime() - p.getArrivalTime());
            // Waiting time = turnaround time - burst time
            p.setWaitingTime(p.getTurnaroundTime() - p.getBurstTime());

            totalTurnaround += p.getTurnaroundTime();
            totalWaiting += p.getWaitingTime();

            ProcessMetrics metrics = new ProcessMetrics(
                p.getId(),
                p.getArrivalTime(),
                p.getBurstTime(),
                p.getCompletionTime(),
                p.getTurnaroundTime(),
                p.getWaitingTime(),
                p.getResponseTime()
            );
            metricsMap.put(p.getId(), metrics);
        }

        double avgTurnaround = totalTurnaround / processes.size();
        double avgWaiting = totalWaiting / processes.size();

        return new SchedulerResult(
            getAlgorithmName(),
            avgTurnaround,
            avgWaiting,
            contextSwitches,
            timeline,
            metricsMap
        );
    }

    /**
     * Resolve empates na escolha de processos seguindo as regras:
     * 1. Processo que já está com processador (evita troca de contexto)
     * 2. Processo com menor tempo restante
     * 3. Escolha aleatória
     */
    protected ProcessTask resolveTie(List<ProcessTask> candidates, ProcessTask currentProcess) {
        if (candidates.isEmpty()) return null;
        if (candidates.size() == 1) return candidates.get(0);

        // Regra 1: Se o processo atual está entre os candidatos, mantém ele
        if (currentProcess != null && candidates.contains(currentProcess)) {
            return currentProcess;
        }

        // Regra 2: Escolhe o processo com menor tempo restante
        int minRemainingTime = candidates.stream()
            .mapToInt(ProcessTask::getRemainingTime)
            .min()
            .orElse(Integer.MAX_VALUE);

        List<ProcessTask> shortestCandidates = candidates.stream()
            .filter(p -> p.getRemainingTime() == minRemainingTime)
            .toList();

        if (shortestCandidates.size() == 1) {
            return shortestCandidates.get(0);
        }

        // Regra 3: Escolha aleatória
        Random random = new Random(42); // Seed fixo para reprodutibilidade
        return shortestCandidates.get(random.nextInt(shortestCandidates.size()));
    }

    /**
     * Obtém os processos prontos em um determinado tempo
     */
    protected List<ProcessTask> getReadyProcesses(List<ProcessTask> processes, int currentTime) {
        return processes.stream()
            .filter(p -> p.getArrivalTime() <= currentTime && !p.isComplete())
            .toList();
    }
}
