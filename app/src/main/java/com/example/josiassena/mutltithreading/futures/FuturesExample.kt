package com.example.josiassena.mutltithreading.futures

import android.os.SystemClock
import android.util.Log
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

/**
 * @author Josias Sena
 */
class FuturesExample {

    private val executorService = Executors.newFixedThreadPool(1)

    companion object {
        private val TAG = FuturesExample::class.java.simpleName
    }

    fun execute() {
        // Runs a task on a background thread. For running multiple task,
        // you can use executorService.invokeAll() which takes a array of callable's
        val future = executorService.submit {
            // runs in background pool thread
            Log.d(TAG, "execute(): " + Thread.currentThread().name)
        }

        // As long as the task is still happening, do not ove on
        while (!future.isDone) {
            // wait 1 second
            SystemClock.sleep(TimeUnit.SECONDS.toMillis(1))
        }

        // We are now done, time to shut down
        executorService.shutdown()
    }
}
