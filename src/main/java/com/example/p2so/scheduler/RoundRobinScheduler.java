package com.example.p2so.scheduler;

import com.example.p2so.model.*;
import java.util.*;

/**
 * Implementação do algoritmo Round-Robin com quantum, SEM prioridade
 * Cada processo executa por um quantum de tempo antes de ser preemptado.
 */
public class RoundRobinScheduler extends Scheduler {

    public RoundRobinScheduler(Configuration config) {
        super(config);
    }

    @Override
    public String getAlgorithmName() {
        return "Round-Robin (quantum=" + config.getQuantum() + ")";
    }

    @Override
    public SchedulerResult schedule(List<ProcessTask> processes) {
        List<ProcessTask> processList = copyProcesses(processes);
        List<TimelineEntry> timeline = new ArrayList<>();
        Queue<ProcessTask> readyQueue = new LinkedList<>();
        Set<Integer> inQueue = new HashSet<>();
        int contextSwitches = 0;
        int currentTime = 0;
        ProcessTask currentProcess = null;

        // Adiciona processos que chegam no tempo 0
        for (ProcessTask p : processList) {
            if (p.getArrivalTime() == 0) {
                readyQueue.offer(p);
                inQueue.add(p.getId());
            }
        }

        while (!allProcessesComplete(processList)) {
            // Adiciona novos processos que chegaram
            for (ProcessTask p : processList) {
                if (p.getArrivalTime() == currentTime && !inQueue.contains(p.getId()) && !p.isComplete()) {
                    readyQueue.offer(p);
                    inQueue.add(p.getId());
                }
            }

            if (!readyQueue.isEmpty()) {
                ProcessTask selected = readyQueue.poll();
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
                            readyQueue.offer(p);
                            inQueue.add(p.getId());
                        }
                    }
                }

                if (selected.isComplete()) {
                    selected.setCompletionTime(currentTime);
                    selected.setStatus(ProcessStatus.COMPLETED);
                } else {
                    // Retorna à fila
                    readyQueue.offer(selected);
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
