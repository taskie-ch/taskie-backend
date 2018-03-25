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
