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
          GIT_COMMIT_SHORT = bat(returnStdout: true, script: "git rev-parse --short HEAD").trim()
          env.GIT_COMMIT_SHORT = GIT_COMMIT_SHORT
          echo "Commit short: ${GIT_COMMIT_SHORT}"
        }
      }
    }

    stage('SonarQube Analysis') {
      steps {
        withSonarQubeEnv('SonarQube') {
          bat '''
          mvn sonar:sonar ^
            -Dsonar.projectKey=tn-foyer ^
            -Dsonar.projectName=mon projet
          '''
        }
      }
    }
    
    stage('Build & Test - Maven') {
      steps {
        echo "Lancement du build Maven..."
        bat "mvn -B clean compile"
      }
      post {
        failure {
          echo "Build Maven failed — arrête le pipeline."
        }
      }
    }

    stage('Build Docker Image') {
      steps {
        script {
          IMAGE_TAG_LATEST = "${env.DOCKER_REPO}:${env.DOCKER_TAG}"
          IMAGE_TAG_COMMIT = "${env.DOCKER_REPO}:${env.DOCKER_TAG}-${env.GIT_COMMIT_SHORT}"
          echo "Build docker image ${IMAGE_TAG_LATEST} and ${IMAGE_TAG_COMMIT}"
          bat "docker build -t ${IMAGE_TAG_COMMIT} ."
        }
      }
    }

    stage('Build & Push Docker (CLI)') {
      steps {
        script {
          def shortSha = bat(returnStdout: true, script: 'git rev-parse --short HEAD').trim()
          def image = "${env.DOCKER_REPO}:${env.DOCKER_TAG}"
          def imageCommit = "${env.DOCKER_REPO}:${env.DOCKER_TAG}-${shortSha}"

          bat "docker build -t ${imageCommit} ."

          withCredentials([usernamePassword(credentialsId: 'docker-hub-creds',
                                            usernameVariable: 'shyheb',
                                            passwordVariable: 'shyheb123*')]) {

            bat 'echo Zimbabwe17* | docker login -u boudayashyheb --password-stdin'

            bat "docker push ${imageCommit}"

            bat 'docker logout || true'
          }
        }
      }
    }
}

  post {
    success {
      echo "Pipeline terminé avec succès — image poussée : ${DOCKER_REPO}:${DOCKER_TAG}"
    }
    failure {
      echo "Pipeline échoué. Vérifie les logs."
    }
  }
}
