pipeline {
    agent {
        label 'docker'
    }
    options {
        timestamps()
    }
    stages {
        stage('docker run') {
            steps {
                withDockerContainer(image: 'node:latest', args: "-v /var/run/docker.sock:/var/run/docker.sock -v /bin/docker:/bin/docker") {
                    sh "npm config set registry https://registry.npm.taobao.org/"
                    sh "npm config list"
                    sh "env"
                    sh "docker -v"
                }
            }
        }
    }
    post {
        always {
            cleanWs()
        }
    }
}