package dev.micah.powertools.id;

import org.apache.commons.lang.RandomStringUtils;

public class ItemIdentifier {

    public static String generateId() {
        return RandomStringUtils.random(8,
                "0123456789" +
                        "abcdefghijklmnopqrstuvwxyz" +
                        "ABCDEFGHIJKLMNOPQRSTUVWXYZ");
    }

}
