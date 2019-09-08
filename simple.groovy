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
                    def rootdir = pwd()
                    sh "cd ${rootdir} && pwd && ls -l"
                    sh "cd ${rootdir}/.. && ls -l"
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