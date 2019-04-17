#!/bin/bash
# author: Ren, Xie

# env_args="-Xms128m -Xmx128m"
env_args=""
sleeptime=0
arglen=$#
jarname='example-executable.jar'

get_pid(){
    pname="`find ./ -name $jarname`"
    pname=${pname:3}
    pid=`ps -ef | grep $(basename $pname) | grep -v grep | awk '{print $2}'`
    echo "$pid"
}

startup(){
    mvn clean package -DskipTests
    # mvn clean install -Dmaven.test.skip=true
    pid=$(get_pid)
    if [ "$pid" != "" ]
    then
        echo "APP already startup!"
    else
        jar_path=`find ./ -name $jarname`
        cmd="nohup java $1 -jar $jar_path > ./example.out < /dev/null &"
        echo "cmd: $cmd"
        nohup java $1 -jar $jar_path > ./example.out < /dev/null &
        pid=$(get_pid)
        echo "pid: $pid" >> ./example.out
        show_log
    fi
}

shut_down(){
    pid=$(get_pid)
    if [ "$pid" != "" ]
    then
        curl -X POST http://localhost:58080/MyActuator/shutdown
        pid=$(get_pid)
        if [ "$pid" != "" ]
        then
            kill -9 $pid
            echo -e "\r\nkill process!"
        else
            echo -e "\r\nAPP is stop!"
        fi
    else
        echo "APP is not running!"
    fi
}

show_log(){
    tail -f example.out
}

show_status(){
    pid=$(get_pid)
    if [ "$pid" != "" ]
    then
        echo "APP is running with pid: $pid"
    else
        echo "APP is not running!"
    fi
}

show_help(){
    echo -e "\r\n\t欢迎使用APP"
    echo -e "\r\nUsage: sh exec.sh start|stop|reload|status|log|pid"
    exit
}

if [ $arglen -eq 0 ]
 then
    show_help
else
    if [ "$2" != "" ]
    then
        env_args="$2"
    fi
    case "$1" in
        "pid")
            get_pid
            ;;
        "start")
            startup "$env_args"
            ;;
        "stop")
            shut_down
            ;;
        "reload")
            echo "reload"
            ;;
        "status")
            show_status
            ;;
        "log")
            show_log
            ;;
    esac
fi