package com.ubirch.util.crypto.crypto

import com.typesafe.scalalogging.StrictLogging
import com.ubirch.util.crypto.encrypt.DES
import org.apache.commons.codec.binary.Hex
import org.scalatest.featurespec.AnyFeatureSpec
import org.scalatest.matchers.should.Matchers

import java.util.Base64

class DesUtilsSpec extends AnyFeatureSpec
  with StrictLogging
  with Matchers {

  Feature("basic tests") {

    Scenario("encrypt bytes to bytes") {
      val data = "Hallo Welt öäüÖÄÜß".getBytes("UTF-8")

      val secret = "01234567"

      val encData = DES.encrypt(data, secret)

      val decData1 = DES.decrypt(encData, secret)
      data shouldBe decData1

      val decData2 = DES.decryptB64(Base64.getEncoder.encodeToString(encData), secret)
      data shouldBe decData2
    }

    Scenario("encrypt String to bytes") {
      val data = "Hallo Welt öäüÖÄÜß"

      val secret = "01234567"

      val encData = DES.encryptStr(data, secret)

      val decData = DES.decrypt(encData, secret)

      data.getBytes("UTF-8") shouldBe decData
    }

    Scenario("encrypt Base64 to bytes") {
      val data = Base64.getEncoder.encodeToString("Hallo Welt öäüÖÄÜß".getBytes("UTF-8"))

      val secret = "01234567"

      val encData = DES.encryptB64(data, secret)

      val decData = Base64.getEncoder.encodeToString(DES.decrypt(encData, secret))

      data shouldBe decData
    }

    Scenario("encrypt Hex to bytes") {
      val data = "Hallo Welt öäüÖÄÜß"
      val dataBytes = data.getBytes("UTF-8")
      val dataHex = Hex.encodeHexString(dataBytes)

      val secret = "01234567"

      val encData = DES.encryptHex(dataHex, secret)

      val encDataHex = DES.encryptStr2Hex(data, secret)

      val decData = DES.decrypt(encData, secret)

      val decDataStr = DES.decrypHex2Str(encDataHex, secret)

      dataBytes shouldBe decData
      data shouldBe decDataStr

    }

    Scenario("encrypt String to Base64") {
      val data = "Hallo Welt öäüÖÄÜß"
      val dataBytes = "Hallo Welt öäüÖÄÜß".getBytes("UTF-8")

      val secret = "01234567"

      val encData = DES.encryptStr2B64(data, secret)

      val decData = DES.decryptB64(encData, secret)

      val decDataStr = DES.decrypB642Str(encData, secret)

      dataBytes shouldBe decData

      data shouldBe decDataStr
    }

    Scenario("encrypt String to Hex") {
      val data = "Hallo Welt öäüÖÄÜß"
      val dataBytes = "Hallo Welt öäüÖÄÜß".getBytes("UTF-8")

      val secret = "01234567"

      val encData = DES.encryptStr2Hex(data, secret)

      val decData = DES.decryptHex(encData, secret)

      dataBytes shouldBe decData
    }

  }

}
