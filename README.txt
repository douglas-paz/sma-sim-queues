SMA Sim Queues - Simulador de filas

- Versão Final
Esta é a implementação final do simulador, capaz de realizar simulações com filas infinitas
e redes de filas com probabilidades de roteamento.

É possível obter os arquivos de configuração desta simulação na pasta /data.
O cenário para a homologação está descrito no arquivo teste_final.yml

O link para o vídeo da apresentação é o seguinte.
https://www.youtube.com/watch?v=yBN1otNpyXc


Conteúdo:
- /sim-final.jar:
    Executável desta implementação.
    para usar: java -jar sim-final.jar <spec_yml>

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
    Estacionariedade.yml: Testa o comportamento de filas infinitas com lambda > mu.
    FilaSimples1.yml: Arquivo de configuração da simulação.
    FilasProbRotacao.yml: Cenário básico de uma fila com roteamento aleatório
    ParDeFilas.yml: Cenário para duas filas em tandem.
    Splitter.yml: Testa o comportamento de uma rede de filas em árvore
    SplitterRandom.yml: Mesmo cenário acima com configuração de aleatórios diferentes.


