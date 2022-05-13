#!/bin/bash
export SERVICE_NAME=reactive-server
export SERVICE_HOME=/opt/web/reactive-server
export PATH=$JAVA_HOME/bin:$PATH
export CLASSPATH=.:$CLASSPATH:$SERVICE_HOME/lib/reactive-server.jar
export JAVA_OPTS="$JAVA_OPTS $JAVA_OPTS_ENV "
export JAVA_OPTS="$JAVA_OPTS -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=$SERVICE_HOME/dump "
export JAVA_OPTS="$JAVA_OPTS -Djava.library.path=$SERVICE_HOME/lib -Djava.net.preferIPv4Stack=true"


cd $SERVICE_HOME
java $JAVA_OPTS -cp $CLASSPATH -jar lib/reactive-server.jar