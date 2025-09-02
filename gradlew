#!/bin/bash
# Скрипт для запуска Gradle
exec java -Xmx2048m -Dfile.encoding=UTF-8 -jar ./gradle/wrapper/gradle-wrapper.jar "$@"
