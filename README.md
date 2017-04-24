[![Project Status: Active](http://www.repostatus.org/badges/latest/active.svg)](http://www.repostatus.org/#active)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.zsoltfabok/sqlite-dialect/badge.png)](https://maven-badges.herokuapp.com/maven-central/com.zsoltfabok/sqlite-dialect/)
[![Build Status](https://travis-ci.org/ZsoltFabok/sqlite-dialect.svg?branch=master)](https://travis-ci.org/ZsoltFabok/sqlite-dialect)
[![codebeat badge](https://codebeat.co/badges/5d552b87-552c-4d5f-877b-444a4f3c8dc7)](https://codebeat.co/projects/github-com-zsoltfabok-sqlite-dialect-master)
[![codecov](https://codecov.io/gh/ZsoltFabok/sqlite-dialect/branch/master/graph/badge.svg)](https://codecov.io/gh/ZsoltFabok/sqlite-dialect)
[![License](https://img.shields.io/badge/License-BSD%203--Clause-blue.svg)](https://github.com/ZsoltFabok/sqlite-dialect/blob/master/LICENSE.md)

### Test
    % ./gradlew test
 
### Release

First, create `gradle.properties` with the following entries:

    # signing
    signing.keyId=
    signing.password=
    # under windows use c:\\ and \\ as path separator
    signing.secretKeyRingFile=.../secring.gpg

    # sonatype auth
    sonatypeUsername=
    sonatypePassword=
    
You can check your gpg keys with:

    % gpg2 --list-keys

If you have them in a storage or backup, import them first
    
    % gpg2 --import .../gnupg/securing.gpg
    
Second, upload the archives:

    % ./gradlew uploadArchives
  
It generates the pom, the jar, the sources and javaDoc jars. After the generation, it signs them and finally uploads to sonatype staging repository. In order to keep this happening, you must make the version a *SNAPSHOT* by adding the `-SNAPSHOT` suffix to the version.

Third, go to `http://oss.sonatype.org` and release the snapshot manually.