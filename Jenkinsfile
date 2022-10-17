pipeline {
  agent any

  stages {
    stage('Test') {
      steps {
        sh './mvnw clean test'
      }
    }
    stage('Package') {
      steps {
        sh './mvnw clean package'
        archiveArtifacts artifacts: \
            './target/*-runner.jar'
      }
    }
  }
}