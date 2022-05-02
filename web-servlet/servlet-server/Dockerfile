FROM circleci/jdk8:0.1.1

#set repository name

ARG SERVER_NAME=servlet-server

#set work path
WORKDIR /opt/web/${SERVER_NAME}

ADD ./target/${SERVER_NAME}.jar /opt/web/${SERVER_NAME}/lib/
ADD ./docker /opt/web/${SERVER_NAME}/docker

EXPOSE 8080

CMD sh docker/startdocker.sh