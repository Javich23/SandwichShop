package org.yearup.sandwichshop;

import java.util.Scanner;

public class OrderScreen {
    static Scanner consoleInput = new Scanner(System.in);
    private Order order;
    private Topping topping;

    public OrderScreen(Order order) {
        this.order = order;
    }

    public void orderScreen() {
        boolean exit = false;

        while (!exit) {
            System.out.println("""
                                    
                    ======= ORDER HERE ========
                    
                    [1] ADD SANDWICH
                    [2] ADD DRINK
                    [3] ADD CHIP
                    [4] CHECKOUT
                    [0] CANCEL ORDER
                    """);
            System.out.print("CHOOSE AN OPTION: ");
            int choice = consoleInput.nextInt();
            switch (choice) {
                case 1-> addSandwich();
                case 2-> addDrink();
                case 3-> addChip();
                case 4-> checkout();
                case 0-> {
                    System.out.println("\nCANCELLING ORDER...");
                    exit = true;
                }
                default-> System.out.println("Oops, that didn't work. Try again!");
            }
        }
    }
    private void addSandwich() {

        System.out.println("""
                
                [1] WHITE
                [2] WHEAT
                [3] RYE
                [4] WRAP
                """);
        System.out.print("CHOOSE A BREAD: ");
        int bread = consoleInput.nextInt();
        SandwichBread sandwichBread = SandwichBread.values()[bread - 1];
        System.out.println("""
                
                [1] 4"
                [2] 8"
                [3] 12"
                """);
        System.out.print("CHOOSE A SIZE: ");
        int size = consoleInput.nextInt();
        SandwichSize sandwichSize = SandwichSize.values()[size - 1];
        Sandwich sandwich1 = new Sandwich(sandwichSize,sandwichBread);
        System.out.println("""
                
                [1] STEAK
                [2] HAM
                [3] SALAMI
                [4] ROAST BEEF
                [5] CHICKEN
                [6] BACON
                [0] NO MEAT
                """);
        System.out.print("CHOOSE A MEAT (ENTER NUMBER): ");
        int meat = consoleInput.nextInt();
        if(meat > 0) {
            Topping meatTopping = Topping.values()[meat - 1];
            Sandwich.addTopping(meatTopping);
        } else if(meat == 0){
            System.out.println("NO MEAT");
        }
        System.out.print("WOULD YOU LIKE EXTRA MEAT(Y/N): ");
        String extraChoice = consoleInput.next().toUpperCase();
        if(extraChoice.equalsIgnoreCase("y")) {
            Extras choice = new Extras(ExtraChoice.EXTRA_MEAT, sandwichSize);
            order.addOrder(choice);
        }
        System.out.println("""
                
                [7] AMERICAN
                [8] PROVOLONE
                [9] CHEDDAR
                [10] SWISS
                """);
        System.out.print("CHOOSE A CHEESE(ENTER NUMBER): ");
        int cheese = consoleInput.nextInt();
        Topping cheeseTopping = Topping.values()[cheese - 1];
        Sandwich.addTopping(cheeseTopping);
        System.out.print("WOULD YOU LIKE EXTRA CHEESE(Y/N): ");
        String extraCheese = consoleInput.next().toUpperCase();
        if(extraCheese.equalsIgnoreCase("y")) {
            Extras choice = new Extras(ExtraChoice.EXTRA_CHEESE, sandwichSize);
            order.addOrder(choice);
        }
        System.out.print("WOULD YOU LIKE IT TOASTED?(Y/N): ");
        boolean choice = Boolean.parseBoolean(consoleInput.next());
        if(choice) {
            sandwich1.setToasted(true);
        }
        boolean done = false;
        while(!done) {
            System.out.println("""
                    
                    [11] LETTUCE
                    [12] PEPPER
                    [13] ONION
                    [14] TOMATOES
                    [15] JALAPENOS
                    [16] CUCUMBERS
                    [17] PICKLES
                    [18] GUACAMOLE
                    [19] MUSHROOMS
                    [0] DONE WITH TOPPINGS
                    """);

            System.out.print("CHOOSE TOPPING(S): ");
            int topping = consoleInput.nextInt();
            if(topping == 0) {
                done = true;
            } else {
                Topping freeTopping = Topping.values()[topping - 1];
                Sandwich.addTopping(freeTopping);
            }
        }
        boolean loop = false;
        while(!loop) {
            System.out.println("""
                    
                    [1] MAYO
                    [2] MUSTARD
                    [3] KETCHUP
                    [4] RANCH
                    [5] THOUSAND ISLANDS
                    [6] VINAIGRETTE
                    [7] NO SAUCE
                    [0] DONE WITH SAUCES
                    """);

            System.out.print("CHOOSE SAUCE(S): ");
            int sauce = consoleInput.nextInt();
            consoleInput.nextLine();
            if(sauce == 0) {
              loop = true;
            } else if(sauce == 7){
                System.out.println("\nNO SAUCE");
                loop = true;
            } else {
                Sauce sauces = Sauce.values()[sauce - 1];
                Sandwich.addSauce(sauces);
                loop = true;
            }
        }
        order.addOrder(sandwich1);
    }

    private void addDrink() {
        System.out.println("""
                [1] SMALL
                [2] MEDIUM
                [3] LARGE
                """);
        System.out.print("Choose a size: ");
        int size = consoleInput.nextInt();
        DrinkSize drinkSize = DrinkSize.values()[size -1];
        System.out.print("""
                [1] COKE
                [2] SPRITE
                [3] TEA
                [4] JUICE
                """);
        System.out.print("Chose a beverage: ");
        int type = consoleInput.nextInt();
        DrinkType drinkType = DrinkType.values()[type - 1];
        Drink drink = new Drink(drinkSize, drinkType);
        order.addOrder(drink);
        System.out.println("\nAdded " + drinkSize + " " + drinkType);

    }

    private void addChip() {
        System.out.print("""
                [1] BAKED
                [2] BBQ
                [3] JALAPENO
                [4] CHEDDAR
                [5] SEASALT
                """);
        System.out.print("Choose a chip: ");
        int type = consoleInput.nextInt();
        consoleInput.nextLine();
        ChipType chipType = ChipType.values()[type - 1];
        Chip chip = new Chip(chipType);
        order.addOrder(chip);
        System.out.println("\nAdded " + chipType + " CHIPS");
    }

    private void checkout() {
        System.out.println("========= YOUR ORDER =========");
        for(OrderItems item: order.getItems()) {
            System.out.println(item.getDetails());
        }
        System.out.printf("TOTAL PRICE: $%.2f\n", order.getTotalPrice());
        System.out.print("CONFIRM ORDER (Y/N): ");
        String choice = consoleInput.next().toUpperCase();
        if(choice.equalsIgnoreCase("Y")) {
            String receipt = order.getReceipt();
            ReceiptFileManager receiptFileManager = new ReceiptFileManager(order);
            receiptFileManager.saveToFile(receipt);
            order.clear();
        } else if(choice.equalsIgnoreCase("N")){
            System.out.println("\nORDER CANCELLED");
        }
    }
}

