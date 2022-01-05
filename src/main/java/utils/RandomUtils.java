package utils;

import io.cucumber.core.logging.Logger;
import io.cucumber.core.logging.LoggerFactory;

import static java.lang.String.format;
import static org.apache.commons.lang3.RandomStringUtils.random;

public final class RandomUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(RandomUtils.class);

    public static final String PREFIX_NAME = "AutoPet-";
    private static final String CHARS = "1234567890";
    private static final int RANGE = 10;

    public static String generatePetName() {
        return format("%s%s", PREFIX_NAME, random(RANGE, CHARS));
    }
}