[![Build Status](https://travis-ci.org/ZsoltFabok/sqlite-dialect.svg?branch=master)](https://travis-ci.org/ZsoltFabok/sqlite-dialect)

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