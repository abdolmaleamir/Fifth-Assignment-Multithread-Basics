Answer 1 :
Thread was interrupted!
Thread will be finished here!!!
Both the sentences will be printed. The message "Thread was interrupted!"" will be printed due catch block handling the InterruptedException. Then, the message "Thread will be finished here!!!" will also be printed because it's part of the finally block, which always executes.


Answer 2 :
It will be executed in the current thread, not in a separate thread.
First, the code contains a mistake where the main function needs to not be static.
Second, If the run() method of a Runnable object is invoked directly without initiating it inside a Thread object, it will be executed in the current thread, not in a separate thread.
So, the message "Running in: Thread.currentThread().getName()" will be printed, where "Thread.currentThread().getName()"" is the name of the thread executing the main() method.

Answer 3 :
When the join() method of Thread_0 is invoked within the main() method:
Thread_0 starts execution when thread.start() is called.
The main thread then waits for Thread_0 to finish its execution by calling thread.join().
If Thread_0 completes execution without being interrupted, the main thread continues its execution after thread.join() and prints "Back to: Main thread".


