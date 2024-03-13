/*
    CREDENCIALES NECESARIAS
    - SonarQube     (Token de acceso)
    - Github        (Usuario y clave)
    - DockerHub     (Usuario y clave)
    - Kubernetes    (Token del service account de Jenkins)

    HERRAMIENTAS NECESARIAS
    - Java 20
    - Docker 24.0.2 (Cualquier versión que soporte --password-stdin)
    - Maven 3.9.3
    - NodeJS 16.20.1

    PLUGINS NECESARIOS
    - Slack
    - Docker
    - Kubernetes
    - JUnit
    - EmailText

    CONSIDERACIONES
    * Se debe configurar como multibranch pipeline
    * Deben existir las ramas master y develop
    * Se debe configurar la cloud de kubernetes para el acceso al servidor
 */
 
def ARTIFACT_ID
def IDENTIFICADOR_PROYECTO
def IDENTIFICADOR_UNICO_BUILD
def RAMA_PARA_CLONAR

pipeline {

agent any 

   tools {
        // Install the Maven version configured as "M3" and add it to the path.
        maven "maven3.8.8"
        jdk 'java21'
        dockerTool 'docker-2'
    }

    environment {

        HORA_DESPLIEGUE = sh(returnStdout: true, script: "date '+%A %W %Y %X'").trim()

        GITHUB_DESPLIEGUE_URL = "https://github.com/dim-desarrollo/ventanilla-monolito.git"

        GITHUB_CREDENCIALES = "github"
        DOCKERHUB_CREDENCIALES = "dockerhub"
        KUBERNETES_CREDENCIALES = "k8s-jenkins-account-15"
        SONARQUBE_CREDENCIALES = 'sonarqube'
        PUERTO_EXTERNO = 4002

        CANAL_SLACK = "#canal-slack"            // TODO: Por reemplazar
        CORREO_A_NOTIFICAR = "dim@gmail.com"    // TODO: Por reemplazar

        CARPETA_APLICACION = './'
        //CARPETA_DESPLIEGUE = 'despliegue'

    }

   stages {



    stage('Message start deploy') {

        steps {

            discordSend description: "Inicio de deploy!!!", footer: "Inicado", link: env.BUILD_URL, result: currentBuild.currentResult, title: "Deploy BackEnd-domicilio-fiscal", webhookURL: "https://discord.com/api/webhooks/1173648912838561922/iB8YUryvKbcj66EWQa2e6161BDuygkfaMx57VUalxPnDAMvoRHcYKxJTaxV4nfBEdoxi"

        }

    }

    stage('Iniciando variables') {

        steps {

            dir("${CARPETA_APLICACION}"){

                script {

                    PROYECTO_VERSION = sh(returnStdout: true, script: 'mvn help:evaluate -Dexpression=project.version -q -DforceStdout')
                    ARTIFACT_ID = sh(script: "mvn help:evaluate -Dexpression=project.artifactId -f pom.xml -q -DforceStdout", returnStdout: true).trim()
                    IDENTIFICADOR_PROYECTO = "${ARTIFACT_ID}:${PROYECTO_VERSION}"
                    IDENTIFICADOR_UNICO_BUILD = "${IDENTIFICADOR_PROYECTO}.${BUILD_NUMBER}"

                }

            }


        }

    }

    /*

    stage('SonarQube Analysis') {

    environment {

        SONAR_SCANNER_HOME = tool 'sonarScaner' //nombre en la configuracion de las tools de jenkins 
        SONAR_SERVER = 'sonarqube' 
        SONAR_HOST_IP = '172.17.0.4' // IP interna de Docker de SonarQube, debido a que SonarQube corre en un contenedor (docker inspect nombre_contenedo_SonarQube)
        SONAR_PORT = '9000' //puerto donde esta trabajando el contenedor
        SONAR_SRC = 'src/'
        SONAR_ENCODING = 'UTF-8'

    }


    steps {
            dir("${CARPETA_APLICACION}"){
                withSonarQubeEnv(installationName: "${SONAR_SERVER}", credentialsId: "${SONARQUBE_CREDENCIALES}") {
                    sh "${SONAR_SCANNER_HOME}/bin/sonar-scanner \
                        -Dsonar.projectName=${ARTIFACT_ID} \
                        -Dsonar.projectVersion=${PROYECTO_VERSION} \
                        -Dsonar.projectKey=${IDENTIFICADOR_PROYECTO} \
                        -Dsonar.host.url=http://${SONAR_HOST_IP}:${SONAR_PORT} \
                        -Dsonar.sources=${SONAR_SRC} \
                        -Dsonar.java.binaries=. \
                        -Dsonar.sourceEncoding=${SONAR_ENCODING}"
                }
            }
        }
    }   
    */
      stage('Tools initialization') {
          steps {
              script {

                    if (env.BRANCH_NAME){

                        RAMA_PARA_CLONAR = env.BRANCH_NAME

                    }
                    else{

                        RAMA_PARA_CLONAR = 'Dev'

                    }


                  DOCKER_VERSION = sh(returnStdout: true, script: 'docker version').trim()
                  JAVA_VERSION = sh(returnStdout: true, script: 'java -version').trim()
                  MAVEN_VERSION = sh(returnStdout: true, script: 'mvn -v').trim()

                  echo "Docker version: ${DOCKER_VERSION}"
                  echo "Java version: ${JAVA_VERSION}"
                  echo "Maven version: ${MAVEN_VERSION}"
              }
          }
      }

      
      stage('Build Maven') {  
               
            steps{

                    sh "mvn -version"
                    sh "mvn clean package -DskipTests"

                }

            }
         
      

     stage('Build and run') {

        environment{
             PUERTO_INTERNO = 8091
             NOMBRE_CONTENEDOR = " domicilio-fiscal"
             IDENTIFICADOR_IMAGEN = "domicilio-fiscal"
         }
          
        steps {
            script {

                dir ("${CARPETA_APLICACION}"){
                    // Verifica si existe un archivo Dockerfile en la subcarpeta actual
                    if (!fileExists("Dockerfile")) {
                        error "Dockerfile not found"
                    }   

                    // Revisar si existe un contenedor con el mismo nombre

                    
                    def contenedorExistente = sh(script: "docker ps -aqf name=${NOMBRE_CONTENEDOR}", returnStdout: true).trim()

                    sh "echo ID CONTENEDOR: $contenedorExistente"

                    // Verifica si existe un contenedor con el mismo nombre
                    if (contenedorExistente) {
                        // Si existe el contenedor, pausarlo y luego eliminarlo
                        //sh "echo contenedores existiendo (BORRADOS)"
                        sh "docker stop $contenedorExistente"
                        sh "docker rm -f $contenedorExistente"
                    }

                    

                    sh "docker build -t ${IDENTIFICADOR_IMAGEN} ."

                    sh "docker run --network estaciones_my-network-login -d -p ${PUERTO_EXTERNO}:${PUERTO_INTERNO} --name ${NOMBRE_CONTENEDOR} ${IDENTIFICADOR_IMAGEN}"

                }

            }
        }
          
     }

    /*
    stage('Deploy kubernetes'){

        when { //when: Es una directiva condicional que controla cuándo debe ejecutarse este stage
            anyOf { // anyOf: significa que el stage se ejecutará si al menos una de estas condiciones es verdadera

                branch 'master'
                branch 'develop'

                expression{
                    return (RAMA_PARA_CLONAR == 'develop' || RAMA_PARA_CLONAR == 'master') // condicional
                }
            }
        }

        environment {

            CARPETA_MANIFIESTO = "${RAMA_PARA_CLONAR == 'master' ? 'prod' : 'dev'}"
            DIRECCION_DESPLIEGUE = "${WORKSPACE}/${CARPETA_DESPLIEGUE}" 

            //para exponer el cluster a este puerto tenes que tirar el siguiente comando en el servidor
            //kubectl proxy --accept-hosts '^.*' --address '0.0.0.0' --port 8445
            KUBE_SERVIDOR = "172.20.255.15:8445" //IP DEL SERVIDOR D ONDE ESTAS DESPLEGADO EL CLUSTER, PUERTO POR DEFECTO

        }

        steps {

            checkout scmGit(branches: [[name: 'master']], extensions: [[$class: 'RelativeTargetDirectory', relativeTargetDir: "${CARPETA_DESPLIEGUE}"]], userRemoteConfigs: [[credentialsId: "${GITHUB_CREDENCIALES_DEPLOY}", url: "${GITHUB_DESPLIEGUE_URL}"]])

            dir ("${CARPETA_DESPLIEGUE}"){
                
                withCredentials([usernamePassword(credentialsId: "${DOCKERHUB_CREDENCIALES}", usernameVariable: 'DOCKERHUB_USERNAME', passwordVariable: 'DOCKERHUB_PASSWORD')]) {

                    // Actualiza el archivo de despliegue con la última versión de la aplicación
                    sh "sed -i s+\$DOCKERHUB_USERNAME/${ARTIFACT_ID}:TAG+\$DOCKERHUB_USERNAME/${ARTIFACT_ID}:${IDENTIFICADOR_UNICO_BUILD}+g ${DIRECCION_DESPLIEGUE}/${CARPETA_MANIFIESTO}/general/monolito.yaml"

                    sh "cat ${DIRECCION_DESPLIEGUE}/${CARPETA_MANIFIESTO}/general/monolito.yaml"
                }


                withCredentials([string(credentialsId: "${KUBERNETES_CREDENCIALES}", variable: 'KUBE_TOKEN')]) {
                    // sh 'kubectl --token $KUBE_TOKEN --server ${SEVER} --insecure-skip-lts-verify=true apply -f ${FOLDER}'
                    sh "kubectl --token \$KUBE_TOKEN --server ${KUBE_SERVIDOR} apply -R -f ${DIRECCION_DESPLIEGUE}/${CARPETA_MANIFIESTO}/basedatos"
                    sh "kubectl --token \$KUBE_TOKEN --server ${KUBE_SERVIDOR} apply -R -f ${DIRECCION_DESPLIEGUE}/${CARPETA_MANIFIESTO}/general"
                }
            }

        }

    }
    */

    stage('Message finish deploy') {


        steps {

            sh "echo ${HORA_DESPLIEGUE}"

            discordSend description: "Deploy de estaciones echo!!!", footer: "Hora de inicio de despliegue: ${HORA_DESPLIEGUE} ", link: env.BUILD_URL, result: currentBuild.currentResult, title: "Deploy BackEnd-domicilio-fiscal", webhookURL: "https://discord.com/api/webhooks/1173648912838561922/iB8YUryvKbcj66EWQa2e6161BDuygkfaMx57VUalxPnDAMvoRHcYKxJTaxV4nfBEdoxi"

        }

    }

   }


 }
