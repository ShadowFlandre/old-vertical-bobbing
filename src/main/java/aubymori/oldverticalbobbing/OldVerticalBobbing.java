package aubymori.oldverticalbobbing;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OldVerticalBobbing implements ModInitializer {
	public static final String MOD_ID = "old-vertical-bobbing";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing Old Vertical Bobbing");
	}
}