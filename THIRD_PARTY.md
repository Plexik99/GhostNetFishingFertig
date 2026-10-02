# Fremdkomponenten und Lizenzhinweise

Stand: aufgelöster Maven-Build vom 1. Oktober 2026. Diese Bestandsaufnahme umfasst 98 Abhängigkeiten einschließlich Testumfang, davon 75 im Compile-/Runtime-Umfang. Maven-Buildplugins und das JDK sind nicht Bestandteil dieser Zahl. Die Angaben stammen aus den jeweiligen Paketmetadaten, bei H2 zusätzlich von der offiziellen Lizenzseite. Eine Liste von Lizenznamen ersetzt nicht die Originalbedingungen.

Das Quellcodepaket enthält keine mitgelieferten Fremd-JARs. Bei `mvn package` werden dagegen Bibliotheken in die ausführbare Spring-Boot-JAR aufgenommen. Vor einer Weitergabe dieses Binärpakets sind die jeweiligen Bedingungen, eingebetteten Lizenz- und NOTICE-Dateien sowie etwaige Pflichten zur Bereitstellung oder zum Angebot des Quellcodes zu beachten. Diese Datei erteilt keine Lizenz für den eigenen Projektcode; eine eigene Lizenz muss der Rechteinhaber bewusst wählen.

Wichtige Originalinformationen:

- Apache License 2.0, insbesondere Abschnitt 4: <https://www.apache.org/licenses/LICENSE-2.0>
- Hibernate: <https://hibernate.org/community/license/>. Hibernate ORM 6.6.53.Final verwendet LGPL 2.1 oder später; eine Apache-Lizenz neuerer Hauptversionen darf darauf nicht übertragen werden.
- H2: <https://h2database.com/html/license.html> (MPL 2.0 oder EPL 1.0).
- Thymeleaf: <https://www.thymeleaf.org/> (Apache License 2.0).

Vorhandene Hinweise dürfen nicht entfernt werden. Bei Änderungen oder einer anderen Verteilung muss die Bestandsaufnahme erneut mit dem tatsächlich ausgelieferten Artefakt abgeglichen werden. Mehrere in einer POM genannte Lizenzen sind hier wörtliche Metadaten und keine pauschale Aussage, dass alle alternativ oder gleichzeitig gelten.

Die Anwendung lädt keine externen Fotos, Kartenkacheln oder Webfonts. Die Projektherkunft und eingesetzte Unterstützung sind in der README dokumentiert. Ungeklärte Rechte an nachträglich ergänzten Inhalten sind dadurch nicht abgedeckt.

## Aufgelöste Abhängigkeiten

Die maschinenlesbare Datei `docs/dependencies-2026-10-01.json` enthält zusätzlich die Fundstellen eingebetteter Lizenz- und NOTICE-Dateien. Die POM-Links dienen der Nachprüfung.

| Maven-Koordinate | Umfang | Lizenzangabe in den Metadaten | Quelle |
|---|---|---|---|
| `org.springframework.boot:spring-boot-starter-web:3.5.16` | compile | Apache License, Version 2.0 | [Original](https://repo.maven.apache.org/maven2/org/springframework/boot/spring-boot-starter-web/3.5.16/spring-boot-starter-web-3.5.16.pom) |
| `org.springframework.boot:spring-boot-starter:3.5.16` | compile | Apache License, Version 2.0 | [Original](https://repo.maven.apache.org/maven2/org/springframework/boot/spring-boot-starter/3.5.16/spring-boot-starter-3.5.16.pom) |
| `org.springframework.boot:spring-boot:3.5.16` | compile | Apache License, Version 2.0 | [Original](https://repo.maven.apache.org/maven2/org/springframework/boot/spring-boot/3.5.16/spring-boot-3.5.16.pom) |
| `org.springframework.boot:spring-boot-autoconfigure:3.5.16` | compile | Apache License, Version 2.0 | [Original](https://repo.maven.apache.org/maven2/org/springframework/boot/spring-boot-autoconfigure/3.5.16/spring-boot-autoconfigure-3.5.16.pom) |
| `org.springframework.boot:spring-boot-starter-logging:3.5.16` | compile | Apache License, Version 2.0 | [Original](https://repo.maven.apache.org/maven2/org/springframework/boot/spring-boot-starter-logging/3.5.16/spring-boot-starter-logging-3.5.16.pom) |
| `ch.qos.logback:logback-classic:1.5.34` | compile | EPL-2.0; LGPL-2.1-only | [Original](https://repo.maven.apache.org/maven2/ch/qos/logback/logback-classic/1.5.34/logback-classic-1.5.34.pom) |
| `ch.qos.logback:logback-core:1.5.34` | compile | EPL-2.0; LGPL-2.1-only | [Original](https://repo.maven.apache.org/maven2/ch/qos/logback/logback-core/1.5.34/logback-core-1.5.34.pom) |
| `org.apache.logging.log4j:log4j-to-slf4j:2.24.3` | compile | Apache-2.0 | [Original](https://repo.maven.apache.org/maven2/org/apache/logging/log4j/log4j-to-slf4j/2.24.3/log4j-to-slf4j-2.24.3.pom) |
| `org.apache.logging.log4j:log4j-api:2.24.3` | compile | Apache-2.0 | [Original](https://repo.maven.apache.org/maven2/org/apache/logging/log4j/log4j-api/2.24.3/log4j-api-2.24.3.pom) |
| `org.slf4j:jul-to-slf4j:2.0.18` | compile | MIT | [Original](https://repo.maven.apache.org/maven2/org/slf4j/jul-to-slf4j/2.0.18/jul-to-slf4j-2.0.18.pom) |
| `jakarta.annotation:jakarta.annotation-api:2.1.1` | compile | EPL 2.0; GPL2 w/ CPE | [Original](https://repo.maven.apache.org/maven2/jakarta/annotation/jakarta.annotation-api/2.1.1/jakarta.annotation-api-2.1.1.pom) |
| `org.yaml:snakeyaml:2.4` | compile | Apache License, Version 2.0 | [Original](https://repo.maven.apache.org/maven2/org/yaml/snakeyaml/2.4/snakeyaml-2.4.pom) |
| `org.springframework.boot:spring-boot-starter-json:3.5.16` | compile | Apache License, Version 2.0 | [Original](https://repo.maven.apache.org/maven2/org/springframework/boot/spring-boot-starter-json/3.5.16/spring-boot-starter-json-3.5.16.pom) |
| `com.fasterxml.jackson.core:jackson-databind:2.21.4` | compile | The Apache Software License, Version 2.0 | [Original](https://repo.maven.apache.org/maven2/com/fasterxml/jackson/core/jackson-databind/2.21.4/jackson-databind-2.21.4.pom) |
| `com.fasterxml.jackson.core:jackson-annotations:2.21` | compile | The Apache Software License, Version 2.0 | [Original](https://repo.maven.apache.org/maven2/com/fasterxml/jackson/core/jackson-annotations/2.21/jackson-annotations-2.21.pom) |
| `com.fasterxml.jackson.core:jackson-core:2.21.4` | compile | The Apache Software License, Version 2.0 | [Original](https://repo.maven.apache.org/maven2/com/fasterxml/jackson/core/jackson-core/2.21.4/jackson-core-2.21.4.pom) |
| `com.fasterxml.jackson.datatype:jackson-datatype-jdk8:2.21.4` | compile | The Apache Software License, Version 2.0 | [Original](https://repo.maven.apache.org/maven2/com/fasterxml/jackson/datatype/jackson-datatype-jdk8/2.21.4/jackson-datatype-jdk8-2.21.4.pom) |
| `com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.21.4` | compile | The Apache Software License, Version 2.0 | [Original](https://repo.maven.apache.org/maven2/com/fasterxml/jackson/datatype/jackson-datatype-jsr310/2.21.4/jackson-datatype-jsr310-2.21.4.pom) |
| `com.fasterxml.jackson.module:jackson-module-parameter-names:2.21.4` | compile | The Apache Software License, Version 2.0 | [Original](https://repo.maven.apache.org/maven2/com/fasterxml/jackson/module/jackson-module-parameter-names/2.21.4/jackson-module-parameter-names-2.21.4.pom) |
| `org.springframework.boot:spring-boot-starter-tomcat:3.5.16` | compile | Apache License, Version 2.0 | [Original](https://repo.maven.apache.org/maven2/org/springframework/boot/spring-boot-starter-tomcat/3.5.16/spring-boot-starter-tomcat-3.5.16.pom) |
| `org.apache.tomcat.embed:tomcat-embed-core:10.1.55` | compile | Apache License, Version 2.0 | [Original](https://repo.maven.apache.org/maven2/org/apache/tomcat/embed/tomcat-embed-core/10.1.55/tomcat-embed-core-10.1.55.pom) |
| `org.apache.tomcat.embed:tomcat-embed-websocket:10.1.55` | compile | Apache License, Version 2.0 | [Original](https://repo.maven.apache.org/maven2/org/apache/tomcat/embed/tomcat-embed-websocket/10.1.55/tomcat-embed-websocket-10.1.55.pom) |
| `org.springframework:spring-web:6.2.19` | compile | Apache License, Version 2.0 | [Original](https://repo.maven.apache.org/maven2/org/springframework/spring-web/6.2.19/spring-web-6.2.19.pom) |
| `org.springframework:spring-beans:6.2.19` | compile | Apache License, Version 2.0 | [Original](https://repo.maven.apache.org/maven2/org/springframework/spring-beans/6.2.19/spring-beans-6.2.19.pom) |
| `io.micrometer:micrometer-observation:1.15.12` | compile | The Apache Software License, Version 2.0 | [Original](https://repo.maven.apache.org/maven2/io/micrometer/micrometer-observation/1.15.12/micrometer-observation-1.15.12.pom) |
| `io.micrometer:micrometer-commons:1.15.12` | compile | The Apache Software License, Version 2.0 | [Original](https://repo.maven.apache.org/maven2/io/micrometer/micrometer-commons/1.15.12/micrometer-commons-1.15.12.pom) |
| `org.springframework:spring-webmvc:6.2.19` | compile | Apache License, Version 2.0 | [Original](https://repo.maven.apache.org/maven2/org/springframework/spring-webmvc/6.2.19/spring-webmvc-6.2.19.pom) |
| `org.springframework:spring-context:6.2.19` | compile | Apache License, Version 2.0 | [Original](https://repo.maven.apache.org/maven2/org/springframework/spring-context/6.2.19/spring-context-6.2.19.pom) |
| `org.springframework:spring-expression:6.2.19` | compile | Apache License, Version 2.0 | [Original](https://repo.maven.apache.org/maven2/org/springframework/spring-expression/6.2.19/spring-expression-6.2.19.pom) |
| `org.springframework.boot:spring-boot-starter-thymeleaf:3.5.16` | compile | Apache License, Version 2.0 | [Original](https://repo.maven.apache.org/maven2/org/springframework/boot/spring-boot-starter-thymeleaf/3.5.16/spring-boot-starter-thymeleaf-3.5.16.pom) |
| `org.thymeleaf:thymeleaf-spring6:3.1.5.RELEASE` | compile | The Apache Software License, Version 2.0 | [Original](https://repo.maven.apache.org/maven2/org/thymeleaf/thymeleaf-spring6/3.1.5.RELEASE/thymeleaf-spring6-3.1.5.RELEASE.pom) |
| `org.thymeleaf:thymeleaf:3.1.5.RELEASE` | compile | The Apache Software License, Version 2.0 | [Original](https://repo.maven.apache.org/maven2/org/thymeleaf/thymeleaf/3.1.5.RELEASE/thymeleaf-3.1.5.RELEASE.pom) |
| `org.attoparser:attoparser:2.0.7.RELEASE` | compile | The Apache Software License, Version 2.0 | [Original](https://repo.maven.apache.org/maven2/org/attoparser/attoparser/2.0.7.RELEASE/attoparser-2.0.7.RELEASE.pom) |
| `org.unbescape:unbescape:1.1.6.RELEASE` | compile | The Apache Software License, Version 2.0 | [Original](https://repo.maven.apache.org/maven2/org/unbescape/unbescape/1.1.6.RELEASE/unbescape-1.1.6.RELEASE.pom) |
| `org.slf4j:slf4j-api:2.0.18` | compile | MIT | [Original](https://repo.maven.apache.org/maven2/org/slf4j/slf4j-api/2.0.18/slf4j-api-2.0.18.pom) |
| `org.springframework.boot:spring-boot-starter-data-jpa:3.5.16` | compile | Apache License, Version 2.0 | [Original](https://repo.maven.apache.org/maven2/org/springframework/boot/spring-boot-starter-data-jpa/3.5.16/spring-boot-starter-data-jpa-3.5.16.pom) |
| `org.springframework.boot:spring-boot-starter-jdbc:3.5.16` | compile | Apache License, Version 2.0 | [Original](https://repo.maven.apache.org/maven2/org/springframework/boot/spring-boot-starter-jdbc/3.5.16/spring-boot-starter-jdbc-3.5.16.pom) |
| `com.zaxxer:HikariCP:6.3.3` | compile | The Apache Software License, Version 2.0 | [Original](https://repo.maven.apache.org/maven2/com/zaxxer/HikariCP/6.3.3/HikariCP-6.3.3.pom) |
| `org.springframework:spring-jdbc:6.2.19` | compile | Apache License, Version 2.0 | [Original](https://repo.maven.apache.org/maven2/org/springframework/spring-jdbc/6.2.19/spring-jdbc-6.2.19.pom) |
| `org.hibernate.orm:hibernate-core:6.6.53.Final` | compile | GNU Library General Public License v2.1 or later | [Original](https://repo.maven.apache.org/maven2/org/hibernate/orm/hibernate-core/6.6.53.Final/hibernate-core-6.6.53.Final.pom) |
| `jakarta.persistence:jakarta.persistence-api:3.1.0` | compile | Eclipse Public License v. 2.0; Eclipse Distribution License v. 1.0 | [Original](https://repo.maven.apache.org/maven2/jakarta/persistence/jakarta.persistence-api/3.1.0/jakarta.persistence-api-3.1.0.pom) |
| `jakarta.transaction:jakarta.transaction-api:2.0.1` | compile | EPL 2.0; GPL2 w/ CPE | [Original](https://repo.maven.apache.org/maven2/jakarta/transaction/jakarta.transaction-api/2.0.1/jakarta.transaction-api-2.0.1.pom) |
| `org.jboss.logging:jboss-logging:3.6.3.Final` | compile | Apache License 2.0 | [Original](https://repo.maven.apache.org/maven2/org/jboss/logging/jboss-logging/3.6.3.Final/jboss-logging-3.6.3.Final.pom) |
| `org.hibernate.common:hibernate-commons-annotations:7.0.3.Final` | runtime | Apache License Version 2.0 | [Original](https://repo.maven.apache.org/maven2/org/hibernate/common/hibernate-commons-annotations/7.0.3.Final/hibernate-commons-annotations-7.0.3.Final.pom) |
| `io.smallrye:jandex:3.2.0` | runtime | Apache License, Version 2.0 | [Original](https://repo.maven.apache.org/maven2/io/smallrye/jandex/3.2.0/jandex-3.2.0.pom) |
| `com.fasterxml:classmate:1.7.3` | compile | Apache License, Version 2.0 | [Original](https://repo.maven.apache.org/maven2/com/fasterxml/classmate/1.7.3/classmate-1.7.3.pom) |
| `net.bytebuddy:byte-buddy:1.17.8` | runtime | Apache License, Version 2.0 | [Original](https://repo.maven.apache.org/maven2/net/bytebuddy/byte-buddy/1.17.8/byte-buddy-1.17.8.pom) |
| `org.glassfish.jaxb:jaxb-runtime:4.0.9` | runtime | Eclipse Distribution License - v 1.0 | [Original](https://repo.maven.apache.org/maven2/org/glassfish/jaxb/jaxb-runtime/4.0.9/jaxb-runtime-4.0.9.pom) |
| `org.glassfish.jaxb:jaxb-core:4.0.9` | runtime | Eclipse Distribution License - v 1.0 | [Original](https://repo.maven.apache.org/maven2/org/glassfish/jaxb/jaxb-core/4.0.9/jaxb-core-4.0.9.pom) |
| `org.eclipse.angus:angus-activation:2.0.3` | runtime | EDL 1.0 | [Original](https://repo.maven.apache.org/maven2/org/eclipse/angus/angus-activation/2.0.3/angus-activation-2.0.3.pom) |
| `org.glassfish.jaxb:txw2:4.0.9` | runtime | Eclipse Distribution License - v 1.0 | [Original](https://repo.maven.apache.org/maven2/org/glassfish/jaxb/txw2/4.0.9/txw2-4.0.9.pom) |
| `com.sun.istack:istack-commons-runtime:4.1.2` | runtime | Eclipse Distribution License - v 1.0 | [Original](https://repo.maven.apache.org/maven2/com/sun/istack/istack-commons-runtime/4.1.2/istack-commons-runtime-4.1.2.pom) |
| `jakarta.inject:jakarta.inject-api:2.0.1` | runtime | The Apache Software License, Version 2.0 | [Original](https://repo.maven.apache.org/maven2/jakarta/inject/jakarta.inject-api/2.0.1/jakarta.inject-api-2.0.1.pom) |
| `org.antlr:antlr4-runtime:4.13.2` | compile | BSD-3-Clause | [Original](https://repo.maven.apache.org/maven2/org/antlr/antlr4-runtime/4.13.2/antlr4-runtime-4.13.2.pom) |
| `org.springframework.data:spring-data-jpa:3.5.13` | compile | Apache License, Version 2.0 | [Original](https://repo.maven.apache.org/maven2/org/springframework/data/spring-data-jpa/3.5.13/spring-data-jpa-3.5.13.pom) |
| `org.springframework.data:spring-data-commons:3.5.13` | compile | Apache License, Version 2.0 | [Original](https://repo.maven.apache.org/maven2/org/springframework/data/spring-data-commons/3.5.13/spring-data-commons-3.5.13.pom) |
| `org.springframework:spring-orm:6.2.19` | compile | Apache License, Version 2.0 | [Original](https://repo.maven.apache.org/maven2/org/springframework/spring-orm/6.2.19/spring-orm-6.2.19.pom) |
| `org.springframework:spring-tx:6.2.19` | compile | Apache License, Version 2.0 | [Original](https://repo.maven.apache.org/maven2/org/springframework/spring-tx/6.2.19/spring-tx-6.2.19.pom) |
| `org.springframework:spring-aspects:6.2.19` | compile | Apache License, Version 2.0 | [Original](https://repo.maven.apache.org/maven2/org/springframework/spring-aspects/6.2.19/spring-aspects-6.2.19.pom) |
| `org.aspectj:aspectjweaver:1.9.25.1` | compile | Eclipse Public License - v 2.0 | [Original](https://repo.maven.apache.org/maven2/org/aspectj/aspectjweaver/1.9.25.1/aspectjweaver-1.9.25.1.pom) |
| `org.springframework.boot:spring-boot-starter-validation:3.5.16` | compile | Apache License, Version 2.0 | [Original](https://repo.maven.apache.org/maven2/org/springframework/boot/spring-boot-starter-validation/3.5.16/spring-boot-starter-validation-3.5.16.pom) |
| `org.apache.tomcat.embed:tomcat-embed-el:10.1.55` | compile | Apache License, Version 2.0 | [Original](https://repo.maven.apache.org/maven2/org/apache/tomcat/embed/tomcat-embed-el/10.1.55/tomcat-embed-el-10.1.55.pom) |
| `org.hibernate.validator:hibernate-validator:8.0.3.Final` | compile | Apache License 2.0 | [Original](https://repo.maven.apache.org/maven2/org/hibernate/validator/hibernate-validator/8.0.3.Final/hibernate-validator-8.0.3.Final.pom) |
| `jakarta.validation:jakarta.validation-api:3.0.2` | compile | Apache License 2.0 | [Original](https://repo.maven.apache.org/maven2/jakarta/validation/jakarta.validation-api/3.0.2/jakarta.validation-api-3.0.2.pom) |
| `org.springframework.boot:spring-boot-starter-security:3.5.16` | compile | Apache License, Version 2.0 | [Original](https://repo.maven.apache.org/maven2/org/springframework/boot/spring-boot-starter-security/3.5.16/spring-boot-starter-security-3.5.16.pom) |
| `org.springframework:spring-aop:6.2.19` | compile | Apache License, Version 2.0 | [Original](https://repo.maven.apache.org/maven2/org/springframework/spring-aop/6.2.19/spring-aop-6.2.19.pom) |
| `org.springframework.security:spring-security-config:6.5.11` | compile | Apache License, Version 2.0 | [Original](https://repo.maven.apache.org/maven2/org/springframework/security/spring-security-config/6.5.11/spring-security-config-6.5.11.pom) |
| `org.springframework.security:spring-security-web:6.5.11` | compile | Apache License, Version 2.0 | [Original](https://repo.maven.apache.org/maven2/org/springframework/security/spring-security-web/6.5.11/spring-security-web-6.5.11.pom) |
| `com.h2database:h2:2.3.232` | runtime | MPL 2.0 OR EPL 1.0 | [Original](https://h2database.com/html/license.html) |
| `org.springframework.boot:spring-boot-starter-test:3.5.16` | test | Apache License, Version 2.0 | [Original](https://repo.maven.apache.org/maven2/org/springframework/boot/spring-boot-starter-test/3.5.16/spring-boot-starter-test-3.5.16.pom) |
| `org.springframework.boot:spring-boot-test:3.5.16` | test | Apache License, Version 2.0 | [Original](https://repo.maven.apache.org/maven2/org/springframework/boot/spring-boot-test/3.5.16/spring-boot-test-3.5.16.pom) |
| `org.springframework.boot:spring-boot-test-autoconfigure:3.5.16` | test | Apache License, Version 2.0 | [Original](https://repo.maven.apache.org/maven2/org/springframework/boot/spring-boot-test-autoconfigure/3.5.16/spring-boot-test-autoconfigure-3.5.16.pom) |
| `com.jayway.jsonpath:json-path:2.9.0` | test | The Apache Software License, Version 2.0 | [Original](https://repo.maven.apache.org/maven2/com/jayway/jsonpath/json-path/2.9.0/json-path-2.9.0.pom) |
| `jakarta.xml.bind:jakarta.xml.bind-api:4.0.5` | runtime | Eclipse Distribution License - v 1.0 | [Original](https://repo.maven.apache.org/maven2/jakarta/xml/bind/jakarta.xml.bind-api/4.0.5/jakarta.xml.bind-api-4.0.5.pom) |
| `jakarta.activation:jakarta.activation-api:2.1.4` | runtime | EDL 1.0 | [Original](https://repo.maven.apache.org/maven2/jakarta/activation/jakarta.activation-api/2.1.4/jakarta.activation-api-2.1.4.pom) |
| `net.minidev:json-smart:2.5.2` | test | The Apache Software License, Version 2.0 | [Original](https://repo.maven.apache.org/maven2/net/minidev/json-smart/2.5.2/json-smart-2.5.2.pom) |
| `net.minidev:accessors-smart:2.5.2` | test | The Apache Software License, Version 2.0 | [Original](https://repo.maven.apache.org/maven2/net/minidev/accessors-smart/2.5.2/accessors-smart-2.5.2.pom) |
| `org.ow2.asm:asm:9.7.1` | test | BSD-3-Clause | [Original](https://repo.maven.apache.org/maven2/org/ow2/asm/asm/9.7.1/asm-9.7.1.pom) |
| `org.assertj:assertj-core:3.27.7` | test | Apache-2.0 | [Original](https://repo.maven.apache.org/maven2/org/assertj/assertj-core/3.27.7/assertj-core-3.27.7.pom) |
| `org.awaitility:awaitility:4.2.2` | test | Apache 2.0 | [Original](https://repo.maven.apache.org/maven2/org/awaitility/awaitility/4.2.2/awaitility-4.2.2.pom) |
| `org.hamcrest:hamcrest:3.0` | test | BSD-3-Clause | [Original](https://repo.maven.apache.org/maven2/org/hamcrest/hamcrest/3.0/hamcrest-3.0.pom) |
| `org.junit.jupiter:junit-jupiter:5.12.2` | test | Eclipse Public License v2.0 | [Original](https://repo.maven.apache.org/maven2/org/junit/jupiter/junit-jupiter/5.12.2/junit-jupiter-5.12.2.pom) |
| `org.junit.jupiter:junit-jupiter-api:5.12.2` | test | Eclipse Public License v2.0 | [Original](https://repo.maven.apache.org/maven2/org/junit/jupiter/junit-jupiter-api/5.12.2/junit-jupiter-api-5.12.2.pom) |
| `org.opentest4j:opentest4j:1.3.0` | test | The Apache License, Version 2.0 | [Original](https://repo.maven.apache.org/maven2/org/opentest4j/opentest4j/1.3.0/opentest4j-1.3.0.pom) |
| `org.junit.platform:junit-platform-commons:1.12.2` | test | Eclipse Public License v2.0 | [Original](https://repo.maven.apache.org/maven2/org/junit/platform/junit-platform-commons/1.12.2/junit-platform-commons-1.12.2.pom) |
| `org.apiguardian:apiguardian-api:1.1.2` | test | The Apache License, Version 2.0 | [Original](https://repo.maven.apache.org/maven2/org/apiguardian/apiguardian-api/1.1.2/apiguardian-api-1.1.2.pom) |
| `org.junit.jupiter:junit-jupiter-params:5.12.2` | test | Eclipse Public License v2.0 | [Original](https://repo.maven.apache.org/maven2/org/junit/jupiter/junit-jupiter-params/5.12.2/junit-jupiter-params-5.12.2.pom) |
| `org.junit.jupiter:junit-jupiter-engine:5.12.2` | test | Eclipse Public License v2.0 | [Original](https://repo.maven.apache.org/maven2/org/junit/jupiter/junit-jupiter-engine/5.12.2/junit-jupiter-engine-5.12.2.pom) |
| `org.junit.platform:junit-platform-engine:1.12.2` | test | Eclipse Public License v2.0 | [Original](https://repo.maven.apache.org/maven2/org/junit/platform/junit-platform-engine/1.12.2/junit-platform-engine-1.12.2.pom) |
| `org.skyscreamer:jsonassert:1.5.3` | test | The Apache Software License, Version 2.0 | [Original](https://repo.maven.apache.org/maven2/org/skyscreamer/jsonassert/1.5.3/jsonassert-1.5.3.pom) |
| `com.vaadin.external.google:android-json:0.0.20131108.vaadin1` | test | Apache License 2.0 | [Original](https://repo.maven.apache.org/maven2/com/vaadin/external/google/android-json/0.0.20131108.vaadin1/android-json-0.0.20131108.vaadin1.pom) |
| `org.springframework:spring-core:6.2.19` | compile | Apache License, Version 2.0 | [Original](https://repo.maven.apache.org/maven2/org/springframework/spring-core/6.2.19/spring-core-6.2.19.pom) |
| `org.springframework:spring-jcl:6.2.19` | compile | Apache License, Version 2.0 | [Original](https://repo.maven.apache.org/maven2/org/springframework/spring-jcl/6.2.19/spring-jcl-6.2.19.pom) |
| `org.springframework:spring-test:6.2.19` | test | Apache License, Version 2.0 | [Original](https://repo.maven.apache.org/maven2/org/springframework/spring-test/6.2.19/spring-test-6.2.19.pom) |
| `org.xmlunit:xmlunit-core:2.10.4` | test | The Apache Software License, Version 2.0 | [Original](https://repo.maven.apache.org/maven2/org/xmlunit/xmlunit-core/2.10.4/xmlunit-core-2.10.4.pom) |
| `org.springframework.security:spring-security-test:6.5.11` | test | Apache License, Version 2.0 | [Original](https://repo.maven.apache.org/maven2/org/springframework/security/spring-security-test/6.5.11/spring-security-test-6.5.11.pom) |
| `org.springframework.security:spring-security-core:6.5.11` | compile | Apache License, Version 2.0 | [Original](https://repo.maven.apache.org/maven2/org/springframework/security/spring-security-core/6.5.11/spring-security-core-6.5.11.pom) |
| `org.springframework.security:spring-security-crypto:6.5.11` | compile | Apache License, Version 2.0 | [Original](https://repo.maven.apache.org/maven2/org/springframework/security/spring-security-crypto/6.5.11/spring-security-crypto-6.5.11.pom) |
