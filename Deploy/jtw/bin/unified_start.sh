#!/bin/bash
CUR_PATH=$(cd `dirname $0`; pwd);
APP_DIR=$CUR_PATH/../repo/
nohup java -jar $APP_DIR/microservice-unified-center-0.0.1-SNAPSHOT.jar &