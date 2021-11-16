import { TransactionState } from "../../store/modules/transactions/types";
import { mount } from "@vue/test-utils";
import { createStore, Store } from "vuex";
import TransactionList from "../../components/TransactionList.vue";
import MyTransactions from "../../views/MyTransactions.vue";
import { Plugin } from "@vue/runtime-core";

import flushPromises from "flush-promises";


describe("TransactionList", () => {
    const initState: TransactionState = {
      transactionStatus: "",
      transactions: [
        {
            id: 1,
            from: 8,
            to: 9,
            amount: 100,
            date: "22-10-2021",
        },
        {
            id: 2,
            from: 4,
            to: 5,
            amount: 75,
            date: "25-10-2021",
        },
      ],
    };
    
    let mockAllTransactions: jest.Mock<any, any>;
    let mockFetchAccounts: jest.Mock<any, any>;
    let mockFetchTransactions: jest.Mock<any, any>;

    let store: Store<any> | Plugin | [Plugin, ...any[]];
  
    beforeEach(() => {
        mockAllTransactions = jest.fn().mockReturnValue(initState.transactions);
        mockFetchAccounts = jest.fn();
        mockFetchTransactions = jest.fn();
        store = createStore({
        state: initState,
        getters: {
          allTransactions: () => mockAllTransactions,
        },
        actions: {
          fetchTransactions: mockFetchAccounts,
          fetchAccounts: mockFetchAccounts
        }
      });
    });

  
    test("test initial state", () => {
      const wrapper = mount(MyTransactions, {
        global: { plugins: [store] },
      });


  
      expect(wrapper.html()).toContain("Dato");
      expect(mockAllTransactions).toHaveBeenCalled();
    });
  
    
  });