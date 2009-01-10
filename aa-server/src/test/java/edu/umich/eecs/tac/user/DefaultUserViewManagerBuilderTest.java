package edu.umich.eecs.tac.user;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import edu.umich.eecs.tac.util.config.ConfigProxy;
import edu.umich.eecs.tac.sim.AgentRepository;
import edu.umich.eecs.tac.sim.DefaultSalesAnalyst;
import edu.umich.eecs.tac.sim.SalesAnalyst;
import edu.umich.eecs.tac.props.RetailCatalog;
import edu.umich.eecs.tac.props.AdvertiserInfo;
import edu.umich.eecs.tac.props.Query;
import edu.umich.eecs.tac.props.Ad;

import java.util.Random;
import java.util.Map;
import java.util.HashMap;

import se.sics.tasim.sim.SimulationAgent;


/**
 * @author Patrick Jordan
 */
public class DefaultUserViewManagerBuilderTest {
    private DefaultUserViewManagerBuilder builder;
    private ConfigProxy userConfigProxy;
    private AgentRepository repository;
    private Random random;
    private RetailCatalog catalog;
    private SalesAnalyst salesAnalyst;
    private Map<String, AdvertiserInfo> advertiserInfo;

    @Before
    public void setup() {
        catalog = new RetailCatalog();
        advertiserInfo = new HashMap<String, AdvertiserInfo>();
        
        builder = new DefaultUserViewManagerBuilder();

        salesAnalyst = new SalesAnalyst() {

            public void addAccount(String name) {                
            }

            public void sendSalesReportToAll() {
            }

            public void queryIssued(Query query) {
            }

            public void viewed(Query query, Ad ad, int slot, String advertiser) {
            }

            public void clicked(Query query, Ad ad, int slot, double cpc, String advertiser) {
            }

            public void converted(Query query, Ad ad, int slot, double salesProfit, String advertiser) {
            }

            public double getRecentConversions(String name) {
                return 0;
            }
        };

        userConfigProxy = new ConfigProxy() {

            public String getProperty(String name) {
                return null;
            }

            public String getProperty(String name, String defaultValue) {
                return null;
            }

            public String[] getPropertyAsArray(String name) {
                return new String[0];
            }

            public String[] getPropertyAsArray(String name, String defaultValue) {
                return new String[0];
            }

            public int getPropertyAsInt(String name, int defaultValue) {
                return 0;
            }

            public int[] getPropertyAsIntArray(String name) {
                return new int[0];
            }

            public int[] getPropertyAsIntArray(String name, String defaultValue) {
                return new int[0];
            }

            public long getPropertyAsLong(String name, long defaultValue) {
                return 0;
            }

            public float getPropertyAsFloat(String name, float defaultValue) {
                return 0;
            }

            public double getPropertyAsDouble(String name, double defaultValue) {
                return 0;
            }
        };

        repository = new AgentRepository() {

            public RetailCatalog getRetailCatalog() {
                return catalog;
            }

            public Map<String, AdvertiserInfo> getAdvertiserInfo() {
                return advertiserInfo;
            }

            public SimulationAgent[] getPublishers() {
                return new SimulationAgent[0];
            }

            public SimulationAgent[] getUsers() {
                return new SimulationAgent[0];
            }

            public SalesAnalyst getSalesAnalyst() {
                return salesAnalyst;
            }
        };

        random = new Random();
    }

    @Test
    public void testConstructor() {
        assertNotNull(builder);    
    }

    @Test
    public void testBuild() {
        UserViewManager manager = builder.build(userConfigProxy, repository, random);
        assertNotNull(manager);
    }


    @Test(expected = NullPointerException.class)
    public void testRepositoryNullBuild() {
        UserViewManager manager = builder.build(userConfigProxy, null, random);
        assertNotNull(manager);
    }

    @Test(expected = NullPointerException.class)
    public void testRandomNullBuild() {
        UserViewManager manager = builder.build(userConfigProxy, repository, null);
        assertNotNull(manager);
    }
}