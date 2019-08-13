# image-provider
A Spring Boot Image provider with ftp interaction where you can upload images to a file server through FTP and save the path and others info in your DataBase. He can return informations to you based on the route you enter and the uri params.

## Examples
All the Uri's bellow belongs to my private app using this api.

Try with https://romullo-image-provider.herokuapp.com + one of options bellow.

* Return all images and infos from database: **/api/imagens**
* Return all images with Tags or id matching the query:
  * id:  **/api/imagens/id/123** (need authentication)
  * tag: **/api/imagens/tag/anime**


## How to Use
You will need to configure your FTP Server info on src/main/java/com/romullocordeiro/imageprovider/resources/, mine is a test server. You will need to configure your database options in build.gradle and /src/main/resources/application.properties
