# 📦 ENTREGA - PRÁTICA 02: ESCALONAMENTO DE PROCESSOS

## ✅ PROJETO COMPLETO E FUNCIONAL

### 🎯 TODOS OS REQUISITOS IMPLEMENTADOS

#### ✓ 7 Algoritmos de Escalonamento
1. **FCFS** (First Come, First Served) - ✅ Implementado
2. **SJF** (Shortest Job First) - ✅ Implementado
3. **SRTF** (Shortest Remaining Time First) - ✅ Implementado
4. **Prioridade sem preempção** - ✅ Implementado
5. **Prioridade com preempção** - ✅ Implementado
6. **Round-Robin** (quantum, sem prioridade) - ✅ Implementado
7. **Round-Robin com prioridade e envelhecimento** - ✅ Implementado

#### ✓ Funcionalidades Obrigatórias
- ✅ Leitura de arquivo de configuração (config.txt)
- ✅ Entrada de processos via texto (formato especificado)
- ✅ Cálculo de tempo médio de vida (Turnaround Time)
- ✅ Cálculo de tempo médio de espera (Waiting Time)
- ✅ Contagem de trocas de contexto
- ✅ Geração de diagrama de tempo
- ✅ Código completamente comentado
- ✅ Documentação das estruturas de dados

#### 🎁 BÔNUS: Interface Gráfica Web (+2.5 pontos)
- ✅ Interface web moderna e responsiva
- ✅ Animações na exibição de resultados
- ✅ Comparação simultânea de todos os algoritmos
- ✅ Configuração dinâmica via interface
- ✅ Visualização interativa dos resultados

---

## 📂 ARQUIVOS ENTREGUES

### Código Fonte (src/)
```
src/main/java/com/example/p2so/
├── model/
│   ├── ProcessTask.java          # Modelo de processo (com atributos e métodos)
│   ├── ProcessStatus.java        # Estados do processo
│   ├── Configuration.java        # Configurações (quantum, aging)
│   ├── SchedulerResult.java      # Resultado do escalonamento
│   ├── TimelineEntry.java        # Entrada do timeline
│   └── ProcessMetrics.java       # Métricas de cada processo
│
├── scheduler/
│   ├── Scheduler.java            # Classe base abstrata
│   ├── FCFSScheduler.java        # Implementação FCFS
│   ├── SJFScheduler.java         # Implementação SJF
│   ├── SRTFScheduler.java        # Implementação SRTF
│   ├── PriorityNonPreemptiveScheduler.java
│   ├── PriorityPreemptiveScheduler.java
│   ├── RoundRobinScheduler.java
│   └── RoundRobinPriorityScheduler.java
│
├── service/
│   ├── ConfigurationService.java # Gerencia config.txt
│   └── SchedulerService.java     # Orquestra algoritmos
│
├── controller/
│   └── SchedulerController.java  # Controller REST/Web
│
└── P2SoApplication.java          # Classe principal
```

### Interface (templates/)
```
src/main/resources/templates/
└── index.html                    # Interface web com CSS e JavaScript
```

### Documentação
- ✅ **README.md** - Documentação completa e detalhada
- ✅ **INSTRUCOES.txt** - Como executar o projeto
- ✅ **ENTREGA.md** - Este arquivo (resumo da entrega)

### Configuração
- ✅ **config.txt** - Arquivo de configuração (quantum e aging)
- ✅ **pom.xml** - Dependências Maven

### Executável
- ✅ **target/P2SO-0.0.1-SNAPSHOT.jar** - JAR executável

### Scripts
- ✅ **executar.bat** - Script para executar a aplicação
- ✅ **compilar.bat** - Script para compilar o projeto

---

## 🚀 COMO EXECUTAR

### Opção 1: Usar o JAR compilado (MAIS FÁCIL)
```bash
# Duplo clique em executar.bat
# OU no terminal:
java -jar target/P2SO-0.0.1-SNAPSHOT.jar
```

Depois abra o navegador em: **http://localhost:8080**

### Opção 2: Compilar e executar
```bash
# Duplo clique em compilar.bat
# OU no terminal:
mvn clean package -DskipTests

# Depois execute:
java -jar target/P2SO-0.0.1-SNAPSHOT.jar
```

### Opção 3: Executar com Maven
```bash
mvn spring-boot:run
```

---

## 📊 ESTRUTURAS DE DADOS UTILIZADAS

### 1. ProcessTask (modelo de processo)
```java
Atributos:
- id: Identificador único
- arrivalTime: Instante de chegada
- burstTime: Tempo total de execução
- remainingTime: Tempo restante
- priority: Prioridade estática
- currentPriority: Prioridade atual (para aging)
- waitingTime, turnaroundTime, responseTime: Métricas
- hasStarted: Flag de início
- status: Estado atual (READY, RUNNING, WAITING, COMPLETED)
```

### 2. Configuration
```java
- quantum: Valor do quantum para Round-Robin
- agingRate: Taxa de envelhecimento
```

### 3. SchedulerResult
```java
- algorithmName: Nome do algoritmo
- averageTurnaroundTime: Tempo médio de vida
- averageWaitingTime: Tempo médio de espera
- contextSwitches: Número de trocas de contexto
- timeline: Lista de TimelineEntry (diagrama)
- processMetrics: Métricas individuais
```

---

## 🎨 PADRÕES DE PROJETO

1. **Strategy Pattern**: Cada algoritmo é uma estratégia
2. **Template Method**: Classe base Scheduler
3. **Factory Pattern**: SchedulerService cria schedulers
4. **MVC Pattern**: Model-View-Controller

---

## 📈 EXEMPLO DE USO

### Entrada:
```
0 5 2
0 2 3
1 4 1
3 3 4
```

### Configuração (config.txt):
```
quantum:2
aging:1
```

### Saída (para cada algoritmo):
- Tempo médio de vida (TT)
- Tempo médio de espera (TW)
- Número de trocas de contexto
- Diagrama de tempo visual

---

## 🏆 DIFERENCIAIS DO PROJETO

1. **Interface Gráfica Moderna**: Design responsivo com gradientes e animações
2. **Comparação Simultânea**: Executa e compara todos os 7 algoritmos
3. **Configuração Dinâmica**: Altera quantum e aging pela interface
4. **Código Limpo**: Totalmente comentado e seguindo boas práticas
5. **Documentação Completa**: README detalhado com exemplos
6. **Fácil Execução**: Scripts .bat para Windows
7. **Validação de Entrada**: Tratamento de erros robusto
8. **Resolução de Empates**: Implementada conforme especificação

---

## 📝 REGRAS IMPLEMENTADAS

### Envelhecimento (Aging)
- Aplicado a cada quantum
- Processos em espera ganham prioridade
- Processo que executa tem prioridade resetada

### Resolução de Empates
1. Mantém processo atual (evita troca de contexto)
2. Escolhe menor tempo restante
3. Escolha aleatória (seed fixo para reprodutibilidade)

### Trocas de Contexto
- Contabilizadas quando CPU troca de processo
- Exceto quando CPU estava ociosa

---

## 🔧 TECNOLOGIAS

- **Java 21**
- **Spring Boot 3.5.6**
- **Maven**
- **Thymeleaf**
- **HTML5 + CSS3 + JavaScript**

---

## ✨ MÉTRICAS CALCULADAS

### Turnaround Time (TT)
```
TT = Tempo de Conclusão - Tempo de Chegada
```

### Waiting Time (TW)
```
TW = Turnaround Time - Tempo de Execução
```

### Response Time (RT)
```
RT = Tempo da Primeira Execução - Tempo de Chegada
```

---

## 📞 SUPORTE

Para mais informações, consulte:
- **README.md** - Documentação completa
- **INSTRUCOES.txt** - Instruções de execução
- Comentários no código fonte

---

## ✅ CHECKLIST DE ENTREGA

- [x] 7 algoritmos implementados
- [x] Arquivo de configuração (config.txt)
- [x] Leitura da entrada padrão (formato especificado)
- [x] Cálculo de métricas (TT, TW, trocas de contexto)
- [x] Diagrama de tempo
- [x] Código comentado
- [x] Documentação das estruturas
- [x] Resolução de empates conforme especificação
- [x] Interface gráfica (+2.5 pontos bônus)
- [x] README completo
- [x] Executável funcional

---

**🎓 Trabalho desenvolvido para a disciplina de Sistemas Operacionais - UFC**

**Data:** Outubro de 2025

**Status:** ✅ COMPLETO E FUNCIONAL
