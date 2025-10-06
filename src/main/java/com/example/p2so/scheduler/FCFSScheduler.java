package com.example.p2so.scheduler;

import com.example.p2so.model.*;
import java.util.*;

/**
 * Implementação do algoritmo FCFS (First Come, First Served)
 * Processos são executados na ordem de chegada, sem preempção.
 */
public class FCFSScheduler extends Scheduler {

    public FCFSScheduler(Configuration config) {
        super(config);
    }

    @Override
    public String getAlgorithmName() {
        return "FCFS (First Come, First Served)";
    }

    @Override
    public SchedulerResult schedule(List<ProcessTask> processes) {
        List<ProcessTask> processList = copyProcesses(processes);
        List<TimelineEntry> timeline = new ArrayList<>();
        int contextSwitches = 0;
        int currentTime = 0;
        ProcessTask currentProcess = null;

        // Ordena processos por tempo de chegada
        processList.sort(Comparator.comparingInt(ProcessTask::getArrivalTime)
                                   .thenComparingInt(ProcessTask::getId));

        while (!allProcessesComplete(processList)) {
            // Obtém processos prontos
            List<ProcessTask> ready = getReadyProcesses(processList, currentTime);

            if (!ready.isEmpty()) {
                // Seleciona o primeiro processo pronto (já ordenado por chegada)
                ProcessTask selected = ready.get(0);

                // Verifica troca de contexto
                if (currentProcess != null && currentProcess.getId() != selected.getId()) {
                    contextSwitches++;
                }

                // Define tempo de resposta na primeira execução
                if (selected.getResponseTime() == -1) {
                    selected.setResponseTime(currentTime - selected.getArrivalTime());
                }

                // Executa o processo até completar (FCFS não tem preempção)
                int startTime = currentTime;
                currentProcess = selected;

                while (!selected.isComplete()) {
                    selected.execute();
                    currentTime++;
                }

                selected.setCompletionTime(currentTime);
                selected.setStatus(ProcessStatus.COMPLETED);

                // Adiciona entrada no timeline
                Map<Integer, String> states = new HashMap<>();
                for (ProcessTask p : processList) {
                    if (p.getArrivalTime() <= currentTime && p.getId() != selected.getId()) {
                        states.put(p.getId(), "--");
                    }
                }
                timeline.add(new TimelineEntry(startTime, currentTime, selected.getId(), states));

            } else {
                // CPU ociosa
                currentTime++;
            }
        }

        return buildResult(processList, timeline, contextSwitches);
    }

    private boolean allProcessesComplete(List<ProcessTask> processes) {
        return processes.stream().allMatch(ProcessTask::isComplete);
    }
}
