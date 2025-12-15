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

    stage('Trigger webhook') {
            steps {
                echo 'Webhook triggered successfully!'
            }
        }
    
    stage('Checkout code') {
      steps {
        checkout scm
        script {
          env.GIT_COMMIT_SHORT = env.GIT_COMMIT.take(7)
          env.IMAGE_TAG = "${env.DOCKER_REPO}:${env.DOCKER_TAG}-${env.GIT_COMMIT_SHORT}"
          echo "Image tag: ${env.IMAGE_TAG}"
        }
      }
    }

    stage('SonarQube Analysis') {
  steps {
    withSonarQubeEnv('SonarQube') {
      bat '''
        mvn clean compile sonar:sonar ^
          -Dsonar.projectKey=tn-foyer ^
          -Dsonar.projectName=tn-foyer ^
          -Dsonar.java.binaries=target/classes
      '''
    }
  }
}


    stage('Build & Test - Maven') {
      steps {
        echo "Lancement du build Maven..."
        bat "mvn -B clean compile"
      }
    }

   stage('Build Docker Image') {
      steps {
        bat 'docker build -t %IMAGE_TAG% .'
      }
    }

stage('Push Docker Image') {
  steps {
    withCredentials([
      usernamePassword(
        credentialsId: env.DOCKER_CREDENTIALS_ID,
        usernameVariable: 'shyheb',
        passwordVariable: 'shyheb123*'
      )
    ]) {
      bat '''
            echo %DOCKER_PASS% | docker login -u %DOCKER_USER% --password-stdin
            docker push %IMAGE_TAG%
            docker logout
      '''
    }
  }
  }

  stage('Deploiement Kubernetes') {
  steps {
    echo "Déploiement de l'image dans Kubernetes..."

    bat """
      kubectl set image deployment/spring-app spring-app=%IMAGE_TAG% -n devops
      kubectl rollout status deployment/spring-app -n devops
    """
  }
}
    
  }
  post {
    success {
      echo "Pipeline terminé avec succès — image poussée"
    }
    failure {
      echo "Pipeline échoué. Vérifie les logs."
    }
  }
}
