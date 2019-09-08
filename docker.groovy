pipeline {
    agent {
        label 'docker'
    }
    options {
        timestamps()
    }
    environment {
        GITHUB_USER_ID = '2b98d5a0-65f8-4961-958d-ad3620541256'
        HAO_IMAGE_TAG = 'hao:latest'
        NODE_IAMGE_TAG = 'node:latest'
    }
    stages {
        stage('Clone sources') {
            options {
                timeout(time: 30, unit: 'SECONDS')
            }
            steps {
                git (credentialsId: "${GITHUB_USER_ID}", url: 'https://github.com/Hopetree/hao.git', branch: 'master')
            }
        }
        stage('Build image') {
            steps {
                script {
                    // 必须以 Dockerfile 所有目录路径为结尾
                    docker.build("${HAO_IMAGE_TAG}", "--no-cache .")
                }
            }
        }
    }
    post {
        always {
            sh "docker images|grep '<none>'|awk '{print \$3}'|xargs docker image rm > /dev/null 2>&1 || true"
            sh "docker images"
            cleanWs()
        }
    }
}