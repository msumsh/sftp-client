package io.github.msumsh.tests;

import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import java.util.Arrays;
import java.util.Collections;

public class TestRunner {
    public static void main(String[] args) {
        XmlSuite suite = new XmlSuite();
        suite.setName("SFTP Client Test Suite");

        XmlTest test = new XmlTest(suite);
        test.setName("Unit Tests");
        test.setXmlClasses(Arrays.asList(
                new XmlClass("io.github.msumsh.tests.DomainServiceTest"),
                new XmlClass("io.github.msumsh.tests.JsonUtilTest"),
                new XmlClass("io.github.msumsh.tests.IpValidatorTest")
        ));

        TestNG testng = new TestNG();
        testng.setXmlSuites(Collections.singletonList(suite));
        testng.run();

        System.exit(testng.hasFailure() ? 1 : 0);
    }
}