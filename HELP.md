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

https://github.com/bohdanius/ocp-pipeline-test

oc delete project pipeline
oc new-project pipeline
oc apply -f pipeline.yml
oc process -p APP_NAME=springboot-ci ci-build | oc create -f -
oc start-build springboot-ci