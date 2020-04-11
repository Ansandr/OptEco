package me.playernguyen.opteco.transaction;

import java.util.ArrayList;

/**
 * Storage of transaction handler class <br>
 * Create the database to contain this class
 * @since 1.5b
 */
public interface TransactionStorage {

    /**
     * Push the transaction into the database. If the transaction is available, calling {@link #updateTransaction(Transaction)}.
     * @param transaction {@link Transaction} the transaction class
     */
    void push(Transaction transaction);

    /**
     * Get the transaction list
     * @deprecated Every transaction has just one private key. Please using {@link #getTransaction(String)}
     * @param id The id you want to search
     * @return {@link Transaction}
     */
    ArrayList<TransactionResult> getList(String id);

    /**
     * Get the transaction storage in database via id. It's work by find the first value and put it. <br>
     * Whether it's two and more same id, please using {@link #getList(String)}
     * @param id id of transaction
     * @return The transaction
     */
    TransactionResult getTransaction(String id);

    /**
     * Update the transaction
     * @param transaction {@link Transaction} object
     * @return the state are update or not
     */
    boolean updateTransaction(Transaction transaction);

}
