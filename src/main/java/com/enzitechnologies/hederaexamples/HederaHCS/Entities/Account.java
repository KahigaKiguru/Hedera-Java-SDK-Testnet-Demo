package com.enzitechnologies.hederaexamples.HederaHCS.Entities;

import com.hedera.hashgraph.sdk.account.AccountId;
import io.github.cdimascio.dotenv.Dotenv;

public class Account {
    AccountId accountId;

    public AccountId getMainAccountId(){
        return AccountId.fromString(Dotenv.load().get("MY_ACCOUNT_ID"));

    }

}
