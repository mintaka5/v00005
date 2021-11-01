package org.white5moke.cj5x;

import org.white5moke.cj5x.transaction.TxOutput;

import java.util.ArrayList;
import java.util.HashMap;

public class Chain {
    private ArrayList<Block> blockChain = new ArrayList<Block>();
    private HashMap<String, TxOutput> unconfirmedTransactions = new HashMap<String, TxOutput>();
    private int difficulty = 5;
}
