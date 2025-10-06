package com.example.p2so.scheduler;

import com.example.p2so.model.*;
import java.util.*;

/**
 * Implementação do algoritmo SRTF (Shortest Remaining Time First)
 * Seleciona o processo com menor tempo restante, COM preempção.
 */
public class SRTFScheduler extends Scheduler {

    public SRTFScheduler(Configuration config) {
        super(config);
    }

    @Override
    public String getAlgorithmName() {
        return "SRTF (Shortest Remaining Time First)";
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
                // Seleciona processo com menor tempo restante
                int minRemaining = ready.stream()
                    .mapToInt(ProcessTask::getRemainingTime)
                    .min()
                    .orElse(Integer.MAX_VALUE);

                List<ProcessTask> candidates = ready.stream()
                    .filter(p -> p.getRemainingTime() == minRemaining)
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
