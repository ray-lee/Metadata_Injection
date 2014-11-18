Metadata_Injection
==================

A Java program to embed BAM/PFA cataloging data into image files, written by Andrew Chang of BAM/PFA. This program originally extracted cataloging data from the museum's FileMaker database. It has been adapted to use data extracted from CollectionSpace, as part of the migration of the collection management system from FileMaker to CollectionSpace. It has also been converted to use maven for build management (instead of using unscripted Eclipse builds), and the onejar maven plugin for dependency bundling (instead of Eclipse's jarinjarloader).

To build:

	mvn clean install
	
This produces two jar files:

	InjectMetaDataBAMCollection-[version].jar
	InjectMetaDataCDArchive-[version].jar
	
The jar files are identical, except for having different main classes. All dependencies are bundled in the jars.

Usage:

	java -jar target/InjectMetaDataCDArchive-[version].jar [path to image archive directory]
	java -jar target/InjectMetaDataBAMCollection-[version].jar [path to image directory]