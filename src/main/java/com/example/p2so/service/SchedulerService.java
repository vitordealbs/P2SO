package com.example.p2so.service;

import com.example.p2so.model.*;
import com.example.p2so.scheduler.*;
import org.springframework.stereotype.Service;
import java.util.*;

/**
 * Serviço principal que gerencia a execução dos algoritmos de escalonamento
 */
@Service
public class SchedulerService {
    private final ConfigurationService configService;

    public SchedulerService(ConfigurationService configService) {
        this.configService = configService;
    }

    /**
     * Executa todos os algoritmos de escalonamento para uma lista de processos
     */
    public Map<String, SchedulerResult> runAllSchedulers(List<ProcessTask> processes) {
        Map<String, SchedulerResult> results = new LinkedHashMap<>();
        Configuration config = configService.getConfiguration();

        // Lista de todos os schedulers
        List<Scheduler> schedulers = Arrays.asList(
            new FCFSScheduler(config),
            new SJFScheduler(config),
            new SRTFScheduler(config),
            new PriorityNonPreemptiveScheduler(config),
            new PriorityPreemptiveScheduler(config),
            new RoundRobinScheduler(config),
            new RoundRobinPriorityScheduler(config)
        );

        // Executa cada scheduler
        for (Scheduler scheduler : schedulers) {
            SchedulerResult result = scheduler.schedule(processes);
            results.put(scheduler.getAlgorithmName(), result);
        }

        return results;
    }

    /**
     * Executa um algoritmo específico
     */
    public SchedulerResult runScheduler(String algorithm, List<ProcessTask> processes) {
        Configuration config = configService.getConfiguration();
        Scheduler scheduler = getScheduler(algorithm, config);

        if (scheduler == null) {
            throw new IllegalArgumentException("Algoritmo desconhecido: " + algorithm);
        }

        return scheduler.schedule(processes);
    }

    /**
     * Retorna o scheduler correspondente ao nome do algoritmo
     */
    private Scheduler getScheduler(String algorithm, Configuration config) {
        return switch (algorithm.toUpperCase()) {
            case "FCFS" -> new FCFSScheduler(config);
            case "SJF" -> new SJFScheduler(config);
            case "SRTF" -> new SRTFScheduler(config);
            case "PRIORITY" -> new PriorityNonPreemptiveScheduler(config);
            case "PRIORITY_PREEMPTIVE" -> new PriorityPreemptiveScheduler(config);
            case "ROUND_ROBIN" -> new RoundRobinScheduler(config);
            case "ROUND_ROBIN_PRIORITY" -> new RoundRobinPriorityScheduler(config);
            default -> null;
        };
    }

    /**
     * Analisa a entrada de texto e cria a lista de processos
     */
    public List<ProcessTask> parseProcesses(String input) {
        List<ProcessTask> processes = new ArrayList<>();
        String[] lines = input.trim().split("\n");
        int processId = 1;

        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty()) continue;

            String[] parts = line.split("\\s+");
            if (parts.length >= 3) {
                try {
                    int arrivalTime = Integer.parseInt(parts[0]);
                    int burstTime = Integer.parseInt(parts[1]);
                    int priority = Integer.parseInt(parts[2]);

                    ProcessTask process = new ProcessTask(processId++, arrivalTime, burstTime, priority);
                    processes.add(process);
                } catch (NumberFormatException e) {
                    System.err.println("Erro ao parsear linha: " + line);
                }
            }
        }

        return processes;
    }
}
