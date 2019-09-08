pipeline {
    agent {
        label 'docker'
    }
    options {
        timestamps()
    }
    environment {
        GITHUB_USER_ID = '2b98d5a0-65f8-4961-958d-ad3620541256'
    }
    stages {
        stage('create file') {
            steps {
                script {
                    def rootdir = pwd()
                    sh "cd ${rootdir} && pwd && ls -l"
                    sh "cd ${rootdir}@tmp && ls -l"
                    git (credentialsId: "${GITHUB_USER_ID}", url: 'https://github.com/Hopetree/jenkins-demos.git', branch: "master")
                    sh "cd ${rootdir}/.. && ls -l"
                    sh "cd ${rootdir} && pwd && ls -l"
                    sh "cd ${rootdir}@tmp && ls -l"
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