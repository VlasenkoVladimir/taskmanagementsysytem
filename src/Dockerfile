FROM openjdk:17
#Записываем в переменную путь до варника (необязательно)
#ARG jarFile=target/SpringProjectLibrary-0.1.war
#Куда переместить варник внутри контейнера
WORKDIR /opt/app
COPY ${jarFile} library.war
EXPOSE 9090
ENTRYPOINT ["java", "-jar", "taskManagementSystem.war"]
