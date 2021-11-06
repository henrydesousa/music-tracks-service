pipeline {
    agent any
    stages {
        stage("build") {
            steps {
               sh './gradlew -q properties > gprops'
               script {
                   echo 'building the application...'
                 buildVersion = sh(returnStdout: true, script: 'cat gprops |grep version:|awk \'{print $2}\'').trim()
                 buildName = sh(returnStdout: true, script: 'cat gprops |grep name:|awk \'{print $2}\'').trim()
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
                        sh "docker build -t henrydesousa/${buildName}:${buildVersion} ."
                        sh "echo $PASSWORD | docker login -u $USER --password-stdin"
                        sh "docker push henrydesousa/${buildName}:${buildVersion}"
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
                    sh 'kubectl create deployment nginx-deployment --image=nginx'
                }
            }
        }

    }
}