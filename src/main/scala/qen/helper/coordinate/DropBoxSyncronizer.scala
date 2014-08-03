package qen.helper.coordinate

import com.dropbox.core._;
import java.io._;
import java.util.Locale;
import com.typesafe.config._

class DropBoxSyncronizer(absoluteConfig: Config) {
  val config: DbxRequestConfig = new DbxRequestConfig("CoordinateHelper", Locale.getDefault().toString());
  var accessToken = "";
  if(absoluteConfig.hasPath("dropbox.access.token")){
    accessToken = absoluteConfig.getString("dropbox.access.token")
  } else {
    accessToken = this.requestAccessToken(config)
  }
  val client: DbxClient = new DbxClient(config, accessToken);
  println("Linked account: " + client.getAccountInfo().displayName);
  def refreshFile(kmlFile: File): Unit = {
    val inputStream: FileInputStream = new FileInputStream(kmlFile);
    try {
    	val dropboxKMLFilePath = "/"+kmlFile.getName()
        val uploadedFile: DbxEntry.File = client.uploadFile(dropboxKMLFilePath,
        DbxWriteMode.force(), kmlFile.length(), inputStream);
      println("You access your file under "+client.createShareableUrl(dropboxKMLFilePath).replace("www.dropbox.com", "dl.dropboxusercontent.com"))
      println("Uploaded: " + uploadedFile.toString());
    } finally {
      inputStream.close();
    }
  }
  def requestAccessToken(config: DbxRequestConfig): String = {
    val APP_KEY: String = "lhp0r5rk7undqjm";
    val APP_SECRET: String = "9qq7if3kj0clnmo";
    val appInfo: DbxAppInfo = new DbxAppInfo(APP_KEY, APP_SECRET);
   
    val webAuth: DbxWebAuthNoRedirect = new DbxWebAuthNoRedirect(config, appInfo);
    val authorizeUrl: String = webAuth.start();
    println("1. Go to: " + authorizeUrl);
    println("2. Click \"Allow\" (you might have to log in first)");
    println("3. Copy the authorization code.");
    val code: String = new BufferedReader(new InputStreamReader(System.in)).readLine().trim();

    val authFinish: DbxAuthFinish = webAuth.finish(code);
    val accessToken = authFinish.accessToken
    println(s"Your access token is $accessToken. Put it under dropbox.access.token in the application.conf file.")
    return accessToken;
  }
}