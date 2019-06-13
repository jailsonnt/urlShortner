# urlShortener - Encurtador de URLs

Em resposta ao teste da SeniorSistemas - https://github.com/ledbruno/desafios/tree/master/1%20-%20Easy/Encurtador%20de%20URL

# Stack utilizado

Foi utilizado Java 8 com SpringBoot, banco de dados MariaDB

# Funcionalidades

Todas as funcionalidades requisitadas foram implementadas.

# Critérios de avaliação

Todos os critérios de avaliação foram abordados com excessão dos testes de integração (foram utilizados somente testes unitários)

Obs.:Devido ao ítem "Rodar o projeto com o mínimo de configuração/setup. Gostamos de projetos que rodam de forma simples e rápida." as propriedades do projeto fazem com que a tabela necessária seja criada automaticamente

# Pré requisitos para executar o projeto

-Possuir o banco de dados MariaDB instalado (banco de dados utilizado em meus testes) e um database vazio criado

-JVM na versão 8 instalado

-Váriaveis de ambiente do java devidamente configuradas

-Usuário no banco de dados com acesso a criação de tabelas e insert, update, select

# Como executar o projeto

O projeto utiliza gradle, para executar os projetos serão necessários executar os seguintes passos.

1 - Clonar ou baixar o repositório

2 - utilizando um terminal, navegar até a pasta do projeto.

3-  Caso o SO seja windows utilizar o comando gradlew build. Caso o SO seja linux utilizar o comando sh gradlew build

4-  Navegar até a subpasta do projeto "\build\libs" 

5-  Copiar dentro da pasta que acessou no ítem 4 os arquivos dentro application.properties e log4j2.xml que estarão na subpasta do 
projeto "\src\main\resources"

5.5-  O arquivo application.properties deve ser editado conforme sua vontade e necessidade. Instruções de como editar o arquivo estarão na sessão "application.properties"

6-  Ainda no terminal dentro da subpasta do projeto "\build\libs" executar o comando "java -jar urlShortner.jar"

7-  Após verificar no log a mensagem "UrlShortnerApplication - Started UrlShortnerApplication" o sistema já estará apto a receber requisições.

# application.properties

O arquivo application.properties está na subpasta do projeto "\src\main\resources" e deve ser colocado na mesma pasta que o jar conforme instruções da seção acima.

Abaixo será explicado como personalizar cada uma das propriedades

* spring.datasource.url=jdbc:mariadb://localhost:3306/url_shortner?useTimezone=true&serverTimezone=UTC * Configurar conforme a instalação do banco de dados que irá utilizar
* spring.datasource.username=root * Configurar conforme usuários do seu banco. Obs.: o Usuario precisa ter permisão para create, insert, update, select
* spring.datasource.password=warning * Configurar conforme usuários do seu banco
* spring.datasource.driver-class-name=org.mariadb.jdbc.Driver * Manter assim
* spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5InnoDBDialect * Manter assim
* server.port=8443 * Configurar conforme a porta que deseje subir a aplicação
* spring.jpa.generate-ddl=true * Manter assim
* spring.jpa.hibernate.ddl-auto=update * Manter assim
* urlShortener.baseurl=http://localhost:8443 * Configurar conforme máquina que estará hospedando o serviço ou conforme configuração de DNS
* urlShortener.days-of-validity-of-Url=5  * Parametriza o padrão para expirar cada URL gerada (em dias)

# Como carregar o projeto na IDE

O projeto foi feito utilizando gradle, portanto para carrega-lo em uma IDE basta que essa IDE tenha um plugin do gradle e com esse plugin importar o projeto. 

Obs.: A IDE que utilizei no desenvolvimento foi uma adaptação do eclipse (Spring Tools Suite 4)

# Cloud

O serviço está rodando temporariamente (ficará online até que confirme o fim da avaliação) na URL: 

* http://ec2-34-216-155-151.us-west-2.compute.amazonaws.com:8443

utilizando os serviços de: 

* Amazon-aws-ec2

# Exemplos

* POST: http://ec2-34-216-155-151.us-west-2.compute.amazonaws.com:8443?originalUrl=https://www.google.com/

para 'encurtar' utilizando a validade padrão

* POST: http://ec2-34-216-155-151.us-west-2.compute.amazonaws.com:8443?originalUrl=https://www.baeldung.com/spring-data-jpa-query&daysOfValidity=0

para encurtar e especificar a data de validade basta usar o parâmetro daysOfValidity

no exemplo acima a API retornou o JSON 

{
    "newUrl": "http://ec2-34-216-155-151.us-west-2.compute.amazonaws.com:8443/short4",
    "expiresAt": "1560433607354"
}

não estará válido pois defini daysOfValidity=0


