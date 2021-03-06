package hudson.plugins.sauce_ondemand;

import com.saucelabs.ci.Browser;
import hudson.Extension;
import hudson.matrix.AxisDescriptor;
import org.json.JSONException;
import org.kohsuke.stapler.DataBoundConstructor;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Ross Rowe
 */
public class SeleniumRCAxis extends BrowserAxis {

    private static final Logger logger = Logger.getLogger(SeleniumRCAxis.class.getName());

    @DataBoundConstructor
    public SeleniumRCAxis(List<String> values) {
        super(values);
    }

    @Override
    protected Browser getBrowserForKey(String value) {
        return BROWSER_FACTORY.seleniumBrowserForKey(value);
    }

    @Extension
    public static class DescriptorImpl extends AxisDescriptor {
        @Override
        public String getDisplayName() {
            return "Sauce OnDemand SeleniumRC tests";
        }

        public List<Browser> getBrowsers() {
            try {
                return BROWSER_FACTORY.getSeleniumBrowsers();
            } catch (IOException e) {
                logger.log(Level.WARNING, "Error retrieving browsers from Saucelabs", e);
            } catch (JSONException e) {
                logger.log(Level.WARNING, "Error parsing JSON response", e);
            }
            return Collections.emptyList();
        }
    }
}
