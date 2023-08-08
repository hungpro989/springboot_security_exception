pipeline {
    agent any

    environment {
        MYSQL_ROOT_PASSWORD = credentials('mysql-root')
    }

    stages {
        stage('Build') {
            steps {
                script {
                    bat 'mvn clean package'
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    def imageTag = "my-spring-app:${BUILD_NUMBER}"
                    bat "docker build -t ${imageTag} ."
                }
            }
        }

        stage('Deploy MySQL') {
            steps {
                script {
                    bat 'docker network create dev || echo "Network exists"'
                    bat 'docker container stop my-mysql || echo "Container does not exist"'
                    bat 'echo y | docker container prune'
                    bat 'docker volume rm mysql-data || echo "No volume"'

                    bat "docker run --name my-mysql --network dev -v mysql-data:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=${MYSQL_ROOT_PASSWORD} -e MYSQL_DATABASE=mydb -d mysql:8.0"
                }
            }
        }

        stage('Deploy Spring Boot App') {
            steps {
                script {
                    def imageTag = "my-spring-app:${BUILD_NUMBER}"
                    bat "docker container run -d --rm --name my-spring-app -p 8080:8080 --network dev ${imageTag}"
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
