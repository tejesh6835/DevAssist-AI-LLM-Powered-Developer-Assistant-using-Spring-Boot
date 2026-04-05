package org.ai.devassistant.orchestrator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class InputSanitizer {

    private static final int MAX_INPUT_LENGTH = 2000;
    Logger log = LoggerFactory.getLogger(InputSanitizer.class);
    public String sanitize(String input) {

        if (input == null) {
            return null;
        }

        if (input.length() > MAX_INPUT_LENGTH) {
            log.info("Input truncated due to size limit");
            return input.substring(0, MAX_INPUT_LENGTH);
        }

        return input;
    }
}
