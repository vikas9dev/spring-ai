package com.knowprogram.openai.tools;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
@Slf4j
public class TimeTools {

    @Tool(name = "getCurrentLocalTime", description = "Returns the current time in the user's time zone")
    public String getCurrentLocalTime() {
        log.info("Returning the current time in the user's time zone");
        return LocalDateTime.now().toString();
    }

    @Tool(name = "getCurrentTime", description = "Returns the current time in the specified time zone")
    public String getCurrentTime(@ToolParam(description = "Value representing the time zone") String timeZone) {
        log.info("Returning the current time in timezone: {}", timeZone);
        return LocalDateTime.now(ZoneId.of(timeZone)).toString();
    }
}
