package net.controlcoffeemachine.util;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

public class LoggerNameFilter extends Filter<ILoggingEvent> {

    @Override
    public FilterReply decide(ILoggingEvent event) {
        return (event.getLoggerName().endsWith("DatabaseLoggingAspect") && event.getMarker().contains("INV"))
                ? FilterReply.ACCEPT
                : FilterReply.DENY;
    }
}
