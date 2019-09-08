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
                    sh "cd ${rootdir}@tmp && ls -l"
                    git (credentialsId: "${GITHUB_USER_ID}", url: 'https://github.com/Hopetree/jenkins-demos.git', branch: "${GITHUB_BRANCH}")
                    sh "cd "
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