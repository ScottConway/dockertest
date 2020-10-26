# DockerTest

Spring boot project to show uploading files into a database.   The whole thing runs
from a docker container.

## URLs

By default the application is set to run on port 10100.  
This is in the application.yml file.

Swagger UI: http://localhost:10100/swagger-ui.html

Actuator: http://localhost:10100/actuator/


## Running in docker with development profile

The development profile uses h2 memory database which is destroyed whenever the service is shut down or restarted.

You can refer to the docker-compose-dev.yml and Dockerfile_dev for docker configuration.

You must have docker already installed.   To start the application in docker run the following command:

>docker-compose -f docker-compose-dev.yml up --build  

To end the application use the following command:

>docker-compose -f docker-compose-dev.yml  down          