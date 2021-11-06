pipeline {
    agent any
    stages {
        stage("build") {
            steps {
               sh './gradlew -q properties > gprops'
               script {
                   echo 'building the application...'
                 APP_VERSION = sh(returnStdout: true, script: 'cat gprops |grep version:|awk \'{print $2}\'').trim()
                 APP_NAME = sh(returnStdout: true, script: 'cat gprops |grep name:|awk \'{print $2}\'').trim()
                 JAR_FILE = "./build/libs/${APP_NAME}-${APP_VERSION}.jar"
                 TAG_TIME = sh(returnStdout: true, script: 'echo $(date +%Y%m%d)').trim()
                 IMAGE_TAG = "${TAG_TIME}.${BUILD_NUMBER}"
               }
               sh './gradlew clean build -x test'
            }
        }

        stage("build image") {
            steps {
                script {
                    echo 'building the docker image...'
                    withCredentials([
                        usernamePassword(credentialsId: 'docker-hub-repo', usernameVariable: 'USER', passwordVariable: 'PASSWORD')
                    ]) {
                        sh "docker build --build-arg JAR_FILE='${JAR_FILE}' -t henrydesousa/${APP_NAME}:${IMAGE_TAG} ."
                        sh "echo $PASSWORD | docker login -u $USER --password-stdin"
                        sh "docker push henrydesousa/${APP_NAME}:${IMAGE_TAG}"
                    }
                }
            }
        }

        stage("deploy") {
            environment {
                AWS_ACCESS_KEY_ID = credentials('jenkins_aws_access_key_id')
                AWS_SECRET_ACCESS_KEY = credentials('jenkins_aws_secret_access_key')
            }
            steps {
                script {
                    echo 'deploying the application...'
                    sh 'envsubst < kubernetes/deployment.yml | kubectl apply -f -'
                    sh 'envsubst < kubernetes/service.yml | kubectl apply -f -'
                }
            }
        }

    }
}