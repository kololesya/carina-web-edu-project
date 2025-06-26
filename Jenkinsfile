pipeline {
  agent any

  stages {
    stage('Checkout code') {
      steps {
        git url: 'https://github.com/kololesya/carina-web-edu-project.git',
            branch: 'jenkins-setup',
            credentialsId:
      }
    }
    stage('Generation jobs') {
      steps {
        jobDsl targets: 'jenkins/jobs/*.groovy'
      }
    }
  }
}
