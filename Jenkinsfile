pipeline { 
    agent any 
    // This shows a simple build wrapper example, using the AnsiColor plugin.
    // This displays colors using the 'xterm' ansi color map in the console output
    options {
        ansiColor('xterm')
        skipStagesAfterUnstable()
        }
    tools {
        // Note: this should match with the tool name configured in your jenkins instance (JENKINS_URL/configureTools/)
        maven 'maven'
    }
    environment {
        // This can be nexus3 or nexus2
        NEXUS_VERSION = "nexus3"
        // This can be http or https
        NEXUS_PROTOCOL = "http"
        // Where your Nexus is running
        NEXUS_URL = "172.22.0.4:8081"
        // Repository where we will upload the artifact
        NEXUS_REPOSITORY = "CI_Sample_Java"
        // // Repository where we will upload the artifact
        // NEXUS_REPOSITORY_RELEASES = "maven-releases"
        // NEXUS_REPOSITORY_SNAPSHOTS = "maven-snapshots"
        // Jenkins credential id to authenticate to Nexus OSS
        NEXUS_CREDENTIAL_ID = "nexus"
    }

    stages {
        stage('Stage1') {
            steps {
            echo "Hello from 1"
            }
        }
        // CloneCode stage is commented as the repo is already cloned by the Jenkins pipe
        stage("CloneCode") {
            steps {
                script {
                    // Let's clone the source
                    echo 'Repo Checkout'
                    // git 'https://github.com/danielalejandrohc/cargotracker.git';
                }
            }
        }
        stage("Build") {
            steps {
                script {
                    // If you are using Windows then you should use "bat" step
                    // Since unit testing is out of the scope we skip them
                    sh "mvn package -DskipTests=true"
                }
            }
            tools { maven 'maven' }
        }
        stage("UnitTest_mvn") {
            steps {
                script {
                    sh "mvn test"
                }
            }
        }
        // ToDo - enable junit testing when the test cases are ready
        // stage('UnitTest'){
        //     steps {
        //         sh 'make check'
        //         junit 'reports/**/*.xml' 
        //     }
        // }
        stage('Inspect_SonarQubeAnalytics') {
            steps {
                withSonarQubeEnv('SonarQube') { // this must match sonar server name from global configuraiton
                // if [[ "$CI_BRANCH_NAME" == 'Feature/*' ]] || [[ "$CI_BRANCH_NAME" == 'master' ]] || [[ "$CI_BRANCH_NAME" == 'release/*' ]]; then
                    sh 'mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.7.0.1746:sonar'
                    // sh 'mvn sonar:sonar -Dsonar.host.url=http://localhost:9000 -Dsonar.login=the-generated-token'
                // fi
                }
            }
        }
    }
    
    post {
        always {
            echo 'JENKINS PIPELINE'
        }
        notBuilt {
            echo 'JENKINS PIPELINE NOT BUILT'
        }
        success {
            echo 'JENKINS PIPELINE SUCCESSFUL'
        }
        failure {
            echo 'JENKINS PIPELINE FAILED'
        }
        unstable {
            echo 'JENKINS PIPELINE WAS MARKED AS UNSTABLE'
        }
        changed {
            echo 'JENKINS PIPELINE STATUS HAS CHANGED SINCE LAST EXECUTION'
        }
        aborted {
            echo 'JENKINS PIPELINE ABORTED'
        }
    }
}