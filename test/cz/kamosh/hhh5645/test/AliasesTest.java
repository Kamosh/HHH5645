package cz.kamosh.hhh5645.test;

import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.tutorial.util.HibernateUtil;
import org.junit.Test;

import cz.kamosh.hhh5645.entity.TableA;

public class AliasesTest extends TestCase {

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

	// If alias for table C precedes alias of table B, order
        // of parameters in org.hibernate.engine.QueryParameters.processedPositionalParameterValues
        // differs from "?" parameters generated for Prepared statement		
        if (cAliasPrecedesB) {
            // Table C Alias
            Criterion tableCRestriction = Restrictions.eq(TABLE_C_ALIAS + ".tableCBoolean", false);
            criteria.createAlias(TABLE_B_ALIAS + ".tableCs", TABLE_C_ALIAS, CriteriaSpecification.LEFT_JOIN, tableCRestriction);

            // Table B Alias		
            Criterion tableBRestriction = Restrictions.eq(TABLE_B_ALIAS + ".tableBDate", new Date());
            criteria.createAlias("tableBs", TABLE_B_ALIAS, CriteriaSpecification.LEFT_JOIN, tableBRestriction);
        } else {
            // Table B Alias		
            Criterion tableBRestriction = Restrictions.eq(TABLE_B_ALIAS + ".tableBDate", new Date());
            criteria.createAlias("tableBs", TABLE_B_ALIAS, CriteriaSpecification.LEFT_JOIN, tableBRestriction);

            // Table C Alias
            Criterion tableCRestriction = Restrictions.eq(TABLE_C_ALIAS + ".tableCBoolean", false);
            criteria.createAlias(TABLE_B_ALIAS + ".tableCs", TABLE_C_ALIAS, CriteriaSpecification.LEFT_JOIN, tableCRestriction);
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
