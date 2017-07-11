package com.example.josiassena.mutltithreading

import android.os.AsyncTask
import android.util.Log

/**
 * There are a few threading rules that must be followed for this class to work properly:
 *
 * The AsyncTask class must be loaded on the UI thread. This is done automatically as of JELLY_BEAN.
 *
 * The task instance must be created on the UI thread.
 * execute(Params...) must be invoked on the UI thread.
 *
 * Do not call onPreExecute(), onPostExecute(Result), doInBackground(Params...), onProgressUpdate(Progress...) manually.
 *
 * The task can be executed only once (an exception will be thrown if a second execution is attempted.)
 * @author Josias Sena
 *
 * @see [AsyncTask](https://developer.android.com/reference/android/os/AsyncTask.html)
 */
class AsyncTaskExample : AsyncTask<String, String, String>() {

    companion object {
        private val TAG = AsyncTaskExample::class.java.simpleName
    }

    override fun onPreExecute() {
        super.onPreExecute()
        // should print main - this runs on the main thread.
        // Gets called before the doInBackground method does
        Log.d(TAG, "onPreExecute: " + Thread.currentThread().name)
    }

    override fun doInBackground(vararg strings: String): String {
        // should print 'AsyncTask #{number}' - this runs in a background thread
        Log.d(TAG, "doInBackground: " + Thread.currentThread().name)
        return strings[0] /* Return anything for this example, such as test1*/
    }

    override fun onPostExecute(s: String) {
        super.onPostExecute(s)
        // s should be equal to whatever is returned in the doInBackground method,
        // in this case test1
        Log.d(TAG, "onPostExecute() called with: s = [$s]")

        // Should print main - this runs on the main thread.
        // Gets called after the doInBackground method does
        Log.d(TAG, "onPostExecute: " + Thread.currentThread().name)
    }
}
