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
                    sh "mvn test -Dtest=SuiteTest"
                }
            }
            tools { maven 'maven' }
        }
        stage('Inspect_SonarQubeAnalytics') {
            steps {
                withSonarQubeEnv('SonarQube') { // this must match sonar server name from global configuraiton
                // if [[ "$CI_BRANCH_NAME" == 'Feature/*' ]] || [[ "$CI_BRANCH_NAME" == 'master' ]] || [[ "$CI_BRANCH_NAME" == 'release/*' ]]; then
                    // sh 'mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.7.0.1746:sonar'
                    sh 'mvn sonar:sonar -Dtest=SuiteTest -Dsonar.host.url=http://172.22.0.6:9000 -Dsonar.login=383062ffbf0f124354b9e29c4dcd89faf671210e'
                // fi
                }
            }
        }
        stage("PackagePublishToNexus") {
            steps {
                script {
                    // Read POM xml file using 'readMavenPom' step , this step 'readMavenPom' is included in: https://plugins.jenkins.io/pipeline-utility-steps
                    pom = readMavenPom file: "pom.xml";
                    // Find built artifact under target folder
                    filesByGlob = findFiles(glob: "target/*.${pom.packaging}");
                    // Print some info from the artifact found
                    echo "${filesByGlob[0].name} ${filesByGlob[0].path} ${filesByGlob[0].directory} ${filesByGlob[0].length} ${filesByGlob[0].lastModified}"
                    // Extract the path from the File found
                    artifactPath = filesByGlob[0].path;
                    // Assign to a boolean response verifying If the artifact name exists
                    artifactExists = fileExists artifactPath;
                    if(artifactExists) {
                        echo "*** File: ${artifactPath}, group: ${pom.groupId}, packaging: ${pom.packaging}, version ${pom.version}";

                        nexusPublisher nexusInstanceId: NEXUS_INSTANCE, 
                            nexusRepositoryId: NEXUS_REPOSITORY, 
                            packages: [
                                [$class: 'MavenPackage', 
                                mavenAssetList: [
                                    [classifier: '', extension: '', 
                                    filePath: artifactPath]
                                    ], 
                                mavenCoordinate: [
                                    artifactId: pom.artifactId, groupId: pom.groupId, 
                                    packaging: pom.packaging, version: pom.version]
                                    ]
                                ]
                    } else {
                        error "*** File: ${artifactPath}, could not be found";
                    }
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