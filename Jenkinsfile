pipeline {
    agent any

    environment {
        MYSQL_ROOT_PASSWORD = credentials('mysql-root')
    }

    stages {
        stage('Build') {
            steps {
                script {
                    sh 'mvn clean package'
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    def imageTag = "my-spring-app:${BUILD_NUMBER}"
                    sh "docker build -t ${imageTag} ."
                }
            }
        }

        stage('Deploy MySQL') {
            steps {
                script {
                    sh 'docker network create dev || echo "Network exists"'
                    sh 'docker container stop my-mysql || echo "Container does not exist"'
                    sh 'echo y | docker container prune'
                    sh 'docker volume rm mysql-data || echo "No volume"'

                    sh "docker run --name my-mysql --network dev -v mysql-data:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=${MYSQL_ROOT_PASSWORD} -e MYSQL_DATABASE=mydb -d mysql:8.0"
                }
            }
        }

        stage('Deploy Spring Boot App') {
            steps {
                script {
                    def imageTag = "my-spring-app:${BUILD_NUMBER}"
                    sh "docker container run -d --rm --name my-spring-app -p 8080:8080 --network dev ${imageTag}"
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
