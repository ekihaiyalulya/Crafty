package com.example.crafty;

import java.util.List;

public class DataResult {
    private List<Integer> quantityData;
    private int profitSum;
    private int salesSum;
    private int quantitySum;

    public DataResult(List<Integer> quantityData, int profitSum, int salesSum, int quantitySum) {
        this.quantityData = quantityData;
        this.profitSum = profitSum;
        this.salesSum = salesSum;
        this.quantitySum = quantitySum;
    }

    public List<Integer> getQuantityData() {
        return quantityData;
    }

    public int getProfitSum() {
        return profitSum;
    }
    public int getSalesSum() {
        return salesSum;
    }
    public int getQuantitySum() {
        return quantitySum;
    }
}

