# Justify
[![Apache 2.0 License](https://img.shields.io/:license-Apache%202.0-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0)
[![Maven Central](https://img.shields.io/maven-central/v/org.leadpony.justify/justify.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22org.leadpony.justify%22%20AND%20a:%22justify%22)
[![Javadocs](https://www.javadoc.io/badge/org.leadpony.justify/justify.svg?color=green)](https://www.javadoc.io/doc/org.leadpony.justify/justify)
[![Build Status](https://travis-ci.org/leadpony/justify.svg?branch=master)](https://travis-ci.org/leadpony/justify)

Justify is a JSON validator based on [JSON Schema Specification] and [Java API for JSON Processing (JSR 374)].

## Key Features

* Compliant with [JSON Schema Specification] Draft-07.
* Reinforces [Java API for JSON Processing (JSR 374)] transparently with the validation functionality.
* Can be used with [Java API for JSON Binding (JSR 367)] via a custom JsonProvider.
* Reports problems with the source locations including line and column numbers.
* Passes 1,000+ test cases including official ones provided by [JSON Schema Test Suite].
* Accepts custom formats for string and other instance types.
* Supports Java 8, 9, 10 and 11, can be used as a modular jar in Java 9 and higher.
* Internationalized problem messages, including Japanese language support.

## Getting Started

### Minimum Setup

This software is available in the [Maven Central Repository].
In runtime the library requires one of [Java API for JSON Processing (JSR 374)] implementations.
If your choice is [Reference Implementation] of the API,
the following two dependencies are all you need to add to your pom.xml.

```xml
<dependency>
  <groupId>org.leadpony.justify</groupId>
  <artifactId>justify</artifactId>
  <version>0.11.0</version>
</dependency>

<dependency>
  <groupId>org.glassfish</groupId>
  <artifactId>javax.json</artifactId>
  <version>1.1.3</version>
</dependency>
```

Alternatively, the latter dependency can be replaced with other implementation
such as [Apache Johnzon] as below.

```xml
<dependency>
  <groupId>org.apache.johnzon</groupId>
  <artifactId>johnzon-core</artifactId>
  <version>1.1.10</version>
</dependency>
```

### Using with the Streaming API of JSON Processing

```java
JsonValidationService service = JsonValidationService.newInstance();

// Reads the JSON schema
JsonSchema schema = service.readSchema(Paths.get("news.schema.json"));

// Problem handler which will print problems found.
ProblemHandler handler = service.createProblemPrinter(System.out::println);

Path path = Paths.get("fake-news.json");
// Parses the JSON instance by javax.json.stream.JsonParser
try (JsonParser parser = service.createParser(path, schema, handler)) {
    while (parser.hasNext()) {
        JsonParser.Event event = parser.next();
        // Do something useful here
    }
}
```

### Using with the Object Model API of JSON Processing

```java
JsonValidationService service = JsonValidationService.newInstance();

// Reads the JSON schema
JsonSchema schema = service.readSchema(Paths.get("news.schema.json"));

// Problem handler which will print problems found.
ProblemHandler handler = service.createProblemPrinter(System.out::println);

Path path = Paths.get("fake-news.json");
// Reads the JSON instance by javax.json.JsonReader
try (JsonReader reader = service.createReader(path, schema, handler)) {
    JsonValue value = reader.readValue();
    // Do something useful here
}
```

## Command-Line Interface

Justify CLI is a command-line utility for validating JSON documents based on the JSON Schema specification.

### Downloads

Check the [Releases] page to get the latest distribution in `tar.gz` or `zip` format,
whichever you prefer. The software requires Java 8 or higher to run.

### Usage

After unpacking the downloaded file, just typing the following command validates a JSON instance against a JSON schema.

```bash
$ ./justify <path/to/JSON-schema> <path/to/JSON-instance>
```

For validating a JSON schema only:

```bash
$ ./justify <path/to/JSON-schema>
```

Invoking the utility with `-h` option shows the syntax and available options for the utility.

## Additional Resources

* [Justify Examples] which show how to use this library.
* [API Reference in Javadoc]

## Current Development Status

### Schema keywords implemented

* type
* enum
* const
* multipleOf
* maximum/exclusiveMaximum
* minimum/exclusiveMinimum
* maxLength
* minLength
* pattern
* items
* additionalItems
* maxItems
* minItems
* uniqueItems
* contains
* maxProperties
* minProperties
* required
* properties
* patternProperties
* additionalProperties
* dependencies
* propertyNames
* if/then/else
* allOf
* anyOf
* oneOf
* not
* definitions
* title
* description
* format
  * date-time/date/time
  * email (compliant with [RFC 5322])
  * idn-email (compliant with [RFC 6531])
  * hostname (compliant with [RFC 1034])
  * idn-hostname (compliant with [RFC 5890])
  * ipv4 (compliant with [RFC 2673])
  * ipv6 (compliant with [RFC 4291])
  * json-pointer (compliant with [RFC 6901])
  * relative-json-pointer
  * uri/uri-reference (compliant with [RFC 3986])
  * iri/iri-reference (compliant with [RFC 3987])
  * uri-template (compliant with [RFC 6570])
  * regex (compliant with [ECMA 262])
* contentEncoding, with built-in "base64" support.
* contentMediaType, with built-in "application/json" support.

### Schema keywords not implemented yet

* default

## Similar Solutions

There exist several JSON validator implementations conformant to the JSON Schema Specification, including those for other programming languages. [The list of implementations] is available on the JSON Schema web site.

## Copyright Notice
Copyright &copy; 2018-2019 the Justify authors. This software is licensed under [Apache License, Versions 2.0][Apache 2.0 License].

[JSON Schema Specification]: https://json-schema.org/
[Java API for JSON Processing (JSR 374)]: https://javaee.github.io/jsonp/
[Java API for JSON Binding (JSR 367)]: http://json-b.net/
[JSON Schema Test Suite]: https://github.com/json-schema-org/JSON-Schema-Test-Suite
[Apache 2.0 License]: https://www.apache.org/licenses/LICENSE-2.0
[RFC 1034]: https://tools.ietf.org/html/rfc1034.html
[RFC 2673]: https://tools.ietf.org/html/rfc2673.html
[RFC 3986]: https://tools.ietf.org/html/rfc3986.html
[RFC 3987]: https://tools.ietf.org/html/rfc3987.html
[RFC 4291]: https://tools.ietf.org/html/rfc4291.html
[RFC 5322]: https://tools.ietf.org/html/rfc5322.html
[RFC 5890]: https://tools.ietf.org/html/rfc5890.html
[RFC 6531]: https://tools.ietf.org/html/rfc6531.html
[RFC 6570]: https://tools.ietf.org/html/rfc6570.html
[RFC 6901]: https://tools.ietf.org/html/rfc6901.html
[ECMA 262]: https://www.ecma-international.org/publications/standards/Ecma-262.htm
[Justify Examples]: https://github.com/leadpony/justify-examples
[Justify CLI]: https://github.com/leadpony/justify-cli
[API Reference in Javadoc]: https://www.javadoc.io/doc/org.leadpony.justify/justify
[Maven Central Repository]: https://mvnrepository.com/repos/central
[Reference Implementation]: https://github.com/eclipse-ee4j/jsonp
[Apache Johnzon]: https://johnzon.apache.org/
[The list of implementations]: https://json-schema.org/implementations.html  
[Releases]: https://github.com/leadpony/justify/releases/latest
