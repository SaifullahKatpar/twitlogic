package net.fortytwo.twitlogic.syntax.afterthought;

import net.fortytwo.twitlogic.syntax.afterthought.impl.foaf.FoafDepictionMatcher;
import net.fortytwo.twitlogic.syntax.afterthought.impl.foaf.FoafInterestMatcher;
import net.fortytwo.twitlogic.syntax.afterthought.impl.foaf.FoafKnowsMatcher;
import net.fortytwo.twitlogic.syntax.afterthought.impl.foaf.FoafMadeMatcher;
import net.fortytwo.twitlogic.syntax.afterthought.impl.foaf.FoafMakerMatcher;
import net.fortytwo.twitlogic.syntax.afterthought.impl.RdfsSeeAlsoMatcher;
import net.fortytwo.twitlogic.syntax.afterthought.impl.ReviewMatcher;
import net.fortytwo.twitlogic.syntax.afterthought.impl.foaf.SelfInterestMatcher;
import net.fortytwo.twitlogic.syntax.afterthought.impl.TypeMatcher;
import net.fortytwo.twitlogic.syntax.afterthought.impl.contact.AddressMatcher;
import net.fortytwo.twitlogic.syntax.afterthought.impl.contact.BirthdayMatcher;
import net.fortytwo.twitlogic.syntax.afterthought.impl.contact.EmailAddressMatcher;
import net.fortytwo.twitlogic.syntax.afterthought.impl.contact.FaxMatcher;
import net.fortytwo.twitlogic.syntax.afterthought.impl.contact.PhoneMatcher;
import net.fortytwo.twitlogic.syntax.afterthought.impl.openvocab.OVCategoryMatcher;
import net.fortytwo.twitlogic.syntax.afterthought.impl.openvocab.OVDepictsMatcher;
import net.fortytwo.twitlogic.syntax.afterthought.impl.openvocab.OVSimilarToMatcher;
import net.fortytwo.twitlogic.syntax.afterthought.impl.openvocab.OVStudiesMatcher;
import net.fortytwo.twitlogic.syntax.afterthought.impl.openvocab.OVUsesMatcher;

import java.util.Arrays;

/**
 * User: josh
 * Date: Sep 29, 2009
 * Time: 10:59:40 PM
 */
public class DemoAfterthoughtMatcher extends CompoundAfterthoughtMatcher {
    public DemoAfterthoughtMatcher() {
        super(Arrays.asList(
                new FoafDepictionMatcher(),
                new FoafInterestMatcher(),
                new FoafKnowsMatcher(),
                new FoafMadeMatcher(),
                new FoafMakerMatcher(),
                new RdfsSeeAlsoMatcher(),
                new ReviewMatcher(),
                new SelfInterestMatcher(),
                new TypeMatcher(),
                // OpenVocab
                new OVCategoryMatcher(),
                new OVDepictsMatcher(),
                new OVSimilarToMatcher(),
                new OVStudiesMatcher(),
                new OVUsesMatcher(),
                // Contact
                new AddressMatcher(),
                new BirthdayMatcher(),
                new EmailAddressMatcher(),
                new FaxMatcher(),
                new PhoneMatcher()));
    }
}
