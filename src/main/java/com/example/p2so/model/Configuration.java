package com.example.p2so.model;

/**
 * Configurações do simulador de escalonamento.
 * Armazena valores de quantum e taxa de envelhecimento.
 */
public class Configuration {
    private int quantum;     // Quantum para Round-Robin
    private int agingRate;   // Taxa de envelhecimento para Round-Robin com prioridade

    public Configuration() {
        this.quantum = 2;    // Valor padrão
        this.agingRate = 1;  // Valor padrão
    }

    public Configuration(int quantum, int agingRate) {
        this.quantum = quantum;
        this.agingRate = agingRate;
    }

    // Getters e Setters
    public int getQuantum() { return quantum; }
    public void setQuantum(int quantum) { this.quantum = quantum; }

    public int getAgingRate() { return agingRate; }
    public void setAgingRate(int agingRate) { this.agingRate = agingRate; }

    @Override
    public String toString() {
        return String.format("Configuration[quantum=%d, aging=%d]", quantum, agingRate);
    }
}
