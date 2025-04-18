package tests.services;

import java.util.List;

import enums.SortType;
import pages.InventoryPage;

public class InventorySortingService {
    private final InventoryPage inventoryPage;

    public InventorySortingService(InventoryPage inventoryPage) {
        this.inventoryPage = inventoryPage;
    }

    public boolean isSortingAppliedCorrectly(SortType sortType) {
        inventoryPage.selectSortingOption(sortType.getOption());

        switch (sortType) {
            case NAME_A_TO_Z:
            case NAME_Z_TO_A:
                List<String> names = inventoryPage.getProductNames();
                return inventoryPage.isSorted(names, sortType);

            case PRICE_LOW_TO_HIGH:
            case PRICE_HIGH_TO_LOW:
                List<Double> prices = inventoryPage.getProductPrices();
                return inventoryPage.isSorted(prices, sortType);

            default:
                throw new IllegalArgumentException("Unexpected sort type: " + sortType);
        }
    }
}
