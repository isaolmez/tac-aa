package edu.umich.eecs.tac.sim;

/**
 * @author Lee Callender, Patrick Jordan
 */

import edu.umich.eecs.tac.props.Auction;
import edu.umich.eecs.tac.props.Query;

import java.util.logging.Logger;

public abstract class Publisher extends Builtin {
    private static final String CONF = "publisher.";

    public Publisher() {
        super(CONF);
    }

    public abstract void sendQueryReportsToAll();

    public abstract Auction runAuction(Query query);

    // DEBUG FINALIZE REMOVE THIS!!! REMOVE THIS!!!
    protected void finalize() throws Throwable {
        Logger.global.info("CUSTOMER " + getName() + " IS BEING GARBAGED");
        super.finalize();
    }


}
