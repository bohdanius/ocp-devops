pipeline {
    agent {
        node {
            label 'maven'
        }
    }
    stages {
        stage('Pull') {
            steps {
                echo ">>>>>>>>>>> pull changes"
                checkout([
                        $class           : 'GitSCM',
                        branches         : [[name: "*/master"]],
                        userRemoteConfigs: [[url: "${GIT_REPO}"]]
                ]);
            }
        }
        stage('Build') {
            steps {
                echo ">>>>>>>>>>> run build"
                sh 'mvn package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                echo ">>>>>>>>>>> run unit tests"
                sh 'mvn test'
            }
        }

        stage('Build Image') {
            steps {
                script {
                    openshift.withCluster() {
                        openshift.selector("bc", "${APPLICATION_NAME}-image").startBuild("--from-dir=./target", "--wait=true")
                        openshift.tag("${CICD_NS}/${APPLICATION_NAME}:latest", "${SIT_NS}/${APPLICATION_NAME}:latest")
                    }
                }
            }
        }

        stage('Deploy to SIT') {
            steps {
                script {
                    openshift.withCluster() {
                        openshift.withProject('${SIT_NS}') {
                            if (openshift.selector('dc', '${APPLICATION_NAME}').exists()) {
                                openshift.selector('dc', '${APPLICATION_NAME}').delete()
                                openshift.selector('svc', '${APPLICATION_NAME}').delete()
                                openshift.selector('route', '${APPLICATION_NAME}').delete()
                            }
                            openshift.newApp("${SIT_NS}/${APPLICATION_NAME}:latest").narrow("svc").expose()
                        }

                    }
                }
            }
        }

        stage('Run Autotests') {
            steps {
                echo ">>>>>>>>>>> pull autotests"
                sh 'cd ..'
                checkout([
                        $class           : 'GitSCM',
                        branches         : [[name: "*/master"]],
                        userRemoteConfigs: [[url: "${GIT_REPO_AUTO}"]]
                ]);
                openshift.withProject('${SIT_NS}') {
                    def host = openshift.selector('route', '${APPLICATION_NAME}').object().spec.host
                    sh 'mvn test -Dserver.host=${host} -Dserver.port=8080'
                }
            }
        }
    }
}