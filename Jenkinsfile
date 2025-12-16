pipeline {
    agent any

    environment {
        DOCKER_CREDENTIALS_ID = 'docker-hub-creds'
        DOCKER_REPO = 'boudayashyheb/alpine'
        DOCKER_TAG = '1.0.0'
    }

    tools {
        maven 'M2_HOME'
        jdk 'JAVA_HOME'
    }

    stages {

        stage('Trigger Webhook') {
            steps {
                echo 'Webhook déclenché avec succès'
            }
        }


        stage('start grafana + prometheus') {
            steps {
                echo " démarrage automatique grafana & prometheus "
                bat '''
                cd C:/monitoring
                docker compose up -d
                '''
            }
        }

        stage('wait for monitor') {
            steps {
                echo " Attente du moniteur "
                bat '''
                timeout /t 10
                '''
            }
        }

        stage('Checkout') {
            steps {
                checkout scm
                script {
                    env.GIT_COMMIT_SHORT = env.GIT_COMMIT.take(7)
                    env.IMAGE_TAG = "${DOCKER_REPO}:${DOCKER_TAG}-${GIT_COMMIT_SHORT}"
                    echo "Image Docker : ${env.IMAGE_TAG}"
                }
            }
        }

        /* ============================
           BUILD + TESTS (JUnit / Mockito / H2 / JaCoCo)
           ============================ */
        stage('Build & Tests') {
            steps {
                echo 'Build Maven + Tests unitaires & intégration'

                bat '''
                mvn -B clean verify ^
                  -Dspring.profiles.active=test ^
                  -Dspring.datasource.url=jdbc:h2:mem:testdb ^
                  -Dspring.datasource.driver-class-name=org.h2.Driver ^
                  -Dspring.jpa.database-platform=org.hibernate.dialect.H2Dialect ^
                  -Dspring.jpa.hibernate.ddl-auto=create-drop
                '''
            }
        }

        /* ============================
           PUBLICATION JUNIT
           ============================ */
        stage('Publish Test Results') {
            steps {
                junit '**/target/surefire-reports/*.xml'
            }
        }

        /* ============================
           SONARQUBE
           ============================ */
        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    bat '''
                    mvn -B sonar:sonar ^
                      -Dsonar.projectKey=tn-foyer ^
                      -Dsonar.projectName=tn-foyer ^
                      -Dsonar.java.binaries=target/classes
                    '''
                }
            }
        }

        /* ============================
           BUILD IMAGE DOCKER
           ============================ */
        stage('Build Docker Image') {
            steps {
                echo 'Construction de l’image Docker'
                bat 'docker build -t %IMAGE_TAG% .'
            }
        }

        /* ============================
           PUSH IMAGE DOCKER
           ============================ */
        stage('Push Docker Image') {
            steps {
                withCredentials([
                    usernamePassword(
                        credentialsId: DOCKER_CREDENTIALS_ID,
                        usernameVariable: 'shyheb',
                        passwordVariable: 'shyheb123*'
                    )
                ]) {
                    bat '''
                    echo Zimbabwe17* | docker login -u boudayashyheb --password-stdin
                    docker push %IMAGE_TAG%
                    docker logout
                    '''
                }
            }
        }

        /* ============================
           DEPLOIEMENT KUBERNETES
           ============================ */
        stage('Deploy to Kubernetes') {
            steps {
                echo 'Déploiement sur Kubernetes'

                bat '''
                kubectl set image deployment/spring-app spring-app=%IMAGE_TAG% -n devops
                kubectl rollout status deployment/spring-app -n devops
                '''
            }
        }
    }

    post {
        success {
            echo '✅ Pipeline terminé avec succès'
        }
        failure {
            echo '❌ Pipeline échoué — vérifier les logs'
        }
        always {
            cleanWs()
        }
    }
}
