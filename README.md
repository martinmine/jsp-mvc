# README #

This is a demo of how the MVC pattern can be used with JSP files using a servlet filter.
The web application allows a user to list all available blog posts from a database, as well as view a specific post in addition to create a new post. 
When used correctly, no Java code is embedded in the JSP file. This is avoided by using EL (Expression Language) and JSP Standard Tag Library (JSTL).

Each page that is accessible to the user consists of the following parts:
* Page descriptor entry (pages), declaring which view, controller and URI belongs together (pages.xml)
* JSP file (view)
* Page controller
* Associated beans (if any, this example has an extensive use of the class BlogPost)

Each call is fist received in a filter which will find the associated controller for the URI as specified by pages.xml. If a controller is found, the logic in the controller will be invoked. Afterwards, the request will be forwarded to the view associated with the controller. If no controller is found, the request will be forwarded to the 404.jsp file. The JSP file will then render based on the values set by the controller. 

![JSP MVC.png](https://bitbucket.org/repo/jBk4a8/images/1139313143-JSP%20MVC.png)

# Libraries #
This demo app relies on the following libraries through Maven:
* [MySQL Connector/J (5.1.36) ](http://mvnrepository.com/artifact/mysql/mysql-connector-java/5.1.36)
* [Jstl (1.2)](http://mvnrepository.com/artifact/javax.servlet/jstl/1.2) 

# Limitation #
* This application has configuration for the database hard-coded in the class DatabaseManager.java.
* The pages.xml could be replaced with annotations in the code, simplifying the framework.
* Requests for saving posts does not have any validation as this demo focuses on demonstrating the MVC pattern.
* Annotations for only HTTP GET and POST are implemented.
* The methods for invoking the controllers that are annotated with @Get or @Post must follow the same signature (See todo in SiteRouter::init).

# References #
This application is based on the MVC approach discussed in the book J2EE Design Patterns by William Crawford and Jonathan Kaplan.