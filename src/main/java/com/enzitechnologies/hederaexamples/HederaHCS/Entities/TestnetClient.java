package com.enzitechnologies.hederaexamples.HederaHCS.Entities;

import com.hedera.hashgraph.sdk.Client;
import com.hedera.hashgraph.sdk.account.AccountId;
import com.hedera.hashgraph.sdk.crypto.ed25519.Ed25519PrivateKey;

public class TestnetClient {
    /**
     * Creates a new new Testnet client
     * @param accountId
     * @param privateKey
     */
    public Client createClient(AccountId accountId, Ed25519PrivateKey privateKey) {
        return Client
                .forTestnet()
                .setOperator(accountId, privateKey);
    }
}
