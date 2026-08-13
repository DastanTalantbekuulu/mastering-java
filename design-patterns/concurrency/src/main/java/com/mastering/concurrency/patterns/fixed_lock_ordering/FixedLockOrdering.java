package com.mastering.concurrency.patterns.fixed_lock_ordering;

/**
 * Pattern: Fixed Lock Ordering
 * <p>
 * Motivations: Acquiring locks in a non fixed-order can deadlock if they're called at the
 * same time, but with the inverse order.
 * <p>
 * Intent: Create a fixed-ordered locking mechanism to prevent possibles
 * deadlocks. We define a value to the locks objects and use comparisons to
 * establish a fixed order bases on who is greater or lesser.
 * <p>
 * Applicability: Every time when acquiring more than one lock.
 *
 */
public class FixedLockOrdering {

    public record Lockable(int id,  String anotherValue) {
    }

    public void doSomeOperation(Lockable obj1, Lockable obj2) {
        int obj1Id = obj1.id();
        int obj2Id = obj2.id();
        if (obj1Id < obj2Id) {
            synchronized (obj1) {
                synchronized (obj2) {
                    // action
                    System.out.println(obj1Id + " " + obj2Id);
                }
            }
        } else {
            synchronized (obj2) {
                synchronized (obj1) {
                    // action
                    System.out.println(obj1Id + " " + obj2Id);
                }
            }
        }
    }

}
