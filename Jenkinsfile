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

    stage('Debug GRID_URL') {
      steps {
        sh '''
          echo ">>> GRID_URL is: $GRID_URL"
          curl --silent --show-error --fail $GRID_URL/status
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
