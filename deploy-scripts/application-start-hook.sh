#!/bin/bash

APP_DIR=/home/ec2-user/app/backend/build/libs
JAR_NAME=backend-0.0.1-SNAPSHOT.jar

# 기존 실행 중인 서버 종료
pkill -f $JAR_NAME || true

# 서버 실행 (백그라운드)
nohup java -jar $APP_DIR/$JAR_NAME > /home/ec2-user/app/app.log 2>&1 &

# nginx 재시작
sudo systemctl restart nginx
