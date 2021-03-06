# SGA - Sistema de Gestão de Atividades


## Como executar

Execute o script *start_db.bat" via linha de comando ou gerenciador de arquivos, para iniciar o banco de dados local.

Em seguida execute *run.bat* para iniciar a aplicação.

Após fechar a aplicação, encerre o banco de dados com o script *shutdown_db.bat*.

## Como editar

Baixe e instale o [IntelliJ IDEA](https://www.jetbrains.com/idea/).

Abra o programa e selecione a opção: importar projeto do VCS.

Copie o link do repositório no github [aqui](https://github.com/krayfaus/sistema-de-gestao) e cole no campo relacionado.

O projeto será importado automaticamente pela IDE e as dependências serão baixadas pelo Maven.

## Dependências

O projeto utiliza as seguintes bibliotecas Java:

* [JavaFX](https://openjfx.io/)
* [MongoDB Java Drivers](https://mongodb.github.io/mongo-java-driver/)
* [JUnit 5](https://junit.org/junit5/)
* [GSON](https://github.com/google/gson)
