# GoFile4j

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/b05a911385fa404883066ce138d9b4f3)](https://app.codacy.com/manual/NichtStudioCode/GoFile4j?utm_source=github.com&utm_medium=referral&utm_content=NichtStudioCode/GoFile4j&utm_campaign=Badge_Grade_Settings)

A java wrapper for the <https://gofile.io> API

## Uploading files

Create a new instance of GoFile4j and provide the Files you want to upload
```java
GoFile4j goFile4j = new GoFile4j(file, file1);
```
optional: set these values
```java
goFile4j.setEmail("email@domain.com"); //The email of the account the upload should be associated with (= manage uploads)
goFile4j.setDescription("description"); //The description shown on the download page
goFile4j.setPassword("password"); //The password used to access the download page
goFile4j.setTags("tag1,tag2,tag3"); //The tags
```
Upload the files
```java
goFile4j.upload();
```

## Use the FileUploadResult
The upload method returns a FileUploadResult, here is how you use it:

The two codes ```getCode()``` and ```getRemovalCode()``` return the values from the API.

```getDownloadLink()``` provides a link to the main download page.

```getDirectDownloadLinks()``` returns an array of direct download links. **These links will not work if the main download page hasn't been visited before.**
