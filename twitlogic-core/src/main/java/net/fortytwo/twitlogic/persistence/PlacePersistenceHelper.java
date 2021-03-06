package net.fortytwo.twitlogic.persistence;

import net.fortytwo.twitlogic.TwitLogic;
import net.fortytwo.twitlogic.flow.ConcurrentBuffer;
import net.fortytwo.twitlogic.flow.Handler;
import net.fortytwo.twitlogic.model.Place;
import net.fortytwo.twitlogic.model.PlaceType;
import net.fortytwo.twitlogic.persistence.beans.Feature;
import net.fortytwo.twitlogic.services.twitter.HandlerException;
import net.fortytwo.twitlogic.services.twitter.PlaceMappingQueue;
import net.fortytwo.twitlogic.services.twitter.TwitterClient;
import net.fortytwo.twitlogic.services.twitter.TwitterClientException;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

/**
 * @author Joshua Shinavier (http://fortytwo.net).
 */
public class PlacePersistenceHelper {
    private static final Logger LOGGER = TwitLogic.getLogger(PlacePersistenceHelper.class);

    private final Handler<Place> placeMappingHandler;
    private final PlaceMappingQueue placeMappingQueue;
    private final ConcurrentBuffer<Place> buffer;
    private final TwitterClient client;
    private final boolean asynchronous;

    public PlacePersistenceHelper(final PersistenceContext pContext,
                                  final TwitterClient client) throws TweetStoreException, TwitterClientException {
        this(pContext, client, true);
    }

    public PlacePersistenceHelper(final PersistenceContext pContext,
                                  final TwitterClient client,
                                  final boolean asynchronous) throws TweetStoreException, TwitterClientException {
        this.client = client;
        this.asynchronous = asynchronous;

        placeMappingHandler = new Handler<Place>() {
            public boolean isOpen() {
                return true;
            }

            public void handle(final Place p) throws HandlerException {
                //System.out.println("received this place: " + p.getJson());
                client.getStatistics().placeDereferenced(p);

                if (0 < p.getContainedWithin().size()) {
                    Feature f = pContext.persist(p);
                    // Refreshing the parent features is all-or-nothing.
                    Set<Feature> parents = new HashSet<Feature>();

                    for (Place par : p.getContainedWithin()) {
                        //System.out.println("and this is a parent: " + par.getJson());

                        Feature parf = pContext.persist(par);
                        parents.add(parf);
                        try {
                            submit(par, parf);
                        } catch (TwitterClientException e) {
                            throw new HandlerException(e);
                        }
                    }

                    f.setParentFeature(parents);
                }
            }
        };

        buffer = new ConcurrentBuffer<Place>(placeMappingHandler);
        placeMappingQueue = asynchronous
                ? new PlaceMappingQueue(client, buffer)
                : null;
    }

    public boolean submit(final Place p,
                          final Feature f) throws TwitterClientException, HandlerException {
        // Note: for now, links in the hierarchy are established once, and never updated.
        if (0 == f.getParentFeature().size() && PlaceType.COUNTRY != p.getPlaceType()) {
            //if (0 == f.getOwlSameAs().size()
            //        && 0 == f.getParentFeature().size()) {
            LOGGER.info("queueing unknown " + p.getPlaceType() + ": " + p);
            client.getStatistics().placeQueued(p);

            if (asynchronous) {
                return placeMappingQueue.offer(p.getId());
            } else {
                Place p2 = client.fetchPlace(p.getId());
                boolean b = placeMappingHandler.isOpen();
                if (b) {
                    placeMappingHandler.handle(p2);
                }

                return b;
            }
        } else {
            LOGGER.fine("familiar " + p.getPlaceType() + ": " + p);
            return true;
        }
    }

    public boolean flush() throws HandlerException {
        return buffer.flush();
    }
}
