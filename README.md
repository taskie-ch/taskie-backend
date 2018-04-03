# Taskie Server
[![Build Status](https://travis-ci.org/taskie-ch/taskie-backend.svg?branch=master)](https://travis-ci.org/taskie-ch/taskie-backend)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/a646471fb0b74ccb81608fc15261424c)](https://www.codacy.com/app/tommyknows/taskie-backend?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=taskie-ch/taskie-backend&amp;utm_campaign=Badge_Grade)

How to start the Taskie Server application
---

1. Run `mvn clean install` to build your application
1. Start application with `java -jar target/taskie-backend-1.0-SNAPSHOT.jar server config.yml`
1. To check that your application is running enter url `http://localhost:8080`

Health Check
---

To see your applications health enter url `http://localhost:8081/healthcheck`



## Example

Set a rotation schedule for a task

    curl -H "Content-Type: application/json" -X POST http://localhost:9090/taskSchedule/1 -d '{"dateToUser":"2018-04-01T23:22:35.448+02:00":"John","2018-04-05T23:22:35.448+02:00":"Jane"}'
