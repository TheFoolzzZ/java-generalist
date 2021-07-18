FROM centos:centos7
#安装maven
RUN mkdir /usr/local/maven
WORKDIR /usr/local/maven/
#从本地复制maven到容器,ADD命令会自动解压
ADD apache-maven-3.8.1-bin.tar.gz /usr/local/maven/
#设置MAVEN_HOME
ENV MAVEN_HOME=/usr/local/maven/apache-maven-3.8.1/

#安装graalvm
RUN	mkdir /usr/local/graalvm
#从本地复制graalvm及native-image到容器
ADD graalvm-ce-java11-linux-amd64-21.1.0.tar.gz /usr/local/graalvm/
ADD native-image-installable-svm-java11-linux-amd64-21.1.0.jar /usr/local/graalvm/
WORKDIR /usr/local/graalvm/
#设置JAVA_HOME
ENV JAVA_HOME=/usr/local/graalvm/graalvm-ce-java11-21.1.0/
#设置PATH
ENV PATH=${JAVA_HOME}/bin:${MAVEN_HOME}/bin:$PATH
#安装native-image
RUN	gu install -L /usr/local/graalvm/native-image-installable-svm-java11-linux-amd64-21.1.0.jar
#安装native-iamge运行时依赖gcc等环境
RUN yum -y install gcc glibc-devel zlib-devel libstdc++-static

#从gitee拉取代码并构建native-image
RUN yum -y install git
WORKDIR /usr/local/
RUN git clone https://gitee.com/karidyang/geekbang-lessons-v1.git
WORKDIR /usr/local/geekbang-lessons-v1/
WORKDIR /usr/local/geekbang-lessons-v1/projects/stage-0/spring-native
RUN mvn -Pnative clean package -Dmaven.test.skip=true
EXPOSE 8080
CMD /usr/local/geekbang-lessons-v1/projects/stage-0/spring-native/target/