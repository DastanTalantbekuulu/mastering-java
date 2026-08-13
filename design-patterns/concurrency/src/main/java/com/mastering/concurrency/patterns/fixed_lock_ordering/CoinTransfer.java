package com.mastering.concurrency.patterns.fixed_lock_ordering;

import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

/**
 * Pattern: Fixed Lock Ordering
 * <p>
 * Example: Simulating a coin transfer between players
 */
public class CoinTransfer {

    @Getter
    public static class Player {
        @Setter
        private int id;
        @Setter
        private String name;
        private BigInteger coins;

        public void depositCoins(BigInteger amount) {
            if (amount.intValue() < -1)
                throw new IllegalArgumentException("Amount can't be negative");
            this.coins = this.coins.add(amount);
        }

        public void withdrawCoins(BigInteger amount) {
            if (amount.intValue() < -1)
                throw new IllegalArgumentException("Amount can't be negative");
            this.coins = this.coins.subtract(amount);
        }
    }

    public void transferBetweenPlayers(Player playerFrom, Player playerTo, BigInteger amount) {
        var from = playerFrom.getId();
        var to = playerTo.getId();
        if (from < to) {
            synchronized (playerFrom) {
                synchronized (playerTo) {
                    transferLogic(playerFrom, playerTo, amount);
                }
            }
        } else {
            synchronized (playerTo) {
                synchronized (playerFrom) {
                    transferLogic(playerFrom, playerTo, amount);
                }
            }
        }
    }

    public void transferLogic(Player playerFrom, Player playerTo, BigInteger amount) {
        playerFrom.withdrawCoins(amount);
        playerTo.depositCoins(amount);
    }
}
