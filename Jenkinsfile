pipeline {
    agent any
    stages {
        stage("build jar") {
            steps {
                script {
                    echo 'building the application'
                    sh './gradlew build -x test'
                }
            }
        }

        stage("build image") {
            steps {
                script {
                    echo 'building the docker image'
                    withCredentials([
                        usernamePassword(credentialsId: 'docker-hub-repo', usernameVariable: 'USER', passwordVariable: 'PASSWORD')
                    ]) {
                        sh 'docker build -t henrydesousa/music-tracks-service:1.0 .'
                        sh "echo $PASSWORD | docker login -u $USER --password-stdin"
                        sh 'docker push henrydesousa/testing/music-tracks-service:1.0'
                    }
                }
            }
        }

    }
}