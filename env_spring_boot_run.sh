#!/bin/bash
if [ -e .env ]
then
    echo "Found .env.dev file with "$(wc -l .env)" lines"
else
    echo "You must define a .env.dev file !!"
    exit 1
fi
mvn clean
env $(cat .env) mvn spring-boot:run
