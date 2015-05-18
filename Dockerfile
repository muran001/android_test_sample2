# based on https://registry.hub.docker.com/u/samtstern/android-sdk/dockerfile/ with openjdk-8
FROM java:8

MAINTAINER muran001 <macmuran001@gmail.com>

ENV DEBIAN_FRONTEND noninteractive

# Install dependencies
RUN apt-get update && \
    apt-get install -yq lib32stdc++6 libstdc++6 lib32z1 libz1 zlib1g libncurses5  --no-install-recommends && \
    apt-get clean

# Download and untar SDK
ENV ANDROID_SDK_URL http://dl.google.com/android/android-sdk_r24.2-linux.tgz
RUN curl -L "${ANDROID_SDK_URL}" | tar --no-same-owner -xz -C /usr/local
ENV ANDROID_HOME /usr/local/android-sdk-linux
ENV ANDROID_SDK /usr/local/android-sdk-linux
ENV PATH ${ANDROID_HOME}/tools:$ANDROID_HOME/platform-tools:$PATH
ENV ADB_INSTALL_TIMEOUT 8

# Install Android SDK components
ENV ANDROID_SDK_COMPONENTS platform-tools,build-tools-22.0.1,android-22,extra-android-m2repository,extra-google-m2repository,sys-img-armeabi-v7a-android-22,extra-android-support,
RUN echo y | android update sdk --no-ui --all --filter "${ANDROID_SDK_COMPONENTS}"

# Support Gradle
ENV TERM dumb
ENV JAVA_OPTS -Xms256m -Xmx512m

ENV PROJECT /project

RUN mkdir $PROJECT
WORKDIR $PROJECT

ADD . $PROJECT

COPY wait-for-emulator /usr/local/bin/wait-for-emulator
COPY start-emulator /usr/local/bin/start-emulator
COPY start-test /usr/local/bin/start-test

RUN echo "sdk.dir=$ANDROID_HOME" > local.properties
RUN ./gradlew --stacktrace androidDependencies

RUN echo n | android create avd --force -n test-emulator -t android-22

