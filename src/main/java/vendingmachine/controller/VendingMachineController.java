package vendingmachine.controller;

import vendingmachine.domain.Changes;
import vendingmachine.domain.InvestmentMoney;
import vendingmachine.domain.PossessionCoins;
import vendingmachine.domain.PossessionCoinsGenerator;
import vendingmachine.domain.PossessionMoney;
import vendingmachine.domain.Products;
import vendingmachine.domain.VendingMachine;
import vendingmachine.view.InputView;
import vendingmachine.view.OutputView;

public class VendingMachineController {
    private final PossessionCoinsGenerator possessionCoinsGenerator;

    public VendingMachineController(PossessionCoinsGenerator possessionCoinsGenerator) {
        this.possessionCoinsGenerator = possessionCoinsGenerator;
    }

    public void run() {
        PossessionCoins possessionCoins = getPossessionCoins();
        Products products = InputView.getProducts();
        VendingMachine vendingMachine = new VendingMachine(possessionCoins, products);

        operate(vendingMachine);
    }

    private void operate(VendingMachine vendingMachine) {
        InvestmentMoney investmentMoney = InputView.getInvestmentMoney();

        while (vendingMachine.isOperate(investmentMoney)) {
            buy(vendingMachine, investmentMoney);
        }

        finish(vendingMachine, investmentMoney);
    }

    private void buy(VendingMachine vendingMachine, InvestmentMoney investmentMoney) {
        try {
            OutputView.printInvestmentMoney(investmentMoney);
            vendingMachine.buy(investmentMoney, InputView.getProductPurchase());
        } catch (IllegalArgumentException e) {
            buy(vendingMachine, investmentMoney);
        }
    }

    private PossessionCoins getPossessionCoins() {
        PossessionMoney possessionMoney = InputView.getPossessionMoney();
        PossessionCoins possessionCoins = possessionCoinsGenerator.generate(possessionMoney);

        OutputView.printPossessionCoins(possessionCoins);
        return possessionCoins;
    }

    private void finish(VendingMachine vendingMachine, InvestmentMoney investmentMoney) {
        OutputView.printInvestmentMoney(investmentMoney);
        Changes changes = vendingMachine.getChanges(investmentMoney);
        OutputView.printChanges(changes);
    }
}