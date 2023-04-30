package de.uni_marburg.sp21.Model;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import de.uni_marburg.sp21.Service.ImportDatabaseTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({CompanyFilterTest.class, MessageTest.class, ImportDatabaseTest.class, OrganisationTest.class,
        AddressTest.class, CompanyTest.class, LocationTest.class, MessageTest.class, TimeIntervallTest.class, ProductGroupTest.class})
public class TestAll {


}
