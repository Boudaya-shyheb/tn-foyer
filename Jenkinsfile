
pipeline {
  agent any

  
  tools {
    jdk 'JAVA_HOME'
    maven 'M2_HOME'
  }

  stages {
    stage('GIT stage') {
      steps {
        git branch: 'master', url: 'https://github.com/Boudaya-shyheb/tn-foyer.git'
      }
    }

    stage('Compile Stage') {
      steps {
        // -B pour build non-interactif
        bat 'mvn -B clean compile'
      }
    }
  }

  post {
    success { echo 'Compile succeeded' }
    failure { echo 'Compile failed' }
  }
}
