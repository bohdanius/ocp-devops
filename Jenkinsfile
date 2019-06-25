pipeline {
    agent {
        node {label 'java'}
    }

    triggers {
        pollSCM('')
    }

    environment {
        APPLICATION_NAME = 'ds-example-project'
    }

    stages {
        stage('Build Testing') {
            steps {
                echo 'Building..'
                sh 'mvn build -skipTests'
            }
        }
        stage('Test Application') {
            steps {
                echo 'Testing..'
                sh 'mvn test'
            }
        }
        /*stage('Build Application') {
            steps {
                script {
                    openshift.withCluster() {
                        openshift.withProject() {
                            openshiftBuild(buildConfig: '${APPLICATION_NAME}', showBuildLogs: 'true')
                        }
                    }
                }
            }
        }*/
        stage('Deploy Application') {
            steps {
                script {
                    openshift.withCluster() {
                        openshift.withProject() {
                            openshiftDeploy(deploymentConfig: '${APPLICATION_NAME}')
                        }
                    }
                }
            }
        }
    }
}
