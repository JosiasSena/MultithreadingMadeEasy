package com.example.josiassena.mutltithreading

import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.josiassena.mutltithreading.futures.CompletableFuturesExample
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

class MainActivity : AppCompatActivity() {

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // AsyncTask Example
        AsyncTaskExample().execute("test1", "test2", "test3")

        // Thread example
        runThreadExample()

        // Handler example
        runHandlerExample()

        // Timer example
        runTimerExample()

        // RxJava example
        runRxJavaExample()
        runRxJavaExample2()

        // Futures example
        runFuturesExample()

    }

    private fun runThreadExample() {
        val thread = Thread({
            // The code that runs in here will be running in a separate thread
            Log.d(TAG, "runThreadExample(): ${Thread.currentThread().name}")
        })

        thread.start()
    }

    private fun runHandlerExample() {
        val handlerThread = HandlerThread("handlerThreadName")
        handlerThread.start() // you must first start the handler thread before using it

        val handler = Handler(handlerThread.looper)

        handler.post({
            // runs on background thread with the name provided above (e.g 'handlerThreadName')
            Log.d(TAG, Thread.currentThread().name)
        })
    }

    private fun runTimerExample() {
        val timer = Timer() // can also be used to schedule tasks at an interval

        timer.schedule(object : TimerTask() { // Schedules the specified task for execution after the specified delay.
            override fun run() {
                // Runs on background thread
                Log.d(TAG, "runTimerExample(): ${Thread.currentThread().name}")

                timer.cancel() // Terminates this timer, discarding any currently scheduled tasks.
                timer.purge() // Removes all cancelled tasks from this timer's task queue.
            }
        }, 0) // delay in milliseconds
    }

    private fun runRxJavaExample() {
        // For this example lets use a string array and rx maps as the
        // methods used to run on separate threads and log the thread names out
        Observable.fromArray(arrayOf("test1", "test2"))
                .map {
                    // Runs on scheduler/background thread
                    Log.d(TAG, "First (background) map function: ${Thread.currentThread().name}")
                }
                .map {
                    // Runs on scheduler/background thread
                    Log.d(TAG, "Second (background) map function: ${Thread.currentThread().name}")
                }
                // anything before this runs on the Schedulers.io thread
                .subscribeOn(Schedulers.io())
                // anything after this runs on the Schedulers.computation() thread
                .observeOn(Schedulers.computation())
                .map {
                    // Runs on computation/background thread
                    Log.d(TAG, "Third (Computation) map function: ${Thread.currentThread().name}")
                }
                // anything after this runs on the AndroidSchedulers.mainThread()
                .observeOn(AndroidSchedulers.mainThread())
                .map {
                    // Runs on UI thread
                    Log.d(TAG, "Fourth (UI/main) map function: ${Thread.currentThread().name}")
                }
                .subscribe({
                    // Runs on UI/main thread
                    Log.d(TAG, "runRxJavaExample().subscribe: ${Thread.currentThread().name}")
                })
    }

    private fun runRxJavaExample2() {
        // For this example lets use a string array and rx maps as the
        // methods used to run on separate threads and log the thread names out
        Observable.fromArray(arrayOf("test1", "test2"))
                .observeOn(AndroidSchedulers.mainThread())
                .map {
                    // Runs on UI thread no matter what
                    Log.d(TAG, "Map function: ${Thread.currentThread().name}")
                }
                .subscribeOn(Schedulers.io()) // has no effect
                .subscribe({
                    // Runs on UI/main thread
                    Log.d(TAG, "runRxJavaExample2().subscribe: ${Thread.currentThread().name}")
                })
    }

    private fun runFuturesExample() {
        // FuturesExample().execute()

        CompletableFuturesExample().execute()
    }
}
