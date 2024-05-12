# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## Unreleased

### Added
- Kotlin support

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