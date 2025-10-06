package com.example.p2so.scheduler;

import com.example.p2so.model.*;
import java.util.*;

/**
 * Implementação do algoritmo SJF (Shortest Job First)
 * Seleciona o processo com menor tempo de execução, sem preempção.
 */
public class SJFScheduler extends Scheduler {

    public SJFScheduler(Configuration config) {
        super(config);
    }

    @Override
    public String getAlgorithmName() {
        return "SJF (Shortest Job First)";
    }

    @Override
    public SchedulerResult schedule(List<ProcessTask> processes) {
        List<ProcessTask> processList = copyProcesses(processes);
        List<TimelineEntry> timeline = new ArrayList<>();
        int contextSwitches = 0;
        int currentTime = 0;
        ProcessTask currentProcess = null;

        while (!allProcessesComplete(processList)) {
            List<ProcessTask> ready = getReadyProcesses(processList, currentTime);

            if (!ready.isEmpty()) {
                // Seleciona processo com menor burst time
                int minBurst = ready.stream()
                    .mapToInt(ProcessTask::getBurstTime)
                    .min()
                    .orElse(Integer.MAX_VALUE);

                List<ProcessTask> candidates = ready.stream()
                    .filter(p -> p.getBurstTime() == minBurst)
                    .toList();

                ProcessTask selected = resolveTie(candidates, currentProcess);

                // Verifica troca de contexto
                if (currentProcess != null && currentProcess.getId() != selected.getId()) {
                    contextSwitches++;
                }

                // Define tempo de resposta
                if (selected.getResponseTime() == -1) {
                    selected.setResponseTime(currentTime - selected.getArrivalTime());
                }

                // Executa até completar (sem preempção)
                int startTime = currentTime;
                currentProcess = selected;

                while (!selected.isComplete()) {
                    selected.execute();
                    currentTime++;
                }

                selected.setCompletionTime(currentTime);
                selected.setStatus(ProcessStatus.COMPLETED);

                // Timeline
                Map<Integer, String> states = new HashMap<>();
                for (ProcessTask p : processList) {
                    if (p.getArrivalTime() <= currentTime && p.getId() != selected.getId()) {
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
