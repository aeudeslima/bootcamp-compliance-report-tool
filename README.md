# How to Build and Run

You will need Maven, then, in the project directory, just type:

`mvn clean install`

it will generate a jar in target directory, and you can run the app by using:

`java -jar java -jar target/compliance-report-generator-0.0.1-SNAPSHOT.jar`

# Report Generator Util

Documentation: https://drive.google.com/open?id=1B5m3q0v0dYH-XjT4Lh7njfS_EEjT1X6Wpny4Evl_JDI

Video showing the functionalities: https://drive.google.com/open?id=1IwfwpMkczlQAZwW_HFpYHtABcp5uVp2K 

# Spreadsheet configuration

Now it`s possible to configure the spreadsheet to collect the data.

See the video showing this new functionality: https://drive.google.com/open?id=1ZbFslpFF2DAtkPwtPZQeQ4S_3_cRZEJ8

# Additional Information

This is a default Spring Boot project. In the [application.yml](src/main/resources/application.yml), you can find and change info related to the database configuration and google keys and access.

### Database
It it using an H2 database, with can be accessed looking in /h2 path (e.g. http://localhost:8080/h2). This is configured in this snippet of application.yml:

```yaml
h2:
    console:
        enabled: true
        path: /h2
    datasource:
        url: jdbc:h2:file:~/creport
        driverClassName: org.h2.Driver
        username: sa
        password:
```

### File upload size
The maximum file size for upload used in mail attachments is configured in that snippet:
```yaml
servlet:
        multipart:
            max-file-size: 1MB
            max-request-size: 10MB
```

### Google Account for the App

The app is using my account as default, but it must be changed in order to use the correct account in a future use. In this session below there is some configuration for Google Account used to identify the App. 

```yaml
security:
    oauth2:
        client:
            clientId: 989032708997-4mogdf4q7llvtculjgfr7rvm3beban64.apps.googleusercontent.com
            clientSecret: 3Njkpi3MnLyC4fhgjhp-lCMS
            accessTokenUri: https://www.googleapis.com/oauth2/v3/token
            userAuthorizationUri: https://accounts.google.com/o/oauth2/auth
            authenticationScheme: query
            clientAuthenticationScheme: form
            scope: 
              - https://www.googleapis.com/auth/spreadsheets.readonly
              - https://www.googleapis.com/auth/gmail.send
              - profile
        resource:
            userInfoUri: https://www.googleapis.com/userinfo/v2/me
```

To create new credentials to use in the app, please refer to [Google Console](https://console.developers.google.com/apis/credentials) to create a new credential for the App. Then, copy the client id and client secret provided by google, in the corresponding fields of the application.yml file.

Also, make sure that you have Sheets API and GMail API in the same account in order to App works fine.

- [Sheets API](https://console.developers.google.com/apis/library/sheets.googleapis.com)
- [GMail API](https://console.developers.google.com/apis/library/gmail.googleapis.com)


