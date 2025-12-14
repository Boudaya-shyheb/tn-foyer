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
      image_tag = "${env.DOCKER_REPO}:${env.DOCKER_TAG}-${env.GIT_COMMIT_SHORT}"
      echo "Building optimized image: ${env.IMAGE_TAG}"
      bat "docker build -t env.image_tag ."
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
      docker push env.image_tag
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
