# 🖥️ Simulador de Escalonamento de Processos

**Disciplina:** Sistemas Operacionais
**Instituição:** Universidade Federal do Ceará (UFC)
**Atividade:** Prática 02 - Escalonamento de Processos

## 📋 Sumário

- [Sobre o Projeto](#sobre-o-projeto)
- [Funcionalidades](#funcionalidades)
- [Algoritmos Implementados](#algoritmos-implementados)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Como Executar](#como-executar)
- [Como Usar](#como-usar)
- [Formato de Entrada](#formato-de-entrada)
- [Métricas Calculadas](#métricas-calculadas)
- [Exemplos](#exemplos)
- [Arquitetura](#arquitetura)

## 📖 Sobre o Projeto

Este projeto implementa um **simulador completo de escalonamento de processos** com interface gráfica web interativa e animada. O simulador permite visualizar e comparar o comportamento de 7 diferentes algoritmos de escalonamento, exibindo métricas detalhadas e diagramas de tempo de execução.

O projeto foi desenvolvido em **Java com Spring Boot** e possui uma interface web moderna e responsiva com animações em tempo real.

## ✨ Funcionalidades

- ✅ **7 Algoritmos de Escalonamento** implementados
- ✅ **Interface Gráfica Web** moderna e responsiva
- ✅ **Animações** na exibição de resultados
- ✅ **Configuração Dinâmica** de quantum e taxa de envelhecimento
- ✅ **Cálculo Automático** de métricas (tempo de vida, tempo de espera, trocas de contexto)
- ✅ **Diagrama de Tempo** visual para cada algoritmo
- ✅ **Comparação Simultânea** de todos os algoritmos
- ✅ **Validação de Entrada** e tratamento de erros
- ✅ **Arquivo de Configuração** (config.txt) para parâmetros

## 🔧 Algoritmos Implementados

### 1. FCFS (First Come, First Served)
**Tipo:** Não-preemptivo
**Descrição:** Processos são executados na ordem de chegada. O primeiro processo a chegar é o primeiro a ser executado até sua conclusão.

### 2. SJF (Shortest Job First)
**Tipo:** Não-preemptivo
**Descrição:** Seleciona o processo com menor tempo total de execução. Uma vez iniciado, o processo executa até completar.

### 3. SRTF (Shortest Remaining Time First)
**Tipo:** Preemptivo
**Descrição:** Versão preemptiva do SJF. Seleciona o processo com menor tempo restante de execução. Pode ser interrompido se um processo com tempo menor chegar.

### 4. Prioridade (sem preempção)
**Tipo:** Não-preemptivo
**Descrição:** Seleciona o processo com maior prioridade (menor valor numérico). Uma vez iniciado, executa até completar.

### 5. Prioridade (com preempção)
**Tipo:** Preemptivo
**Descrição:** Versão preemptiva do algoritmo de prioridade. Um processo pode ser interrompido se outro com prioridade maior chegar.

### 6. Round-Robin (sem prioridade)
**Tipo:** Preemptivo por quantum
**Descrição:** Cada processo executa por um quantum de tempo. Após o quantum, o processo vai para o final da fila.

### 7. Round-Robin com Prioridade e Envelhecimento
**Tipo:** Preemptivo por quantum
**Descrição:** Similar ao Round-Robin, mas considera prioridades. Processos que esperam são envelhecidos (ganham prioridade) a cada quantum para evitar starvation.

## 📁 Estrutura do Projeto

```
P2SO/
├── src/
│   ├── main/
│   │   ├── java/com/example/p2so/
│   │   │   ├── controller/
│   │   │   │   └── SchedulerController.java      # Controller REST e páginas web
│   │   │   ├── model/
│   │   │   │   ├── Configuration.java            # Modelo de configuração
│   │   │   │   ├── Process.java                  # Modelo de processo
│   │   │   │   ├── ProcessStatus.java            # Enum de status do processo
│   │   │   │   ├── ProcessMetrics.java           # Métricas individuais de processo
│   │   │   │   ├── SchedulerResult.java          # Resultado do escalonamento
│   │   │   │   └── TimelineEntry.java            # Entrada do diagrama de tempo
│   │   │   ├── scheduler/
│   │   │   │   ├── Scheduler.java                # Classe base abstrata
│   │   │   │   ├── FCFSScheduler.java            # Implementação FCFS
│   │   │   │   ├── SJFScheduler.java             # Implementação SJF
│   │   │   │   ├── SRTFScheduler.java            # Implementação SRTF
│   │   │   │   ├── PriorityNonPreemptiveScheduler.java
│   │   │   │   ├── PriorityPreemptiveScheduler.java
│   │   │   │   ├── RoundRobinScheduler.java      # Round-Robin básico
│   │   │   │   └── RoundRobinPriorityScheduler.java
│   │   │   ├── service/
│   │   │   │   ├── ConfigurationService.java     # Gerencia config.txt
│   │   │   │   └── SchedulerService.java         # Orquestra os algoritmos
│   │   │   └── P2SoApplication.java              # Classe principal Spring Boot
│   │   └── resources/
│   │       ├── templates/
│   │       │   └── index.html                    # Interface web principal
│   │       └── application.properties            # Configurações Spring
│   └── test/
│       └── resources/
│           └── Pratica02 - Escalonamento de Processos.pdf
├── config.txt                                     # Arquivo de configuração
├── pom.xml                                        # Dependências Maven
└── README.md                                      # Este arquivo
```

## 🛠️ Tecnologias Utilizadas

### Backend
- **Java 21** - Linguagem de programação
- **Spring Boot 3.5.6** - Framework web
- **Spring Web** - REST API
- **Thymeleaf** - Template engine
- **Maven** - Gerenciamento de dependências

### Frontend
- **HTML5** - Estrutura
- **CSS3** - Estilização com gradientes e animações
- **JavaScript (Vanilla)** - Interatividade e chamadas AJAX

## 🚀 Como Executar

### Pré-requisitos

- Java 21 ou superior
- Maven 3.6+

### Passo a Passo

1. **Clone ou navegue até o diretório do projeto:**
   ```bash
   cd P2SO
   ```

2. **Compile o projeto:**
   ```bash
   mvnw clean package
   ```

3. **Execute a aplicação:**
   ```bash
   mvnw spring-boot:run
   ```

   Ou execute o JAR gerado:
   ```bash
   java -jar target/P2SO-0.0.1-SNAPSHOT.jar
   ```

4. **Acesse a interface web:**
   Abra seu navegador e acesse: `http://localhost:8080`

## 📝 Como Usar

### Interface Web

1. **Configure os parâmetros:**
   - **Quantum:** Tempo de execução para algoritmos Round-Robin (padrão: 2)
   - **Taxa de Envelhecimento:** Incremento de prioridade por quantum (padrão: 1)

2. **Insira os processos:**
   - Formato: `tempo_chegada duração prioridade`
   - Um processo por linha
   - Exemplo:
     ```
     0 5 2
     0 2 3
     1 4 1
     3 3 4
     ```

3. **Execute a simulação:**
   - Clique em "▶️ Executar Simulação"
   - Os resultados aparecerão com animações

4. **Analise os resultados:**
   - Métricas: Tempo médio de vida, tempo médio de espera, trocas de contexto
   - Diagrama de tempo visual para cada algoritmo

### Arquivo de Configuração

O arquivo `config.txt` armazena as configurações padrão:

```
quantum:2
aging:1
```

Você pode editar este arquivo manualmente ou através da interface web.

## 📊 Formato de Entrada

Cada linha representa um processo com 3 valores separados por espaços:

```
<instante_criação> <duração> <prioridade>
```

- **instante_criação:** Tempo em que o processo chega (inteiro >= 0)
- **duração:** Tempo total de execução em segundos (inteiro > 0)
- **prioridade:** Prioridade estática (inteiro > 0, menor = maior prioridade)

**Exemplo:**
```
0 5 2   → P1: chega em t=0, executa por 5s, prioridade 2
0 2 3   → P2: chega em t=0, executa por 2s, prioridade 3
1 4 1   → P3: chega em t=1, executa por 4s, prioridade 1
3 3 4   → P4: chega em t=3, executa por 3s, prioridade 4
```

## 📈 Métricas Calculadas

### 1. Turnaround Time (TT) - Tempo de Vida
**Fórmula:** `TT = Tempo_Conclusão - Tempo_Chegada`
**Descrição:** Tempo total que o processo permanece no sistema.

### 2. Waiting Time (TW) - Tempo de Espera
**Fórmula:** `TW = TT - Tempo_Execução`
**Descrição:** Tempo que o processo fica esperando na fila.

### 3. Response Time - Tempo de Resposta
**Fórmula:** `RT = Tempo_Primeira_Execução - Tempo_Chegada`
**Descrição:** Tempo até o processo começar a executar pela primeira vez.

### 4. Context Switches - Trocas de Contexto
**Descrição:** Número de vezes que a CPU troca de um processo para outro.

## 🔍 Exemplos

### Exemplo 1: Entrada Básica

**Entrada:**
```
0 5 2
0 2 3
1 4 1
3 3 4
```

**Processos:**
- P1: Chega em 0, executa por 5s, prioridade 2
- P2: Chega em 0, executa por 2s, prioridade 3
- P3: Chega em 1, executa por 4s, prioridade 1
- P4: Chega em 3, executa por 3s, prioridade 4

**Resultado esperado (FCFS):**
```
tempo   P1  P2  P3  P4
 0- 5   ##  --  --
 5- 7   --  ##  --  --
 7-11   --  --  ##  --
11-14   --  --  --  ##
```

### Exemplo 2: Teste de Prioridade

**Entrada:**
```
0 3 3
0 1 1
0 2 2
```

**Algoritmo:** Prioridade (sem preempção)

**Ordem de execução:** P2 (prioridade 1) → P3 (prioridade 2) → P1 (prioridade 3)

## 🏗️ Arquitetura

### Camadas da Aplicação

#### 1. **Model (Modelo)**
Contém as entidades de domínio:
- `Process`: Representa um processo com todos os seus atributos
- `Configuration`: Armazena quantum e aging
- `SchedulerResult`: Resultado completo de um algoritmo
- `TimelineEntry`: Uma entrada no diagrama de tempo
- `ProcessMetrics`: Métricas individuais de cada processo

#### 2. **Scheduler (Algoritmos)**
Implementações dos algoritmos de escalonamento:
- Classe abstrata `Scheduler` com métodos comuns
- 7 implementações concretas, uma para cada algoritmo
- Método `schedule()` retorna `SchedulerResult`

#### 3. **Service (Serviços)**
Lógica de negócio:
- `ConfigurationService`: Gerencia leitura/escrita de config.txt
- `SchedulerService`: Orquestra a execução dos algoritmos

#### 4. **Controller (Controladores)**
Endpoints REST e renderização de páginas:
- `SchedulerController`: Expõe API REST e serve interface web

### Padrões de Projeto Utilizados

1. **Strategy Pattern**: Cada algoritmo é uma estratégia diferente
2. **Factory Pattern**: `SchedulerService` cria os schedulers apropriados
3. **Template Method**: Classe `Scheduler` define estrutura comum
4. **MVC Pattern**: Separação entre Model, View (Thymeleaf) e Controller

### Decisões de Implementação

#### Resolução de Empates
Quando múltiplos processos têm a mesma prioridade/tempo, a seleção segue:
1. **Processo atual** (evita troca de contexto)
2. **Menor tempo restante**
3. **Escolha aleatória** (seed fixo para reprodutibilidade)

#### Envelhecimento (Aging)
No Round-Robin com prioridade:
- Aplicado a cada quantum nos processos em espera
- Diminui o valor numérico da prioridade (aumenta prioridade real)
- Processo que executa tem prioridade resetada

#### Trocas de Contexto
Contabilizadas sempre que a CPU troca de um processo para outro diferente.

## 📦 Gerando o Executável

Para gerar um JAR executável:

```bash
mvnw clean package
```

O arquivo será gerado em: `target/P2SO-0.0.1-SNAPSHOT.jar`

Para executar:
```bash
java -jar target/P2SO-0.0.1-SNAPSHOT.jar
```

## 🎯 Objetivos Alcançados

- ✅ Implementação de todos os 7 algoritmos solicitados
- ✅ Cálculo correto de métricas (TT, TW, trocas de contexto)
- ✅ Geração de diagrama de tempo
- ✅ Leitura de configuração de arquivo texto
- ✅ Interface gráfica web moderna com animações (+2.5 pontos bônus)
- ✅ Código comentado e documentado
- ✅ Estrutura organizada seguindo boas práticas
- ✅ README completo e detalhado

## 👥 Informações Acadêmicas

**Universidade:** Universidade Federal do Ceará (UFC)
**Departamento:** Departamento de Computação
**Disciplina:** Sistemas Operacionais
**Atividade:** Prática 02 - Escalonamento de Processos

## 📄 Licença

Este projeto foi desenvolvido para fins acadêmicos.

---

**Desenvolvido com ❤️ para a disciplina de Sistemas Operacionais - UFC**
