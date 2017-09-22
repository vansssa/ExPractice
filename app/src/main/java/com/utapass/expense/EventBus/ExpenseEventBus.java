package com.utapass.expense.EventBus;

/**
 * Created by vanessatsai on 2017/9/22.
 */

public class ExpenseEventBus {

    private String message;

    public ExpenseEventBus(String message){
        this.message =message;

    }

    public String getMessgae() {
        return message;
    }
}
