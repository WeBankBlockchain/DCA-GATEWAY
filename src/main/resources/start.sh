#!/bin/bash

SYS_PORT=17141
JAVA_OPTS+=" -Xmx1024m -Xms1024m -XX:MetaspaceSize=128m -XX:NewRatio=5"
JAVA_OPTS+=" -XX:+UseConcMarkSweepGC -XX:+UseParNewGC"
JAVA_OPTS+=" -XX:+UseCompressedClassPointers -XX:+UseCompressedOops"
JAVA_OPTS+=" -XX:AutoBoxCacheMax=200 -XX:CMSInitiatingOccupancyFraction=70 -XX:CompileThreshold=20000 -XX:MaxTenuringThreshold=10"
JAVA_OPTS+=" -XX:+ParallelRefProcEnabled -XX:+DisableExplicitGC -XX:-UseBiasedLocking -XX:+UseCMSInitiatingOccupancyOnly"
JAVA_OPTS+=" -XX:OldPLABSize=16 -XX:-OmitStackTraceInFastThrow"
JAVA_OPTS+=" -XX:+PrintCommandLineFlags -XX:+PrintGCDateStamps"
JAVA_OPTS+=" -XX:+PrintGC -XX:+PrintGCApplicationConcurrentTime -XX:+PrintGCApplicationStoppedTime -XX:+PrintGCDateStamps"
JAVA_OPTS+=" -XX:+PrintGCDetails -XX:+PrintGCID -XX:+PrintGCTaskTimeStamps"
JAVA_OPTS+=" -XX:+PrintGCTimeStamps -XX:+PrintPromotionFailure"
JAVA_OPTS+=" -XX:+PrintSafepointStatistics -XX:PrintSafepointStatisticsCount=1"
JAVA_OPTS+=" -XX:ErrorFile=/data/app/logs/dca-gateway/java_error_%p.log -XX:+ExplicitGCInvokesConcurrent"
JAVA_OPTS+=" -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/data/app/logs/dca-gateway"
JAVA_OPTS+=" -verbose:gc -Xloggc:/data/app/logs/dca-gateway/jvm_gc.log"

echo "nohup /nemo/jdk1.8.0_141/bin/java -jar $JAVA_OPTS `pwd`/dca-gateway_1779_1_3_0.jar >/dev/null 2>&1 &"
nohup /nemo/jdk1.8.0_141/bin/java -jar $JAVA_OPTS `pwd`/dca-gateway_1779_1_3_0.jar  >/dev/null &
sleep 8s
tail -n 150 /data/app/logs/dca-gateway/dca-gateway.log
