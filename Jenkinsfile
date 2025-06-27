pipeline {
  agent any

  tools {
    maven 'M3'
  }

  environment {
    GRID_URL = 'http://host.docker.internal:4444'
  }

  stages {
    stage('Checkout') {
      steps {
        checkout scm
      }
    }

    stage('Debug SELENIUM_HOST') {
      steps {
        sh '''
          echo ">>> SELENIUM_HOST is: $SELENIUM_HOST"
          curl --silent --show-error --fail $SELENIUM_HOST/status
        '''
      }
    }

    stage('Run E-commerce Web Tests') {
      steps {
        sh """
          mvn clean test \
            -DsuiteXmlFile=web.xml \
            -Dcapabilities.provider=selenium \
            -Dselenium_url=${GRID_URL}/wd/hub
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
