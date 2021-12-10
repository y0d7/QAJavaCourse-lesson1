package com.officelibrary.library;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

class ArchUnitTest {

    private static final String ROOT_PACKAGE = "com.officelibrary.library";

    private JavaClasses classes;

    @BeforeEach
    public void setup() {
        classes = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_ARCHIVES)
            .importPackages("com.officelibrary.library");
        System.out.println(classes);
    }

    @Test
    void serviceClassesNamesShouldEndWithService() {
        classes()
            .that().resideInAPackage(ROOT_PACKAGE + ".service")
            .should().haveSimpleNameEndingWith("Service")
            .check(classes);
    }

    @Test
    void utilClassesShouldBePackagePrivate() {
        classes()
            .that().haveSimpleNameEndingWith("Util")
            .should()
            .haveOnlyPrivateConstructors()
            .check(classes);
    }

    @Test
    void controllerClassesShouldHaveSpringRestControllerAnnotation() {
        ArchRule rule = classes()
            .that().resideInAPackage(ROOT_PACKAGE + ".exposure.controller")
            .should().beAnnotatedWith(RestController.class)
            .orShould().beAnnotatedWith(Controller.class);
        rule.check(classes);
    }
}
