#!/bin/bash
CUR_PATH=$(cd `dirname $0`; pwd);
APP_DIR=$CUR_PATH/../repo/
nohup java -jar $APP_DIR/spring-ribbon-0.0.1-SNAPSHOT.jar &