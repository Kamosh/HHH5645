HHH5645
=======

This is test case for hibernate bug HHH5645 https://hibernate.atlassian.net/browse/HHH-5645
Project created in NetBeans 8.0.
Used library: hibernate-release-4.3.5.Final
Please setup correct path to all used libraries.
Test uses hsqldb database version hsqldb-2.3.0, http://www.hsqldb.org

Howto start the database and fill initial data:
> cd path_to_project/myTestData
> java -cp path_to_hsqldb/lib/hsqldb.jar org.hsqldb.server.Server --database.0 file:myTestData --dbname.0 mytestdata
