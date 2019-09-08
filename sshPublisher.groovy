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
        stage('ssh publisher') {
            steps {
                sshPublisher(publishers: [
                    sshPublisherDesc(
                        configName: 'CentOS-3-root', 
                        transfers: [
                            sshTransfer(
                                cleanRemote: false, 
                                excludes: '', 
                                execCommand: '''
                                pwd
                                ls -l
                                date''', 
                                execTimeout: 120000, 
                                flatten: false, 
                                makeEmptyDirs: false, 
                                noDefaultExcludes: false, 
                                patternSeparator: '[, ]+', 
                                remoteDirectory: 'demos', 
                                remoteDirectorySDF: false, 
                                removePrefix: '', 
                                sourceFiles: '*.log'
                            ),
                            sshTransfer(
                                cleanRemote: false, 
                                excludes: '', 
                                execCommand: 'docker images && docker ps -a', 
                                execTimeout: 120000, 
                                flatten: false, 
                                makeEmptyDirs: false, 
                                noDefaultExcludes: false, 
                                patternSeparator: '[, ]+', 
                                remoteDirectory: '', 
                                remoteDirectorySDF: false, 
                                removePrefix: '', 
                                sourceFiles: ''
                            )
                        ], 
                        usePromotionTimestamp: false, 
                        useWorkspaceInPromotion: false, 
                        verbose: false
                    )
                ])
            }
        }
    }
    post {
        always {
            cleanWs()
        }
    }
}