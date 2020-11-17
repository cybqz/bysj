FROM openjdk:latest
VOLUME /tmp
COPY target/goodsms-0.0.1.jar goodsms.jar
#RUN bash -c "touch /goodsms.jar"
EXPOSE 8080
ENTRYPOINT ["java","-jar","goodsms.jar"]