#!/bin/bash

APP_DIR=/home/ec2-user/app/backend/build/libs

echo "Stopping existing Spring Boot app..."
pkill -f 'java -jar' || true

echo "Starting Spring Boot app..."
cd $APP_DIR
nohup java -jar *.jar > app.log 2>&1 &
