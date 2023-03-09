# Dne sequence Spring Boot Application

This is an example Spring Boot application that checks if arrays of integer contains DNE

## Steps to build and run application

## 1. Build docker image:
To build the Docker image, run the following command from the root directory of the project:

`docker build -t mend-dne-app .` 

## 2. Run docker image:
To run the Docker image, run the following command from the root directory of the project:

`docker run -p 10000:10000 mend-dne-app` 

This will start the container and map port `10000` on the host machine to port `10000` on the container.

Once the container is running, you can use the application 
by sending example request: 

`curl -X POST http://localhost:10000/server -H "content-type:application/json" -d '{"seq":[1,2,3]}'`



