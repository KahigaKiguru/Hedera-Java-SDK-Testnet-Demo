package com.enzitechnologies.hederaexamples;

import com.enzitechnologies.hederaexamples.HederaHCS.HederaService;
import com.hedera.hashgraph.sdk.Client;
import com.hedera.hashgraph.sdk.Hbar;
import com.hedera.hashgraph.sdk.HederaStatusException;
import com.hedera.hashgraph.sdk.TransactionId;
import com.hedera.hashgraph.sdk.account.AccountId;

public class HederaExamples {
    public static void main(String[] args) throws HederaStatusException {

        HederaService service = new HederaService();

        AccountId mainAccountId = service.getMainAccountID();

        System.out.println();

//        Get Main Account
        Client mainAccountClient = service.getMainAccount();
        System.out.println("Your main accountId: " + mainAccountId + " and your account balance is: " + service.checkAccountBalance(mainAccountId, mainAccountClient));

//        Get Operator Account
        Client operatorClient = service.getOperatorClient();
        AccountId operatorId = service.getOperatorAccountID();
        System.out.println("Your Operator Account ID is : " + operatorId + " and Balance is : "
                + service.checkAccountBalance(operatorId,operatorClient));

        service.transferHbar(mainAccountId, operatorId, 0, mainAccountClient);

//        Create Testnet Client
        Client newClient = service.createTestnetClient();

//        Create a new Testnet Client Account
        TransactionId newAccount = service.createTestnetClientAccount(0, mainAccountClient);
//
//        Get the new Account's ID
        AccountId newAccountID = newAccount.getReceipt(newClient).getAccountId();
        System.out.println("The New Account's ID is: " + newAccountID);


//        Transfer Hbar
        TransactionId sendHbar = service.transferHbar( mainAccountId, newAccountID,1000, mainAccountClient);
        System.out.println("The transfer transaction was : " + sendHbar.getReceipt(mainAccountClient).status);

//        Check the new Account's balance
        Hbar newAccountBalance = service.checkAccountBalance(newAccountID, newClient);
        System.out.println("The New Account's balance is : " + newAccountBalance);

//        Request the cost of the query
        long queryCost = service.getQueryCost(operatorId, mainAccountClient);
        System.out.println("The cost of this query is : " + queryCost + " hbars");

//        Get the Operator Account's balance
        Hbar operatorAccountBalance = service.checkAccountBalance(operatorId, operatorClient);
        System.out.println("The Operator Account's balance is : " + operatorAccountBalance);
        System.out.println("The Main Account's balance is: " + service.checkAccountBalance(mainAccountId, mainAccountClient));


    }
}
