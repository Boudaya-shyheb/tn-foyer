pipeline {
  agent any

  tools {
    jdk 'JAVA_HOME'
    maven 'M2_HOME'
  }

  stages {

    stage('GIT stage') {
      steps {
        git branch: 'master',
            url: 'https://github.com/Boudaya-shyheb/tn-foyer.git'
      }
    }

    stage('Compile Stage') {
      steps {
        bat 'mvn -B clean compile'
      }
    }

    stage('SonarQube Analysis') {
      steps {
        withSonarQubeEnv('SonarQube') {
          bat '''
          mvn sonar:sonar ^
            -Dsonar.projectKey=tn-foyer ^
            -Dsonar.projectName=tn-foyer
          '''
        }
      }
    }
  }

  post {
    success { echo 'Pipeline succeeded' }
    failure { echo 'Pipeline failed' }
  }
}

