pipeline {
    agent any

    stages {

        stage('Backend Test') {
            steps {
                dir('backend/cache-manager-backend') {
                    withCredentials([
                        string(
                            credentialsId: 'postgres-password',
                            variable: 'SPRING_DATASOURCE_PASSWORD'
                        )
                    ]) {
                        sh 'mvn test'
                    }
                }
            }
        }

        stage('Docker Compose Build') {
            steps {
                sh 'docker compose build'
            }
        }

        stage('Docker Compose Up') {
            steps {
                sh 'docker compose up -d'
            }
        }
    }
}