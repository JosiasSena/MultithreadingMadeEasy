package com.example.josiassena.mutltithreading.futures

import android.os.Build
import android.util.Log
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors
import java.util.function.Function
import java.util.function.Supplier

/**
 * File created by josiassena on 7/10/17.
 */
class CompletableFuturesExample {

    companion object {
        private val TAG = CompletableFuturesExample::class.java.simpleName
    }

    fun execute() {
        val executorService = Executors.newSingleThreadExecutor()

        // create a supplier object to start with
        val supplier = Supplier { "Hello World!" }

        // This function takes some text and returns its length
        val function = Function<String, Int> { text -> text.length }

        // requires api 24+ (N)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            CompletableFuture
                    .supplyAsync(supplier, executorService) // provide the initial supplier and specify executor
                    .thenApply(function) // do something
                    .whenComplete { int, _ ->
                        Log.d(TAG, "$int") // returns the length of 'Hello World!'

                        executorService.shutdown() // complete
                    }
        }
    }

}
