package com.example.p2so.scheduler;

import com.example.p2so.model.*;
import java.util.*;

/**
 * Implementação do algoritmo de Prioridade COM preempção por prioridade
 * Seleciona o processo com maior prioridade (menor valor numérico), com preempção.
 */
public class PriorityPreemptiveScheduler extends Scheduler {

    public PriorityPreemptiveScheduler(Configuration config) {
        super(config);
    }

    @Override
    public String getAlgorithmName() {
        return "Prioridade (com preempção)";
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
                // Seleciona processo com maior prioridade (menor valor numérico)
                int highestPriority = ready.stream()
                    .mapToInt(ProcessTask::getPriority)
                    .min()
                    .orElse(Integer.MAX_VALUE);

                List<ProcessTask> candidates = ready.stream()
                    .filter(p -> p.getPriority() == highestPriority)
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

                currentProcess = selected;

                // Executa por 1 unidade de tempo (preempção)
                int startTime = currentTime;
                selected.execute();
                currentTime++;

                if (selected.isComplete()) {
                    selected.setCompletionTime(currentTime);
                    selected.setStatus(ProcessStatus.COMPLETED);
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
