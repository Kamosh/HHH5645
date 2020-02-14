package cz.kamosh.hhh5645.test;

import java.util.Date;
import java.util.List;


import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.tutorial.util.HibernateUtil;
import org.junit.Test;

import cz.kamosh.hhh5645.entity.TableA;
import java.sql.Connection;
import java.sql.DriverManager;
import org.hibernate.sql.JoinType;
import org.hsqldb.Server;
import org.junit.AfterClass;
import org.junit.BeforeClass;

public class AliasesTest {

    private static Server hsqlServer;

    @BeforeClass
    public static void runDB() throws Exception {

        hsqlServer = new Server();

        hsqlServer.setDatabaseName(0, "mainDb");
        hsqlServer.setDatabasePath(0, "mem:mainDb");
        hsqlServer.setDatabaseName(1, "standbyDb");
        hsqlServer.setDatabasePath(1, "mem:standbyDb");

        // Start the database!
        hsqlServer.start();

        Connection connection = null;
        // We have here two 'try' blocks and two 'finally'
        // blocks because we have two things to close
        // after all - HSQLDB server and connection
        try {
            // Getting a connection to the newly started database
            Class.forName("org.hsqldb.jdbcDriver");
            // Default user of the HSQLDB is 'sa'
            // with an empty password
            connection = DriverManager.getConnection(
                    "jdbc:hsqldb:hsql://localhost/mainDb", "sa", "");

            connection.prepareStatement(
                    "create table TABLE_A(TABLE_A_ID BIGINT NOT NULL,TABLE_A_CHARACTER CHARACTER(10),CONSTRAINT TABLE_A_PK PRIMARY KEY(TABLE_A_ID));")
                    .execute();
            connection.prepareStatement("create table TABLE_B(TABLE_B_ID BIGINT NOT NULL,TABLE_A_ID BIGINT,TABLE_B_CHARACTER CHARACTER(10),TABLE_B_INTEGER INTEGER,TABLE_B_DATE DATE,TABLE_B_BOOLEAN BOOLEAN,CONSTRAINT \"TABLE_B_PK\" PRIMARY KEY(TABLE_B_ID),FOREIGN KEY(TABLE_A_ID) REFERENCES PUBLIC.TABLE_A(TABLE_A_ID));")
                    .execute();
            connection.prepareStatement("create table TABLE_C(TABLE_C_ID BIGINT NOT NULL,TABLE_C_CHAR CHARACTER(10),TABLE_C_DATE DATE,TABLE_B_ID BIGINT,TABLE_C_BOOLEAN BOOLEAN,TABLE_C_INTEGER INTEGER,CONSTRAINT TABLE_C_PK PRIMARY KEY(TABLE_C_ID),FOREIGN KEY(TABLE_B_ID) REFERENCES PUBLIC.TABLE_B(TABLE_B_ID));")
                    .execute();

            connection.prepareStatement("INSERT INTO TABLE_A(TABLE_A_ID, TABLE_A_CHARACTER) VALUES(1,'a         ');").execute();
            connection.prepareStatement("INSERT INTO TABLE_A(TABLE_A_ID, TABLE_A_CHARACTER) VALUES(2,'b         ');").execute();
            connection.prepareStatement("INSERT INTO TABLE_A(TABLE_A_ID, TABLE_A_CHARACTER) VALUES(3,'c         ');").execute();
            connection.prepareStatement("INSERT INTO TABLE_B(TABLE_B_ID,TABLE_A_ID,TABLE_B_CHARACTER, TABLE_B_INTEGER, TABLE_B_DATE, TABLE_B_BOOLEAN) VALUES(1,1,NULL,NULL,'2010-04-05',NULL);").execute();
            connection.prepareStatement("INSERT INTO TABLE_B(TABLE_B_ID,TABLE_A_ID,TABLE_B_CHARACTER, TABLE_B_INTEGER, TABLE_B_DATE, TABLE_B_BOOLEAN) VALUES(2,1,NULL,NULL,'2010-03-04',NULL);").execute();
            connection.prepareStatement("INSERT INTO TABLE_B(TABLE_B_ID,TABLE_A_ID,TABLE_B_CHARACTER, TABLE_B_INTEGER, TABLE_B_DATE, TABLE_B_BOOLEAN) VALUES(3,1,NULL,NULL,'2010-04-06',NULL);").execute();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Closing the connection
            if (connection != null) {
                connection.close();
            }
        }
    }

    @AfterClass
    public static void stopDB() {
        // Closing the server
        if (hsqlServer != null) {
            hsqlServer.stop();
        }
    }

    @Test
    public void testBAliasPrecedesC() {
        testAliases(false);
    }

    @Test
    public void testCAliasPrecedesB() {
        testAliases(true);
    }

    private void testAliases(boolean cAliasPrecedesB) {
        // Create hibernate session
        Session currentSession = HibernateUtil.getSessionFactory().openSession();

        Criteria criteria = currentSession.createCriteria(TableA.class);

        final String TABLE_B_ALIAS = "tableBAlias";
        final String TABLE_C_ALIAS = "tableCAlias";

        // TableA (1) - (Many) TableB 
        // TableB (1) - (Many) TableC
        // If alias for table C precedes alias of table B, the order
        // of parameters in org.hibernate.engine.QueryParameters.processedPositionalParameterValues
        // differs from "?" parameters generated for Prepared statement		
        if (cAliasPrecedesB) {
            // Table C Alias
            Criterion tableCRestriction = Restrictions.eq(TABLE_C_ALIAS + ".tableCBoolean", false);
            criteria.createAlias(TABLE_B_ALIAS + ".tableCs", TABLE_C_ALIAS, JoinType.INNER_JOIN, tableCRestriction);

            // Table B Alias		
            Criterion tableBRestriction = Restrictions.eq(TABLE_B_ALIAS + ".tableBDate", new Date());
            criteria.createAlias("tableBs", TABLE_B_ALIAS, JoinType.INNER_JOIN, tableBRestriction);
        } else {
            // Table B Alias		
            Criterion tableBRestriction = Restrictions.eq(TABLE_B_ALIAS + ".tableBDate", new Date());
            criteria.createAlias("tableBs", TABLE_B_ALIAS, JoinType.INNER_JOIN, tableBRestriction);

            // Table C Alias
            Criterion tableCRestriction = Restrictions.eq(TABLE_C_ALIAS + ".tableCBoolean", false);
            criteria.createAlias(TABLE_B_ALIAS + ".tableCs", TABLE_C_ALIAS, JoinType.INNER_JOIN, tableCRestriction);
        }

        criteria.add(Restrictions.eq("tableACharacter", "c"));

        ProjectionList projectionList = Projections.projectionList();
        projectionList.add(Projections.property("tableACharacter"));
        criteria.setProjection(projectionList);

        List list = criteria.list();
        System.out.println("Result:");
        for (Object o : list) {
            System.out.println(o);
        }
    }
}
