#!/usr/bin/env bash
echo "Limpando projeto"
mvn clean

echo "Construindo projeto"
mvn install

echo "Executando chat"
java -jar target/chat-smb-jar-with-dependencies.jar



