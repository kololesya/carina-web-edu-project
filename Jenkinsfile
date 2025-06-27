pipeline {
  agent any

  tools {
    maven 'Maven 3'
  }

  environment {
    SELENIUM_HOST = 'http://localhost:4444'
  }

  stages {
    stage('Checkout') {
      steps {
        checkout scm
      }
    }

    stage('Run E-commerce Web Tests') {
      steps {
        sh """
          mvn clean test \
            -DsuiteXmlFile=web.xml \
            -Dcapabilities.provider=selenium \
            -Dselenium.host=${SELENIUM_HOST}
        """
      }
    }
  }

  post {
    always {
      junit '**/target/surefire-reports/*.xml'
    }
  }
}
