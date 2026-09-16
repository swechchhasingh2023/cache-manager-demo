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
                sh '''
                    echo "Waiting for backend to become ready..."

                    for i in {1..30}; do
                        if curl --fail http://localhost:8082/health; then
                            echo ""
                            echo "Backend is healthy!"
                            exit 0
                        fi

                        echo "Backend not ready yet. Waiting 2 seconds..."
                        sleep 2
                    done

                    echo "Backend failed to become healthy."
                    docker compose logs backend
                    exit 1
                '''
            }
        }
    }
}
