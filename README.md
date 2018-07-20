# Monitoring System

O Monitoring System é uma infraestrutura de monitoramento de temperatura e umidade relativa desenvolvida com o objetivo de monitorar o ambiente climático de lugares que possuem equipamentos que exigem o controle de temperatura e umidade relativa.

Todos os detalhes de implementação e implantação da infraestrutura pode ser lida através do artigo **[Infraestrutura de Monitoramento de Temperatura e Umidade Relativa em Centro de Processamento de Dados](https://github.com/michelmotta/monitoring_system/blob/master/article.pdf)**

A infraestrutura do Monitoring System é composta por três subsistemas

* [Subsistema Android](https://github.com/michelmotta/monitoring_system/tree/master/subsistema_android)
* [Subsistema Arduino](https://github.com/michelmotta/monitoring_system/tree/master/subsistema_arduino)
* [Subsistema Servidor](https://github.com/michelmotta/monitoring_system/tree/master/subsistema_servidor)

### Subsistema Android

O subsistema Android é um aplicativo desenvolvido para a plataforma Android que funciona como uma aplicação cliente do subsistema Servidor. Pelo aplicativo é possível acompanhar os valores de temperatura e umidade relativa que são fornecidas pelo subsistema Servidor e alterar valores de configuração como limites de temperatura e umidade relativa máxima e mínima aceitáveis.

### Subsistema Arduino

O subsistema Arduino é um arquivo com a extensão .ino que deve ser carregado para a memória de placas Wemos D1 ou compatíveis Arduino. O código fonte gerencia a forma como a placa recebe conexões HTTP a o tipo de resposta que será devolvido para o solicitante. Caso a solicitação HTTP seja feita na url definida como /api, a resposta será Json com os valores de temperatura e umidade relativa obtidas através do sensor. 

### Subsistema Servidor

![IDiagrama de Implantação do Monitoring System](https://github.com/michelmotta/monitoring_system/blob/master/deployment_diagram.png)