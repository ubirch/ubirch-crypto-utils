package com.ubirch.util.crypto.codec

import com.typesafe.scalalogging.StrictLogging
import org.apache.commons.codec.binary.Hex
import org.scalatest.featurespec.AnyFeatureSpec
import org.scalatest.matchers.should.Matchers

import java.util.Base64

class CodecUtilTest extends AnyFeatureSpec
  with StrictLogging
  with Matchers {

  Feature("happy decoding") {


    Scenario("invalid") {
      CodecUtil.multiDecoder("z").isEmpty shouldBe true
    }

    Scenario("valid hex") {
      val testString = "Hallo Welt!"
      val hexString = Hex.encodeHexString(testString.getBytes("UTF-8"))
      val bytes = Hex.decodeHex(hexString.toCharArray)

      val bytesOpt = CodecUtil.multiDecoder(hexString)
      bytesOpt.isDefined shouldBe true

      bytesOpt.get shouldBe bytes
    }

    Scenario("valid base64") {
      val testString = "Hallo Welt!"
      val b64String = Base64.getEncoder.encodeToString(testString.getBytes("UTF-8"))

      val bytesOpt = CodecUtil.multiDecoder(b64String)

      bytesOpt.isDefined shouldBe true
      bytesOpt.get shouldBe testString.getBytes
      testString shouldBe new String(bytesOpt.get, "UTF-8")
    }

  }

}
