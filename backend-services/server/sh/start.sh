d1=`date +%Y%m%d-%H%M%S`
if [ ! -d "bak" ]; then
  mkdir bak
fi
cp server-1.0-SNAPSHOT.jar bak/server-1.0-SNAPSHOT_${d1}.jar

PID=`ps axu | grep java | grep server-1.0-SNAPSHOT.jar |grep -v grep| awk '{printf $2}'`

if [ -n "$PID" ]
then
    kill -9 $PID
fi
java -jar -Xms256M -Xmx256M -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=inf.hprof server-1.0-SNAPSHOT.jar  >/dev/null &
