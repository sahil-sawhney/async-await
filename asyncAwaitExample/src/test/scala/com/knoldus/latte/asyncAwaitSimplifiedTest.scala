package com.knoldus.latte

import java.io.File

import org.apache.commons.io.FileUtils
import org.scalatest.FunSuite
import scala.concurrent.{Await, Future}
import scala.concurrent.duration._

/**
  * Created by sahil on 2/20/16.
  *
  * this class tests the  result obtained from fetchEmployeeName
  */
class asyncAwaitSimplifiedTest extends FunSuite {

  /**
    * this test case checks whether the result obtained by performing io operations asynchronously
    * ie from fetchEmployeeName method is correct or not....
    */
  test("Name fetched successfully") {

    val folderName = "./src/test/resources"
    val futureResult: Future[String] = asyncAwaitSimplified.fetchEmployeeName(folderName)
    val first: String = FileUtils.readFileToString(new File(folderName + "/" + "firstNameFile"), "utf-8").trim
    val last: String = FileUtils.readFileToString(new File(folderName + "/" + "lastNameFile"), "utf-8").trim
    val actualResult: String = Await.result(futureResult, 10 seconds)
    val expectedResult: String = first + " " + last
    assert(actualResult === expectedResult)
  }

}
