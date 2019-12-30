# find-service

Quarkus based backend service for the Find My Relative demo application.

Frontend Service can be found [here](https://github.com/Emergency-Response-Demo/findmyrelative-frontend). 

## Running

- Locally

    Modify the `INCIDENT_SERVICE_URL` and `MISSION_SERVICE_URL` in `application.properties` file.

    Run `./mvnw compile quarkus:dev`
  
- Deploy on OpenShift

  You can deploy using Dockerfile on OpenShift Webconsole or run the following command.
  
  ```
  oc new-app --name find-service --strategy docker https://github.com/Emergency-Response-Demo/find-service
  ```
  
  You will need to pass `INCIDENT_SERVICE_URL` and `MISSION_SERVICE_URL` as Environment variable.
  
## Deploying using Tekton Pipeline

* Prerequisite

    - OpenShift v4.x Cluster with cluster admin permissions.
     
    - Tekton Pipeline Installed. You can install using `OpenShift Pipelines Operator` from the Operator Hub.
    
    - Knative Installed. You can follow this [steps](https://docs.openshift.com/container-platform/4.2/serverless/installing-openshift-serverless.html) to install Serverless in OpenShift. 
    
    - Tekton CLI (tkn) (Optional). Download the Tekton CLI by following [instructions](https://github.com/tektoncd/cli#installing-tkn) available on the CLI GitHub repository.

* Installation Steps

    1. Fork this repository so that you can create a trigger for any GitHub Event which will trigger the pipeline and deploy the new code.
    
    2. Clone the repository to your local machine.
    
       ```
       git clone https://github.com/<your-github-username>/find-service
       ```
    
    3. Traverse to the pipeline folder.
    
        ```
        cd find-service/pipeline/
        ```
        
    4. Login to your OpenShift Cluster.
    
    5. Create a new project `find-my-relative`
    
       ``` 
       oc new-project find-my-relative
       ```
    
    6. Install the Pipeline Resources,Task and Pipeline.
    
        1. Pipeline - There are two task in pipeline - buildah and openshift-client.
        
            Install pipeline using below command - 
        
            ```
            02-pipelines/01-findmyrelative-backend-pipeline.yaml
            ```
         
        2. Tasks - 
            
            - buildah - This task build a image using the Dockerfile and then push it to repository which we will specify in pipeline resource.
            
            - openshift-client - Here, this task is used to apply knative service which will deploy the image as serverless.
            
            buildah and openshift-client used from the cluster task. So, they come pre-installed with the OpenShift Pipelines Operator.
            
        3. Pipeline Resources - First is git resource where we give git url of repository and second is image resource where the image will be pushed.
        
            Here we are using OpenShift Internal Registry but you can also use any external registry like DockerHub, Quay. 
            
            Before Installing Resources, Replace your git url of find-service in `01-findmyrelative-backend-git-resource.yaml`
            
            Now, you can install using below command - 
            
            ```
            oc apply -f 01-pipelineresources/
            ``` 
        
        Now, we are ready to run the pipeline. You can run it by using below command or Go to OpenShift Web Console -> find-my-relative Project -> Pipeline Tab -> Pipeline -> Click on pipeline and Start.
        
        ``` 
        tkn pipeline start findmyrelative-backend-pipeline -r source-git-repo=findmyrelative-backend-git-repo -r image-resource-name=findmyrelative-backend-image -s pipeline
        ```  
        
        Also, You can start pipeline using pipeline run.      
        
        ```
        oc apply -f 03-pipelineruns/01-findmyrelative-backend-pipelinerun.yaml
        ```
              
    7. Next Step is to create a trigger so that on any code change in GitHub the pipeline will start and deploy the new code. 
         
        Install Event Listener, Trigger Template and Trigger binding.
        
        ```
        oc apply -f 04-pipeline-triggers/
        ```
        
        New pod will be created for Event listener. Get the url for Event Listener which we will need for creating Webhook - ` oc get route`.
    
    8. Create a webhook  -
    
        Firstly, create a GitHub Personal access token. Follow this [instructions](https://help.github.com/en/github/authenticating-to-github/creating-a-personal-access-token-for-the-command-line#creating-a-token) to create a GitHub token.
        
        The token should have the access - `public_repo`  and `admin:repo_hook`.  
        
        To create a webhook - Go to GitHub Repository -> Settings -> Webhooks -> Add webhook -> Add the EventListener url, the token as secret and select the event.
        
        Also, you can create a webhook using Webhook task which you can find in `03_create-webhook-task.yaml`. Follow below steps to create using a task - 
        
          1. Add your GitHub Personal access token and Random String data in `secret` in `02_webhook-secret.yaml`.
       
          2. In `04_backend-webhook-run.yaml` add your GitHub Username for `GitHubOrg` and `GitHubUser`. Add the Event Listener's url for `ExternalDomain`.
        
        Now, install the task and the task run.
        
        ```
        oc apply -f 05-github-webhooks/
        ```
        
        If you go to Github, you can see a webhook created for the repository.   
        
    9. Now, when you change code and push it to repository. You can see a new pipelinerun is started.              
        
