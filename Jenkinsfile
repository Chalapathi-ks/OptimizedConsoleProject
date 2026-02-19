pipeline {
    agent any
    environment {
        SELENIUM_GRID_URL = 'http://selenium-hub.netcorein.com:4444/wd/hub'
    }

    tools {
        maven 'M3'
    }

    stages {
        stage('Build') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('Grid Health Check') {
            steps {
                script {
                    echo "Checking Selenium Grid health..."
                    def statusOutput = sh(
                        script: "curl -s --connect-timeout 10 --max-time 30 http://selenium-hub.netcorein.com:4444/status | head -20",
                        returnStdout: true
                    ).trim()
                    
                    if (statusOutput.contains('"ready": true')) {
                        echo "✅ Selenium Grid is accessible"
                    } else {
                        echo "⚠️ Selenium Grid may not be ready"
                    }
                    
                    // Check for Chrome browsers available
                    def chromeCheck = sh(
                        script: "curl -s --connect-timeout 10 --max-time 30 http://selenium-hub.netcorein.com:4444/grid/api/hub | grep -o '\"browserName\":\"chrome\"' | wc -l",
                        returnStdout: true
                    ).trim()
                    
                    if (chromeCheck != "0") {
                        echo "✅ Chrome browsers available on Grid"
                    } else {
                        echo "⚠️ No Chrome browsers found on Grid"
                    }
                }
            }
        }

        stage('Test') {
            steps {
                echo "Running tests on ENV: ${params.ENV}"
                echo "Suite File: ${params.SUITE_FILE}"
                echo "Using Selenium Grid URL: ${SELENIUM_GRID_URL}"
                // Ensure we don't reuse an old report folder
                sh 'rm -rf Extent_Report Extent_Report.zip || true'
                script {
                    // Run Maven but continue even if it fails so we can always produce artifacts
                    int mvnStatus = sh(
                        script: "mvn clean test -P${params.ENV} -Denv.profile=${params.ENV} -DhubUrl=${SELENIUM_GRID_URL} -DsuiteXmlFile=${params.SUITE_FILE} -Dlistener=core.reporting.ExtentTestNGITestListener,core.AnnotationTransformer",
                        returnStatus: true
                    )
                    if (mvnStatus != 0) {
                        echo "Maven exited with status ${mvnStatus}. Marking build as FAILURE but continuing to generate artifacts."
                        currentBuild.result = 'FAILURE'
                    }

                    // Try common paths first
                    def sourcePath = ''
                    if (fileExists('extent.html')) {
                        sourcePath = 'extent.html'
                    } else if (fileExists('test-output/ExtentReport.html')) {
                        sourcePath = 'test-output/ExtentReport.html'
                    } else {
                        // Fallback: search workspace for any likely extent report
                        def found = sh(script: """bash -lc 'find . -maxdepth 6 -type f ( -name "extent.html" -o -name "ExtentReport.html" -o -name "Extent*.html" ) | head -n 1'""", returnStdout: true).trim()
                        if (found) {
                            sourcePath = found
                        }
                    }

                    // Normalize to a single artifact path
                    env.EXTENT_REPORT_PATH = 'Extent_Report/index.html'
                    if (sourcePath) {
                        sh "mkdir -p Extent_Report && cp \"${sourcePath}\" Extent_Report/index.html && zip -r Extent_Report.zip Extent_Report || true"
                    } else {
                        sh "mkdir -p Extent_Report && echo '<html><body><h2>No Extent report was produced for build #${env.BUILD_NUMBER}</h2></body></html>' > Extent_Report/index.html && zip -r Extent_Report.zip Extent_Report || true"
                    }
                    echo "Using Extent report artifact: ${env.EXTENT_REPORT_PATH} (source: ${sourcePath ?: 'none'})"
                }
            }
        }
    }

    post {
        success {
            slackSend(
                channel: '#qa-automation-reports',
                color: 'good',
                message: """🎉 *${env.JOB_NAME.toUpperCase()} SUCCESS* 🎉

🏗️ **Job**: ${env.JOB_NAME}
🔢 **Build**: #${env.BUILD_NUMBER}
⏰ **Duration**: ${currentBuild.durationString}
🌍 **Environment**: ${params.ENV}
🌿 **Branch**: ${env.BRANCH_NAME ?: 'origin/main'}
🚀 **Build Cause**: 🤖 Manual Trigger

🔗 <${env.BUILD_URL}|View Build>
📊 <${env.BUILD_URL}Extent_Report/|View Reports>
📦 <${env.BUILD_URL}artifact/${env.EXTENT_REPORT_PATH}|View Extent HTML Report Artifact>
📦 <${env.BUILD_URL}artifact/Extent_Report.zip|Download Full Extent Report ZIP>
📋 <${env.BUILD_URL}testngreports/|View TestNG Report>""",
                tokenCredentialId: 'slackID',
                teamDomain: 'unbxd',
                botUser: true
            )
        }
        failure {
            slackSend(
                channel: '#qa-automation-reports',
                color: 'danger',
                message: """💥 *${env.JOB_NAME.toUpperCase()} FAILED* 💥

🏗️ **Job**: ${env.JOB_NAME}
🔢 **Build**: #${env.BUILD_NUMBER}
⏰ **Duration**: ${currentBuild.durationString}
🌍 **Environment**: ${params.ENV}
🌿 **Branch**: ${env.BRANCH_NAME ?: 'origin/main'}
🚀 **Build Cause**: 🤖 Manual Trigger

🔗 <${env.BUILD_URL}|View Build>
📊 <${env.BUILD_URL}Extent_Report/|View Reports>
📦 <${env.BUILD_URL}artifact/${env.EXTENT_REPORT_PATH}|View Extent HTML Report Artifact>
📦 <${env.BUILD_URL}artifact/Extent_Report.zip|Download Full Extent Report ZIP>
📋 <${env.BUILD_URL}testngreports/|View TestNG Report>""",
                tokenCredentialId: 'slackID',
                teamDomain: 'unbxd',
                botUser: true
            )
        }
        aborted {
            slackSend(
                channel: '#qa-automation-reports',
                color: 'warning',
                message: """⏸️ *${env.JOB_NAME.toUpperCase()} ABORTED* ⏸️

🏗️ **Job**: ${env.JOB_NAME}
🔢 **Build**: #${env.BUILD_NUMBER}
🌍 **Environment**: ${params.ENV}
🌿 **Branch**: ${env.BRANCH_NAME ?: 'origin/main'}
🚀 **Build Cause**: 🤖 Manual Trigger

🔗 <${env.BUILD_URL}|View Build>
📊 <${env.BUILD_URL}Extent_Report/|View Reports>
📦 <${env.BUILD_URL}artifact/${env.EXTENT_REPORT_PATH}|View Extent HTML Report Artifact>
📦 <${env.BUILD_URL}artifact/Extent_Report.zip|Download Full Extent Report ZIP>
📋 <${env.BUILD_URL}testngreports/|View TestNG Report>""",
                tokenCredentialId: 'slackID',
                teamDomain: 'unbxd',
                botUser: true
            )
        }
        
        always {
            junit '**/target/surefire-reports/*.xml'
            archiveArtifacts artifacts: 'Extent_Report/index.html,Extent_Report.zip', onlyIfSuccessful: false
            publishHTML(target: [
                allowMissing: true,
                alwaysLinkToLastBuild: true,
                keepAll: true,
                reportDir: 'Extent_Report',
                reportFiles: 'index.html',
                reportName: 'Extent Report'
            ])
        }
    }
}
