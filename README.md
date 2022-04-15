# Infonode for Java 8
Un-official version of the InfoNode docking windows framework targetting Java 8 and newer versions. It also contains minor fixes and 
changes coming from other forks and released under the GPL v2 license.

## Differences with the official version
- Support for applets has been removed as applets were deprecated by Oracle.
- Calls to Java's SecurityManager have been removed. SecurityManager has been deprecated by Oracle and will be replaced in the future.
- Added ability to create custom top level container for FloatingWindows (code by Марковский Георгий Борисович - https://github.com/REC-SPb-ETU/idw-gpl).

## Compilation
This project uses Maven. Use `mvn package` from the root directory to generate binaries, source, and javadoc jar files. This version
of Infonode requires Java 8 or better to compile.

