# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## 1.3.0 - 2024-12-27

### Added
- Kotlin support.
- The Scala API has been changed. The ```given```, ```when```, ```then``` and ```and``` method names no longer begin with uppercase letters.
  The method names ```given``` and ```then```, which are reserved words in Scala, must be enclosed in backquotes.
  See the [Scala code examples](doc/scala-example.md) for more information.

### Fixed
- The Scala wrapper is no longer necessary. It has been removed.

## 1.2.3 - 2024-12-16

### Fixed
- Updated README documentation.
- Restored code examples referenced in the documentation. Somehow, these had gotten lost in a previous update.

## 1.2.2 - 2024-11-09

### Fixed
- If your project has a module descriptor that requires org.slf4j, tests will not run because slf4j classes are available from your project and
gwt-test, which results in a resolution error. The gwt-test POM has been modified to omit the slf4j artifact in the generated jar. This means that if
you want logging in your GWT tests, you'll need to include org.slf4j:slf4j-api as well as an slf4j implementation as dependencies.

## 1.2.1 - 2024-05-12

### Fixed
- If a test throws an exception, it was caught and logged, but the test could still pass as a false positive if the exception being thrown is not expected within the 
test. The fix allows an expected exception class to be declared and if an exception of a different class is thrown during a test, the test will fail. 

## 1.2.0 - 2023-10-28

### Added
- Scala support.

### Fixed
- Log exceptions when they caught during a test, in addition to capturing them.

## 1.1.0 - 2023-06-09

### Added
- Multiple when-then in the same test.

## 1.0.0 - 2023-03-03

### Added
- Initial release of gwt-test.