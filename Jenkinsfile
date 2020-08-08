pipeline { 
    agent any 
    // This shows a simple build wrapper example, using the AnsiColor plugin.
    // This displays colors using the 'xterm' ansi color map in the console output
    options {
        ansiColor('xterm')
        skipStagesAfterUnstable()
        }
    // tools {
    //     // Note: this should match with the tool name configured in your jenkins instance (JENKINS_URL/configureTools/)
    //     maven 'maven'
    // }
    environment {
        NEXUS_INSTANCE = 'sonatypeNexus'
        NEXUS_REPOSITORY = "CI_Sample_Java"
        // // Repository where we will upload the artifact
        NEXUS_REPOSITORY_RELEASES = "maven-releases"
        NEXUS_REPOSITORY_SNAPSHOTS = "maven-snapshots"
    }

    stages {
        stage('Stage1') {
            steps {
            echo "Hello from 1"
            echo sh(returnStdout: true, script: 'env')
            }
        }
        // CloneCode stage is commented as the repo is already cloned by the Jenkins pipe
        stage("CloneCode") {
            steps {
                script {
                    // Let's clone the source
                    echo 'Repo Checkout'
                    sh 'printenv'
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
                withSonarQubeEnv('sonarQube') { // this must match sonar server name from global configuraiton
                // if [[ "$CI_BRANCH_NAME" == 'Feature/*' ]] || [[ "$CI_BRANCH_NAME" == 'master' ]] || [[ "$CI_BRANCH_NAME" == 'release/*' ]]; then
                    sh 'mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.7.0.1746:sonar'
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