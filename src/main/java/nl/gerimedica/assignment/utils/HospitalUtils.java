package nl.gerimedica.assignment.utils;

import java.util.concurrent.atomic.AtomicLong;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HospitalUtils {

    private static final AtomicLong usageCounter = new AtomicLong(0);

    public static void recordUsage(String context) {
        long count = usageCounter.incrementAndGet();
        log.info("HospitalUtils used. Counter: {} | Context: {}", count, context);
    }
}
