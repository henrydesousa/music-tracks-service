pipeline {
    agent any
    stages {
        stage("build") {
            steps {
               sh './gradlew -q properties > gprops'
               script {
                 buildVersion = sh(returnStdout: true, script: 'cat gprops |grep version:|awk \'{print $2}\'').trim()
                 buildName = sh(returnStdout: true, script: 'cat gprops |grep name:|awk \'{print $2}\'').trim()
                 // jarFile = "./build/libs/music-tracks-service-${buildVersion}.jar"
                 tagTime = sh(returnStdout: true, script: 'echo $(date +%Y%m%d)').trim()
                 imageTag = "${tagTime}.${BUILD_NUMBER}"
               }
               echo "buildVersion: ${buildVersion}"
               echo "buildName: ${buildName}"
               echo "tagTime: ${tagTime}"
               echo "imageTag: ${imageTag}"
               sh './gradlew clean build -x test'
            }
        }

        stage("build image") {
            steps {
                script {
                    echo 'building the docker image'
                    withCredentials([
                        usernamePassword(credentialsId: 'docker-hub-repo', usernameVariable: 'USER', passwordVariable: 'PASSWORD')
                    ]) {
                        sh "docker build -t henrydesousa/${buildName}:${imageTag} ."
                        sh "echo $PASSWORD | docker login -u $USER --password-stdin"
                        sh "docker push henrydesousa/${buildName}:${imageTag}"
                    }
                }
            }
        }

    }
}