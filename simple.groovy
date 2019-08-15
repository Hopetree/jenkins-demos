pipeline {
    agent {
        label 'docker'
    }
    options {
        timestamps()
    }
    stages {
        stage('create file') {
            steps {
                script {
                    def filename = 'test.log'
                    sh "echo `date` > ${filename}"
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