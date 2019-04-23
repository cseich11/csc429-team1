package model;

import exception.InvalidPrimaryKeyException;

public class Main2 {
    public static void main(String[] args) throws InvalidPrimaryKeyException {
        InventoryItem test = new InventoryItem("1234");
        System.out.println(test);
    }
}
