# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
* [Building a RESTful Web Service with Spring Boot Actuator](https://spring.io/guides/gs/actuator-service/)

jenkins-pipeline
dualstack.nodes-openshift-2010944965.us-east-1.elb.amazonaws.com.
oc get is -n openshift | awk '{ print $1 }'
oc get -o yaml --export all > project.yaml

oc import-image  -n openshift s2i-java:latest --from=fabric8/s2i-java:3.0-java8 --confirm


https://github.com/bohdanius/ocp-pipeline-test

oc delete project pipeline
oc delete project pipeline-sit

oc new-project pipeline-sit
oc new-project pipeline

oc policy add-role-to-user edit system:serviceaccount:pipeline:jenkins -n pipeline-sit
oc apply -f openshift/pipeline.yml
oc process -p APP_NAME=springboot-app ci-build  | oc create -f -
oc start-build springboot-app-ci
