package de.dotwee.sharecrypter.model.commands;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import timber.log.Timber;

/**
 * Created by Lukas Wolfsteiner on 03.02.2016.
 */
public class CryptCommandManager extends ThreadPoolExecutor {
    private static final String LOG_TAG = "CryptCommandManager";

    private static int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
    private static CryptCommandManager instance;

    /**
     * Creates a new {@code ThreadPoolExecutor} with the given initial
     * parameters and default thread factory and rejected execution handler.
     * It may be more convenient to use one of the {@link Executors} factory
     * methods instead of this general purpose constructor.
     *
     * @throws IllegalArgumentException if one of the following holds:<br>
     *                                  {@code corePoolSize < 0}<br>
     *                                  {@code keepAliveTime < 0}<br>
     *                                  {@code maximumPoolSize <= 0}<br>
     *                                  {@code maximumPoolSize < corePoolSize}
     * @throws NullPointerException     if {@code workQueue} is null
     */
    private CryptCommandManager() {
        super(NUMBER_OF_CORES * 2, NUMBER_OF_CORES * 2, 60L, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>());
        Timber.tag(LOG_TAG);
    }

    public static synchronized CryptCommandManager getInstance() {
        if (instance == null) {
            instance = new CryptCommandManager();
        }
        return instance;
    }

    @Override
    public void execute(Runnable command) {
        super.execute(command);
    }

    public void execute(CryptCommand cryptCommand) {
        Timber.i("Running new crypto action: %s", cryptCommand.toString());
        this.execute(cryptCommand.getRunnable());
    }
}
