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
                        sh 'mvn package -DskipTests'
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
                withCredentials([
                    string(
                        credentialsId: 'postgres-password',
                        variable: 'SPRING_DATASOURCE_PASSWORD'
                    )
                ]) {
                    sh 'docker compose up -d'
                }
            }
        }
        stage('API Health Check') {
            steps {
                sh 'curl --fail http://localhost:8082/health'
            }
        }
    }
}