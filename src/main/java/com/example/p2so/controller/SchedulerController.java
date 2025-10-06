package com.example.p2so.controller;

import com.example.p2so.model.*;
import com.example.p2so.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.*;

/**
 * Controller principal que gerencia as requisições web
 */
@Controller
public class SchedulerController {
    private final SchedulerService schedulerService;
    private final ConfigurationService configService;

    public SchedulerController(SchedulerService schedulerService, ConfigurationService configService) {
        this.schedulerService = schedulerService;
        this.configService = configService;
    }

    /**
     * Página inicial
     */
    @GetMapping("/")
    public String index(Model model) {
        Configuration config = configService.getConfiguration();
        model.addAttribute("quantum", config.getQuantum());
        model.addAttribute("aging", config.getAgingRate());

        // Exemplo de entrada
        String exampleInput = "0 5 2\n0 2 3\n1 4 1\n3 3 4";
        model.addAttribute("exampleInput", exampleInput);

        return "index";
    }

    /**
     * Executa os algoritmos e retorna os resultados
     */
    @PostMapping("/simulate")
    @ResponseBody
    public Map<String, Object> simulate(@RequestBody SimulationRequest request) {
        try {
            // Atualiza configuração se fornecida
            if (request.getQuantum() > 0 && request.getAging() > 0) {
                configService.updateConfiguration(request.getQuantum(), request.getAging());
            }

            // Parse dos processos
            List<ProcessTask> processes = schedulerService.parseProcesses(request.getProcessInput());

            if (processes.isEmpty()) {
                Map<String, Object> error = new HashMap<>();
                error.put("error", "Nenhum processo válido fornecido");
                return error;
            }

            // Executa todos os schedulers
            Map<String, SchedulerResult> results = schedulerService.runAllSchedulers(processes);

            // Prepara resposta
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("results", results);
            response.put("processCount", processes.size());

            return response;
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Erro ao executar simulação: " + e.getMessage());
            return error;
        }
    }

    /**
     * Atualiza a configuração
     */
    @PostMapping("/config")
    @ResponseBody
    public Map<String, Object> updateConfig(@RequestBody ConfigRequest request) {
        try {
            configService.updateConfiguration(request.getQuantum(), request.getAging());

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("quantum", request.getQuantum());
            response.put("aging", request.getAging());

            return response;
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Erro ao atualizar configuração: " + e.getMessage());
            return error;
        }
    }

    /**
     * Classe para receber requisição de simulação
     */
    public static class SimulationRequest {
        private String processInput;
        private int quantum;
        private int aging;

        public String getProcessInput() { return processInput; }
        public void setProcessInput(String processInput) { this.processInput = processInput; }

        public int getQuantum() { return quantum; }
        public void setQuantum(int quantum) { this.quantum = quantum; }

        public int getAging() { return aging; }
        public void setAging(int aging) { this.aging = aging; }
    }

    /**
     * Classe para receber requisição de configuração
     */
    public static class ConfigRequest {
        private int quantum;
        private int aging;

        public int getQuantum() { return quantum; }
        public void setQuantum(int quantum) { this.quantum = quantum; }

        public int getAging() { return aging; }
        public void setAging(int aging) { this.aging = aging; }
    }
}
