package org.komamitsu.fluency.flusher;

import java.io.Flushable;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

public class SyncFlusher
        extends Flusher
{
    private final AtomicLong lastFlushTimeMillis = new AtomicLong();

    public SyncFlusher(FlusherConfig flusherConfig)
    {
        super(flusherConfig);
    }

    @Override
    protected void flushInternal(Flushable flushable, boolean force)
            throws IOException
    {
        long now = System.currentTimeMillis();
        if (force || now > lastFlushTimeMillis.get() + flusherConfig.getFlushIntervalMillis()) {
            flushable.flush();
            lastFlushTimeMillis.set(now);
        }
    }
}