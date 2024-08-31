package com.rengwuxian.coursecoroutines._5_collaboration;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class Java {
  int number = 0;
  //  final Object lock = new Object();
  ReentrantLock lock = new ReentrantLock();
  Semaphore semaphore = new Semaphore(3);
  Object waiter = new Object();
  volatile int v = 0;
  transient int t = 0;

  synchronized void synchronizedMethod() {
    try {
      waiter.wait();
      waiter.notify();
      waiter.notifyAll();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    try {
      semaphore.acquire();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    } finally {
      semaphore.release();
    }
    lock.lock();
    try {
      lock.lock();
      try {
        number++;
      } finally {
        lock.unlock();
      }
    } finally {
      lock.unlock();
    }
  }

  void synchronizedMethod2() {
    synchronized (this) {
      number--;
    }
  }
}
