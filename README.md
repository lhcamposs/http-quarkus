# Sistema Distribuído de Mensagens - Quarkus

Este projeto é uma implementação de uma aplicação REST simples utilizando o framework Quarkus, explorando o protocolo HTTP como mecanismo de comunicação direta entre processos (modelo send/receive).

## 4.1. Arquitetura da Solução

### Fluxo da Requisição POST `/mensagens`
Quando um cliente deseja enviar uma nova mensagem, a comunicação direta entre processos ocorre da seguinte forma:

1. **Sender (Cliente/Postman)**: O processo cliente constrói uma requisição HTTP. Ele encapsula os dados da mensagem (`remetente` e `conteudo`) no corpo (Body) da requisição no formato JSON e estabelece uma conexão TCP com o servidor na porta especificada (ex: 8080).
2. **Protocolo HTTP**: Atua como o meio de transporte, definindo as regras de formatação. O cabeçalho carrega metadados (como `Content-Type: application/json`) e o método `POST` indica a intenção de criar um recurso.
3. **Receiver (Servidor/Quarkus)**: A aplicação Quarkus (rodando via servidor embutido, como o Vert.x/RESTEasy) escuta a porta, aceita a conexão, desserializa o JSON recebido para o objeto Java `Mensagem`, atribui o ID e o Timestamp, salva na memória e devolve uma resposta HTTP de confirmação ao Sender.

### Mapeamento Teórico: Métodos HTTP x Send/Receive
Os métodos HTTP utilizados mapeiam diretamente para as primitivas de comunicação de sistemas distribuídos:
* **GET**: Uma operação onde o *Sender* solicita o estado atual de um recurso e aguarda o *Receive* da resposta com os dados (ex: todas as mensagens ou uma específica).
* **POST**: Uma operação onde o *Sender* envia uma nova informação de estado para o processo remoto e faz um *Receive* da confirmação (ACK) de que o recurso foi criado com sucesso.
* **DELETE**: O *Sender* emite um comando de destruição de estado para o processo remoto e aguarda o *Receive* confirmando o sucesso da operação.

---

## 4.2. Evidências de Funcionamento

### Tabela de Testes

| Método | Rota | Descrição | Evidência (Print) |
|---|---|---|---|
| **POST** | `/mensagens` | Cria uma nova mensagem | [INSERIR PRINT DO POSTMAN AQUI] |
| **GET** | `/mensagens` | Retorna todas as mensagens | [INSERIR PRINT DO POSTMAN AQUI] |
| **GET** | `/mensagens/{id}` | Busca mensagem válida | [INSERIR PRINT DO POSTMAN AQUI] |
| **GET** | `/mensagens/{id}` | Busca mensagem inválida | [INSERIR PRINT DO POSTMAN AQUI] |
| **DELETE** | `/mensagens/{id}`| Remove mensagem existente | [INSERIR PRINT DO POSTMAN AQUI] |

### Justificativa dos Status Codes (HTTP)

* **200 OK**: Utilizado nos endpoints `GET` para indicar que a requisição de busca foi processada com sucesso e o corpo da resposta contém os dados solicitados. Também utilizado no `DELETE` bem-sucedido.
* **201 Created**: Utilizado no endpoint `POST` para confirmar que a requisição foi bem-sucedida e que um novo recurso (a mensagem) foi criado no servidor de forma síncrona.
* **404 Not Found**: Utilizado nos endpoints `GET /{id}` e `DELETE /{id}` quando o identificador fornecido pelo cliente não existe na lista em memória, informando corretamente que o recurso alvo da operação é inválido.