package com.enzitechnologies.hederaexamples.HederaHCS;

import com.hedera.hashgraph.sdk.Client;
import com.hedera.hashgraph.sdk.Hbar;
import com.hedera.hashgraph.sdk.HederaStatusException;
import com.hedera.hashgraph.sdk.TransactionId;
import com.hedera.hashgraph.sdk.account.AccountBalanceQuery;
import com.hedera.hashgraph.sdk.account.AccountCreateTransaction;
import com.hedera.hashgraph.sdk.account.AccountId;
import com.hedera.hashgraph.sdk.account.CryptoTransferTransaction;
import com.hedera.hashgraph.sdk.crypto.ed25519.Ed25519PrivateKey;
import com.hedera.hashgraph.sdk.crypto.ed25519.Ed25519PublicKey;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.Objects;

public class HederaService {

    /** Create Testnet Client
     *
     *  Grabs your Testnet Account information and creates a client.
     *
     */
    public Client createTestnetClient(){
        Client client = Client.forTestnet()
                .setOperator(getOperatorAccountID(), getOperatorAccountPrivateKey());
        return client;
    }

    /** Create Testnet Client Account
     *
     * Creates a new Testnet account for a Client
     *
     */
    public TransactionId createTestnetClientAccount(long initialBalance, Client client) throws HederaStatusException {
//        Generate a private key for new account
        Ed25519PrivateKey testnetClientPrivateKey = Ed25519PrivateKey.generate();
//        Extract a public key from testnetClientPrivateKey
        Ed25519PublicKey testnetClientPublicKey = testnetClientPrivateKey.publicKey;
//        Create the account
        return new AccountCreateTransaction()
                    .setKey(testnetClientPublicKey)
                    .setInitialBalance(initialBalance)
                    .execute(client);
    }

    public Hbar checkAccountBalance(AccountId accountId, Client client) throws HederaStatusException{
        return new AccountBalanceQuery()
                .setAccountId(accountId)
                .execute(client);
    }

    public TransactionId transferHbar(AccountId senderAccountId, AccountId recipientAccountId, long amount, Client client) throws HederaStatusException{
        return new CryptoTransferTransaction()
                .addSender(senderAccountId, amount)
                .addRecipient(recipientAccountId, amount)
                .execute(client);
    }
    public long getQueryCost(AccountId accountId, Client client) throws HederaStatusException{
        return new AccountBalanceQuery()
                .setAccountId(accountId)
                .getCost(getOperatorClient());
    }

    public Client getMainAccount(){
        AccountId mainAccountId = getMainAccountID();
        Ed25519PrivateKey mainPrivateKey = getMainAccountPrivateKey();
        return Client.forTestnet().setOperator(mainAccountId, mainPrivateKey);
    }

    public AccountId getMainAccountID() {
        return AccountId.fromString(Objects.requireNonNull(Dotenv.load().get("MAIN_ACCOUNT_ID")));
    }

    public Ed25519PrivateKey getMainAccountPrivateKey() {
       return Ed25519PrivateKey.fromString(Objects.requireNonNull(Dotenv.load().get("MAIN_ACCOUNT_PRIVATE_KEY")));
    }
    public Ed25519PrivateKey getOperatorAccountPrivateKey(){
        return Ed25519PrivateKey.fromString(Objects.requireNonNull(Dotenv.load().get("OPERATOR_ACCOUNT_PRIVATE_KEY")));
    }

    public AccountId getOperatorAccountID(){
        return AccountId.fromString(Objects.requireNonNull(Dotenv.load().get("OPERATOR_ACCOUNT_ID")));
    }

    public Client getOperatorClient(){
        return Client.forTestnet().setOperator(getOperatorAccountID(), getOperatorAccountPrivateKey());
    }

}
