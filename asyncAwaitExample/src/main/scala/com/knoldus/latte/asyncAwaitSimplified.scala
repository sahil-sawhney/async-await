package com.knoldus.latte

import java.io.File
import org.apache.commons.io.FileUtils
import scala.async.Async.{async,await}
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global


/**
  * Created by SAHIL SAWHNEY on 2/19/16.
  *
  * the class has a long running method fetchEmployeeName which is executed asynchronously
  */
object asyncAwaitSimplified {

  /**
    * I am a long running loop.......
    */
  private def longRunningTask() {

    for (i <- 1 to 10) {
      for (j <- -250000000 to 250000000) {}
    }
  }

  /**
    * In this method first name of the employee is fetched asynchronously
    *
    * @param folder relative path of the folder which contains file
    * @param file name of the file that contains first name of the employee
    * @return future enclosed first name of employee
    */
  private def fetchFirstName(folder: String, file: String): Future[String] = {

    async {

      val first: String = FileUtils.readFileToString(new File(folder + "/" + file), "utf-8").trim
      longRunningTask() //Assume that fetching the content from file is a long running task
      first
    }
  }

  /**
    * In this method last name of the employee is fetched asynchronously
    *
    * @param folder relative path of the folder which contains file
    * @param file name of the file that contains first name of the employee
    * @return future enclosed last name of employee
    */
  private def fetchLastName(folder: String, file: String): Future[String] = {

    async {

      val last: String = FileUtils.readFileToString(new File(folder + "/" + file), "utf-8").trim
      longRunningTask() //Assume that fetching the content from file is a long running task
      last
    }
  }

  /**
    * In this method 'space' separated full name of the employee is returned
    *
    * @param folder relative path of the folder which contains files of first and last name of employee
    * @return future enclosed full name of employee
    */
  def fetchEmployeeName(folder: String): Future[String] = {

    val fullName: Future[String] = async {
      //The code below will spawn 2 different threads for the methods returning future results
      val firstName: Future[String] = fetchFirstName(folder, "firstNameFile")
      val lastName: Future[String] = fetchLastName(folder, "lastNameFile")
      val result: String = await(firstName) +" "+ await(lastName)
      result
    }
    fullName
  }

}







