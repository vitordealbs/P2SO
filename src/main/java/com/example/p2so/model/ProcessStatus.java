package com.example.p2so.model;

/**
 * Enumera os possíveis estados de um processo durante a execução
 */
public enum ProcessStatus {
    READY,      // Pronto para execução
    RUNNING,    // Executando
    WAITING,    // Esperando
    COMPLETED   // Concluído
}
