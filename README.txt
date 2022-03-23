SMA Sim Queues - Simulador de filas

- Versão 1
Esta é a implementação inicial do algoritmo de simulação de filas simples.
A implementação executa os seguintes cenários de testes:
- Fila G/G/1/5 com chegadas entre 2 e 4 e saídas entre 3 e 5.
- Fila G/G/2/5 com chegadas entre 2 e 4 e saídas entre 3 e 5.

A fim de testar a corretude das simulações mais facilmente, o executável implementa
uma simulação com os seguintes parâmetros:

Parâmetros:
- Servidores = 1
- Capacidade = 5
- Tempo mínimo de chegada = 2
- Tempo máximo de chegada = 4
- Tempo mínimo de saída = 3
- Tempo máximo de saída = 5
- Tempo do primeiro evento  = 3
- Randoms = 0.2176f, 0.0103f, 0.1109f, 0.3456f, 0.9910f, 0.2323f, 0.9211f, 0.0322f, 0.1211f, 0.5131f, 0.7208f, 0.9172f, 0.9922f, 0.8324f, 0.5011f, 0.2931f

É possível obter os arquivos de configuração desta simulação na pasta /data.


Conteúdo:
- /sma-simulator-1.0.jar:
    Executável desta implementação.

- tools/sma-random-1.0.jar:
    Gerador de números aleatórios.
    para usar: java -jar sma-random-1.0.jar <iteracoes> <semente>

    Random: Congruente Linear
    Parametros:
        a = 191
        c = 2147483647
        m = 9223372036854775807

- tools/simulator.jar:
    Simulador genérico para referência
    para usar: java -jar simulador.jar

- data/:
    FilaSimples1.yml: Arquivo de configuração da simulação.
    random_list.dat: Lista de randoms para comparação
    test1.txt: Saída da simulação da fila G/G/1/5 para 5 execuções com sementes arbitrárias
    test2.txt: Saída da simulação da fila G/G/2/5 para 5 execuções com sementes arbitrárias


- reports/:
    Plotagem dos gráficos de dispersão para cada uma das execuções (gnuplot);
