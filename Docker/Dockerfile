FROM openjdk:11

# folder creation
RUN mkdir -p /gift-card
RUN mkdir -p /gift-card/logs

## logs folder could be mounted on host
VOLUME /gift-card/logs

# binary and conf copy paste
COPY gift-card-rest.jar /gift-card

# java opts
ENV XMX="-Xmx1g"
ENV JAVA_OPTS="-Djava.security.egd=file:/dev/urandom ${XMX} -Dfile.encoding=UTF-8"

EXPOSE 8080
EXPOSE 11000

ENTRYPOINT ["java","-jar","/gift-card/gift-card-rest.jar"]
