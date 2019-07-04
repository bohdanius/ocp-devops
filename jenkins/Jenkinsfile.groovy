pipeline {
    agent {
        node {
            label 'maven'
        }
    }
    stages {
        stage('Build') {
            steps {
                printf("pull changes")
                checkout
                scm
                printf("run build")
                sh 'mvn build -skipTests'
            }
        }

        stage('Test') {
            steps {
                printf("run unit tests")
                sh 'mvn test'
            }
        }
        stage('Deploy') {
            steps {
                openshiftDeploy(deploymentConfig: '${APPLICATION_NAME}')
            }
        }
    }
}

//  steps {
//  echo
//  'Building..'
//}

//steps {
//echo 'Testing..'
//sh 'mvn test'
//}


//stage('Deploy Application') {
//steps {
//script {
//openshift.withCluster() {
//openshift.withProject() {
//openshiftDeploy(deploymentConfig: '${APPLICATION_NAME}')
//}
//}
//}
//}
//}