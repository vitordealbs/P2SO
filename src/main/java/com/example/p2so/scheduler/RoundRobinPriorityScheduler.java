package com.example.p2so.scheduler;

import com.example.p2so.model.*;
import java.util.*;

/**
 * Implementação do algoritmo Round-Robin com prioridade e envelhecimento
 * - Envelhecimento ocorre a cada quantum
 * - NÃO há preempção por prioridade
 * - Processos são organizados por prioridade e executam por quantum
 */
public class RoundRobinPriorityScheduler extends Scheduler {

    public RoundRobinPriorityScheduler(Configuration config) {
        super(config);
    }

    @Override
    public String getAlgorithmName() {
        return "Round-Robin com Prioridade e Envelhecimento (quantum=" + config.getQuantum() + ", aging=" + config.getAgingRate() + ")";
    }

    @Override
    public SchedulerResult schedule(List<ProcessTask> processes) {
        List<ProcessTask> processList = copyProcesses(processes);
        List<TimelineEntry> timeline = new ArrayList<>();
        List<ProcessTask> readyQueue = new ArrayList<>();
        Set<Integer> inQueue = new HashSet<>();
        int contextSwitches = 0;
        int currentTime = 0;
        ProcessTask currentProcess = null;

        // Adiciona processos que chegam no tempo 0
        for (ProcessTask p : processList) {
            if (p.getArrivalTime() == 0) {
                readyQueue.add(p);
                inQueue.add(p.getId());
            }
        }

        while (!allProcessesComplete(processList)) {
            // Adiciona novos processos que chegaram
            for (ProcessTask p : processList) {
                if (p.getArrivalTime() == currentTime && !inQueue.contains(p.getId()) && !p.isComplete()) {
                    readyQueue.add(p);
                    inQueue.add(p.getId());
                }
            }

            if (!readyQueue.isEmpty()) {
                // Ordena por prioridade atual (menor valor = maior prioridade)
                readyQueue.sort(Comparator.comparingInt(ProcessTask::getCurrentPriority)
                                         .thenComparingInt(ProcessTask::getId));

                ProcessTask selected = readyQueue.remove(0);
                inQueue.remove(selected.getId());

                // Verifica troca de contexto
                if (currentProcess != null && currentProcess.getId() != selected.getId()) {
                    contextSwitches++;
                }

                // Define tempo de resposta
                if (selected.getResponseTime() == -1) {
                    selected.setResponseTime(currentTime - selected.getArrivalTime());
                }

                currentProcess = selected;
                int startTime = currentTime;
                int executionTime = 0;

                // Executa por quantum ou até completar
                while (executionTime < config.getQuantum() && !selected.isComplete()) {
                    selected.execute();
                    currentTime++;
                    executionTime++;

                    // Adiciona novos processos que chegaram durante execução
                    for (ProcessTask p : processList) {
                        if (p.getArrivalTime() == currentTime && !inQueue.contains(p.getId()) && !p.isComplete()) {
                            readyQueue.add(p);
                            inQueue.add(p.getId());
                        }
                    }
                }

                // Aplica envelhecimento aos processos na fila de espera
                for (ProcessTask p : readyQueue) {
                    p.age(config.getAgingRate());
                }

                if (selected.isComplete()) {
                    selected.setCompletionTime(currentTime);
                    selected.setStatus(ProcessStatus.COMPLETED);
                } else {
                    // Retorna à fila (reseta prioridade do processo que executou)
                    selected.resetPriority();
                    readyQueue.add(selected);
                    inQueue.add(selected.getId());
                }

                // Timeline
                Map<Integer, String> states = new HashMap<>();
                for (ProcessTask p : processList) {
                    if (p.getArrivalTime() <= currentTime && p.getId() != selected.getId() && !p.isComplete()) {
                        states.put(p.getId(), "--");
                    }
                }
                timeline.add(new TimelineEntry(startTime, currentTime, selected.getId(), states));

            } else {
                currentTime++;
            }
        }

        return buildResult(processList, timeline, contextSwitches);
    }

    private boolean allProcessesComplete(List<ProcessTask> processes) {
        return processes.stream().allMatch(ProcessTask::isComplete);
    }
}
