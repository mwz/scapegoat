package com.sksamuel.scapegoat.inspections

import com.sksamuel.scapegoat.PluginRunner

import org.scalatest.{FreeSpec, Matchers, OneInstancePerTest}

/** @author Stephen Samuel */
class RedundantFinalModifierOnMethodTest
  extends FreeSpec
  with Matchers
  with PluginRunner
  with OneInstancePerTest {

  override val inspections = Seq(new RedundantFinalModifierOnMethod)

  "RedundantFinalModifierOnMethod" - {
    "should report warning" - {
      "when object has final method" in {
        val code = """object Test {  final def foo = {}  } """
        compileCodeSnippet(code)
        compiler.scapegoat.feedback.warnings.size shouldBe 1
      }
      "when final class has final method" in {
        val code = """final class Test {  final def foo = {} } """
        compileCodeSnippet(code)
        compiler.scapegoat.feedback.warnings.size shouldBe 1
      }
      "when case class has final method" in {
        val code = """case class Test() {  final def foo = {} } """
        compileCodeSnippet(code)
        compiler.scapegoat.feedback.warnings.size shouldBe 1
      }
      "when case object has final method" in {
        val code = """case object Test {  final def foo = {} } """
        compileCodeSnippet(code)
        compiler.scapegoat.feedback.warnings.size shouldBe 1
      }
    }
    "should not report warning" - {
      "when non final class has final method" in {
        val code = """class Test {  final def foo = {} } """
        compileCodeSnippet(code)
        compiler.scapegoat.feedback.warnings.size shouldBe 0
      }
      "when trait has final method" in {
        val code = """trait Test {  final def foo = {} } """
        compileCodeSnippet(code)
        compiler.scapegoat.feedback.warnings.size shouldBe 0
      }
      "when abstract class has final method" in {
        val code = """abstract class Test {  final def foo = {} } """
        compileCodeSnippet(code)
        compiler.scapegoat.feedback.warnings.size shouldBe 0
      }
    }
  }
}