# Project 3 - Piscope

![PIScope](https://i.ibb.co/MnLhJhm/piscope.png)

## index
1. [Description](#Description)
2. [Prerequisites](#Prerequisites)
3. [Getting Started](#Getting Started)
4. [Technology Stack](#Technology Stack)
5. [Authors](#Authors)


## Description

PIScope is an application linked to the European patent database: https://www.epo.org/searching-for-patents/data/web-services/ops_fr.html
Its purpose is to allow the user to register his profile, to search for patents and to be able to keep his favorite ones and add comments to them.


## Prerequisites

* [Angular CLI: 13.1.4](https://angular.io/) (check by running ng --version in your console)
* [Node.js 14+](https://nodejs.org/en/) (check by running node –version in your console)
* [Java 8.*](https://www.java.com/fr/) (check by running java --version in your console)
* [Maven 3.*](https://maven.apache.org/) (check by running mvn --version in your console)
* [MySQL 8.0.*](https://www.mysql.com/fr/) (check by running mysql --version in your console)
* [Git 2.*](https://git-scm.com/) (check by running git --version in your console)
* [WARNING : This app use Open Patent Services API](https://www.epo.org/searching-for-patents/data/web-services/ops.html), you also need to get an API key from this service

## Getting Started

If you meet the prerequisites, you can proceed to the installation of the project by running the the following commands:
<br/>
git clone https://github.com/WildCodeSchool/orleans-cda-sept2021-java-project-brevet
<br/>
<b>cd /orleans-cda-sept2021-java-project-brevet</b>
<h2>I. To run the server, follow this steps:</h2>
<h3>1. Install Maven dependencies:</h3>
mvn install
<h3>2. Create the .env file
<h3>3. Add environment variables into .env file you created</h3>
To configure the application, you need to follow these steps to copy these variables below in order into your .env file and replace the variables’ values with your own personal information.
You also have an exemple in the .env.model file . Comments are not recommended on this file. That might provoke some problems to run the application.

* <h4>The list of variables for database</h4>
here you set you database informations

JDBC_DATABASE_URL=mysql://localhost/databaseName
<br/>
DATABASE_USERNAME=mysql_user
<br/>
DATABASE_PASSWORD=mysql-password
<br/>
DATASOURCE_DRIVER_CLASS_NAME=com.mysql.jdbc.Driver
<br/>
PORT=8080
<br/>
JPA_HIBERNATE_DDL_AUTO=update
<br/>
JPA_SHOW_SQL=true
<br/>
<br/>


* <h4>The list of variables for [API OPS](https://www.epo.org/searching-for-patents/data/web-services/ops.html)</h4>
you will need to go on API OPS site web for and sign up, to get the keys

CONSUMMER_KEY=your_consumer_key_for_api_call
<br/>
CONSUMMER_SECRET_KEY=your_secret_key-for_api_call
<br/>
<br/>

* <h4>The list of variables for JWT</h4>
you can choose a jwt secret key and expiration time

JWT_SECRET=choose_your_jwt-secret_key
<br/>
JWT_EXPIRATION_MS=86400000
<br/>
<br/>

* <h4>The list of variables for mail sending (should be replaced by your own email address)</h4>
The exemple here is for gmail you will need to change for your mail

MAIL_HOST=smtp.gmail.com
<br/>
MAIL_PORT=587
<br/>
MAIL_USERNAME=exemple@gmail.com
<br/>
MAIL_PASSWORD=gmailPassword
<br/>
<br/>

* <h4>The list of variables for password resetting</h4>
You can choose in how much time your password token stays valid (in minutes)

PASSWORD_RESER_TOKEN_EXPIRATION=1440
<br/>
<br/>

* You need to find the path for file mailMessage.txt

PATH_FOR_MAIL_MESSAGE=./mailMessage.txt
<br/>
<br/>

* <h4>The list of variables for front server</h4>
You can choose your front server and your port if you are in local you can use localhost and 4200

FRONT_SERVER_NAME=localhost
<br/>
FRONT_SERVER_PORT=4200
<br/>
AUTHORIZED_URLS=http://localhost:4200/
<br/>
<br/>

* <h4>The list of variables URL for EPO and Espace Net URL</h4>
EPO_URL=http://ops.epo.org/3.2/rest-services/
<br/>
PATENT_DATA_URL=published-data/publication/epodoc/
<br/>
PATENT_SEARCH_URL=published-data/search/full-cycle
<br/>
ESPACENET_URL=https://worldwide.espacenet.com/patent/search/family/
<br/>
<br/>
Note: On Windows: all variables must be inline separated by 1 space.
<br/>
<br/>
4. Import the last version of the dataVx.sql file into your database
5. To run the server of the application
* On Linux: run the command <b>sh ./env_spring_boot_run.sh</b>
* On Windows:  
open Git Bash
Go to the root directory of the project orleans-cda-sept2021-java-project-brevet
<br/>
Run the command: <b>sh env_spring_boot_run.sh</b>
<br/>
<h2>II. To run the client, follow this steps:</h2>
1. Go into the client side folder by running the command:
<br/>
<b>cd angular/</b>
2. Install Angular dependencies:
<br/>
<b>npm install</b>
3. Run server
<br/>
<b>ng serve</b>

## Technology Stack

<table>
    <tr>
        <th>Component</th>
        <th colspan="2">Technology</th>
    </tr>
   <tr>
       <td>Backend(REST)</td>
       <td>Spring boot 2 (Java)</td>

   </tr>
   <tr>
       <td>Frontend</td>
       <td>Angular 13</td>
   </tr>
   <tr>
       <td>Security</td>
       <td>JWT Authorization</td>
   </tr>
   <tr>
       <td>Database</td>
       <td>MySQL</td>
   </tr>
   <tr>
       <td>Persistance</td>
       <td>JPA</td>
   </tr>
   <tr>
       <td>Server Build Tools</td>
       <td>Maven</td>
   </tr>
   <tr>
       <td>Client Build Tools</td>
       <td>angular-cli, npm install</td>
   </tr>
</table>

## Authors
* [Lahcen Boukkoutti](https://github.com/misterdev45)
* [Zurabi Grialat](https://github.com/jaldabaoth-code)
* [Thuy Dieu](https://github.com/Thuydieutran)
* [Raphaël Billet Servoin](https://github.com/RaphaelBS-WCS)
* [Gersey Stelmach](https://github.com/gerseystelmach)
