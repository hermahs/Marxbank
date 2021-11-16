import { AccountState } from "../../store/modules/accounts/types";
import { mount } from "@vue/test-utils";
import { createStore, Store } from "vuex";
import MyFavorites from "../../components/MyFavorites.vue";
import MyFavorite from "../../components/MyFavorite.vue";
import AccountInfo from "../../views/AccountInfo.vue";
import { Plugin } from "@vue/runtime-core";
import { TransactionState } from "../../store/modules/transactions/types";



describe("AccountList", () => {
    const initState: AccountState = {
      accountStatus: "",
      accounts: [
        {
          id: 1,
          userId: 1,
          accNumber: 1,
          balance: 200,
          type: "Sparekonto",
          name: "t1",
          interest: 3.0,
        },
        {
          id: 2,
          userId: 1,
          accNumber: 2,
          balance: 300,
          type: "Sparekonto",
          name: "t2",
          interest: 3.0,
        },
        {
            id: 3,
            userId: 1,
            accNumber: 2,
            balance: 100,
            type: "Sparekonto",
            name: "t3",
            interest: 4.0,
          },
          {
            id: 4,
            userId: 1,
            accNumber: 8,
            balance: 50,
            type: "Sparekonto",
            name: "t4",
            interest: 4.5,
          },
      ],
    };
    let mockAllAccounts: jest.Mock<any, any>;
    let store: Store<AccountState> | Plugin | [Plugin, ...any[]];
  
    beforeEach(() => {
      mockAllAccounts = jest.fn().mockReturnValue(initState.accounts);
      store = createStore({
        state: initState,
        getters: {
          allAccounts: mockAllAccounts,
        },
      });
    });
  
    test("test initial state", () => {
      const wrapper = mount(MyFavorites, {
        global: { plugins: [store] },
      });
  
      expect(wrapper.html()).toContain("t1");
      expect(wrapper.html()).toContain("t2");
      expect(wrapper.html()).toContain("t3");

      // Should only contain the first tree accounts of the user.
      expect(wrapper.html()).not.toContain("t4");   
      expect(mockAllAccounts).toHaveBeenCalled();
    });

    test("test showAccount", async () => {
        const mockRoute = {
          params: {
            name: "t1",
          },
        };
        const mockRouter = {
          push: jest.fn(),
        };
    
        const wrapper = mount(MyFavorites, {
          global: {
            plugins: [store],
            mocks: {
              $route: mockRoute,
              $router: mockRouter,
            },
          },
        });
    
        await wrapper.findComponent(MyFavorite).vm.$emit("accountSelected", 1);
    
        //tester at router pusher til AccountInfo fane med parameter 1
        expect(mockRouter.push).toHaveBeenCalledTimes(1);
        expect(mockRouter.push).toHaveBeenCalledWith({
          name: "AccountInfo",
          params: { name: "t1" },
        });
      });
  
  });
  

  