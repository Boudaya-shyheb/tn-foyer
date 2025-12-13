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

    stage('Checkout') {
      steps {
        checkout scm
        script {
          // Jenkins fournit GIT_COMMIT automatiquement
          env.GIT_COMMIT_SHORT = env.GIT_COMMIT.take(7)
          echo "Commit short: ${env.GIT_COMMIT_SHORT}"
        }
      }
    }

    stage('SonarQube Analysis') {
      steps {
        withSonarQubeEnv('SonarQube') {
          bat '''
          mvn sonar:sonar ^
            -Dsonar.projectKey=tn-foyer ^
            -Dsonar.projectName="tn-foyer"
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
        script {
          env.IMAGE_TAG_LATEST = "${env.DOCKER_REPO}:${env.DOCKER_TAG}"
          env.IMAGE_TAG_COMMIT = "${env.DOCKER_REPO}:${env.DOCKER_TAG}-${env.GIT_COMMIT_SHORT}"

          echo "Build Docker image: ${env.IMAGE_TAG_COMMIT}"
          bat "docker build -t ${env.IMAGE_TAG_COMMIT} ."
        }
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
          echo Zimbabwe17* | docker login -u boudayashyheb --password-stdin
          docker push %IMAGE_TAG_COMMIT%
          docker logout
          '''
        }
      }
    }
  }

  post {
    success {
      echo "Pipeline terminé avec succès — image poussée : ${IMAGE_TAG_COMMIT}"
    }
    failure {
      echo "Pipeline échoué. Vérifie les logs."
    }
  }
}
