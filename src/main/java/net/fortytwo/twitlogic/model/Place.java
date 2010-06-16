package net.fortytwo.twitlogic.model;

import net.fortytwo.twitlogic.services.twitter.TwitterAPI;
import net.fortytwo.twitlogic.TwitLogic;
import org.json.JSONObject;
import org.json.JSONException;

import java.util.logging.Logger;

/**
 * User: josh
 * Date: Jun 15, 2010
 * Time: 3:32:11 PM
 */
public class Place {
    private static final Logger LOGGER = TwitLogic.getLogger(Place.class);

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public enum PlaceType {
        CITY("city");

        private final String name;

        private PlaceType(final String name) {
            this.name = name;
        }

        public static PlaceType lookup(final String name) {
            for (PlaceType pt : PlaceType.values()) {
                if (pt.name.equals(name)) {
                    return pt;
                }
            }

            return null;
        }
    }

    private String countryCode;
    private String fullName;
    private String name;
    private String url;
    private String id;
    private PlaceType placeType;

    public Place(final JSONObject json) throws TweetParseException {
        // id is required, as it determines the URI of the place
        try {
            id = json.getString(TwitterAPI.PlaceField.ID.toString());
        } catch (JSONException e) {
            throw new TweetParseException(e);
        }

        countryCode = json.optString(TwitterAPI.PlaceField.COUNTRY_CODE.toString());
        fullName = json.optString(TwitterAPI.PlaceField.FULL_NAME.toString());
        name = json.optString(TwitterAPI.PlaceField.NAME.toString());
        url = json.optString(TwitterAPI.PlaceField.URL.toString());
        String t = json.optString(TwitterAPI.PlaceField.PLACE_TYPE.toString());
        if (null != t) {
            placeType = PlaceType.lookup(t);
            if (null == placeType) {
                LOGGER.warning("unfamiliar place type: '" + t + "'");
            }
        }
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public PlaceType getPlaceType() {
        return placeType;
    }

    public void setPlaceType(PlaceType placeType) {
        this.placeType = placeType;
    }
}