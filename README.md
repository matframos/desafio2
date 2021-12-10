### Desafio Dock Tech

O contrato deste desfio se encontra no arquivo swagger.yaml da raiz do projeto. A partir deste, temos
descritos todos os recursos disponibilizados para uso através da API.

Para melhor visualização, abra o contrato em <https://editor.swagger.io/>.

### Frameworks / Stack Utilizada

* Maven 3.8.1
* Spring 5
* Spring Boot 2.5.0
* PostgreSQL 14.1
* JUnit 5 

### Montando o ambiente local de desenvolvimento

Passo 1:
Instale e deixe o Docker UP na sua maquina.

Passo 2:
Instale o MAVEN.

Passo 3: 
<b>mvn clean install -e -Pdocker-create -Pdocker-compose</b>

* <i><b>mvn clean install -e</b></i>: compila, testa e empacota a aplicação
* <i><b>-Pdocker-create</b></i>: realiza o pull da imagem da aplicação no repositorio local do docker
* <i><b>-Pdocker-compose</b></i>: realiza a construção de todo o ambiente (PostgreSQL, setup de scripts e Aplicação) no docker

### POSTMAN

Para auxilio e execução local, uma collection com todas as requisiçoes esta disponibilizada na raiz do projeto. Basta abrir o 
arquivo Desafio2.postman_collection.json no POSTMAN.

### Como utilizar a API

1. Na tabela pessoa, foram cadastrados dois CPFs no setup inicial que podem ser utilizados para criar uma conta - 65961038033 e 87304306076.
2. Após criar uma conta com sucesso, utilize o valor do campo idConta do objeto de retorno no path dos demais recursos da API, por exemplo, consulta de saldo, saque e deposito.