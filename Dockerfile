FROM alpine
RUN add apk openjdk17
EXPOSE 80
CMD "java"
