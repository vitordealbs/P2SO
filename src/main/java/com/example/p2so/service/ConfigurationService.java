package com.example.p2so.service;

import com.example.p2so.model.Configuration;
import org.springframework.stereotype.Service;
import java.io.*;
import java.nio.file.*;

/**
 * Serviço responsável por gerenciar a configuração do simulador.
 * Lê e escreve o arquivo config.txt com os valores de quantum e aging.
 */
@Service
public class ConfigurationService {
    private static final String CONFIG_FILE = "config.txt";
    private Configuration configuration;

    public ConfigurationService() {
        this.configuration = loadConfiguration();
    }

    /**
     * Carrega a configuração do arquivo config.txt
     * Se o arquivo não existir, cria um com valores padrão
     */
    public Configuration loadConfiguration() {
        File file = new File(CONFIG_FILE);

        if (!file.exists()) {
            Configuration defaultConfig = new Configuration(2, 1);
            saveConfiguration(defaultConfig);
            return defaultConfig;
        }

        try {
            String content = Files.readString(file.toPath());
            int quantum = 2;
            int aging = 1;

            String[] lines = content.split("\n");
            for (String line : lines) {
                line = line.trim();
                if (line.startsWith("quantum:")) {
                    quantum = Integer.parseInt(line.substring(8).trim());
                } else if (line.startsWith("aging:")) {
                    aging = Integer.parseInt(line.substring(6).trim());
                }
            }

            return new Configuration(quantum, aging);
        } catch (IOException | NumberFormatException e) {
            System.err.println("Erro ao ler configuração: " + e.getMessage());
            return new Configuration(2, 1);
        }
    }

    /**
     * Salva a configuração no arquivo config.txt
     */
    public void saveConfiguration(Configuration config) {
        try {
            String content = String.format("quantum:%d\naging:%d\n",
                                         config.getQuantum(),
                                         config.getAgingRate());
            Files.writeString(Paths.get(CONFIG_FILE), content);
            this.configuration = config;
        } catch (IOException e) {
            System.err.println("Erro ao salvar configuração: " + e.getMessage());
        }
    }

    /**
     * Retorna a configuração atual
     */
    public Configuration getConfiguration() {
        return configuration;
    }

    /**
     * Atualiza a configuração
     */
    public void updateConfiguration(int quantum, int aging) {
        Configuration newConfig = new Configuration(quantum, aging);
        saveConfiguration(newConfig);
    }
}
