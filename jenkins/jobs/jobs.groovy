def gitRepo      = 'https://github.com/kololesya/carina-web-edu-project.git'
def branchName   = 'jenkins-setup'
def credentialsId = 'github-token-id'

folder('Carina-Web-Edu') {
    description('All tasks Jenkins for Carina Web EDU Project')
}

job('Carina-Web-Edu/RunEcommerceWebTests') {
    description('Running TestNG-suites from web.xml (E-commerce Web Tests)')
    logRotator {
        numToKeep(10)
    }

    scm {
        git {
            remote {
                url(gitRepo)
            }
            branch("*/${branchName}")
        }
    }

    triggers {
        scm('H/15 * * * *')
    }

    steps {
        maven {
            mavenInstallation('Maven 3')
            goals('clean test')
            rootPOM('pom.xml')
            properties('-DsuiteXmlFile=web.xml', '-Dcapabilities.provider=selenium')
        }
    }

    publishers {
        archiveJunit('**/target/surefire-reports/*.xml')
    }
}
