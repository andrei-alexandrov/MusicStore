package shop;
import instruments.Instrument;
import util.Validation;
import java.util.*;

public class Shop {

    //Characteristics:
    private String name;
    private double money;
    HashMap<String, HashMap<String, HashSet<Instrument>>> warehouse; //Размерът на хешсета е бройката налични инструменти
    HashMap<String, HashMap<String, Integer>> sold;

    //Constructors:
    public Shop(String name) {
        if (Validation.textValidation(name)) {
            this.name = name;
        }

        warehouse = new HashMap<>();
        sold = new HashMap<>();
    }

    //Methods:
    public void sellInstrument(String instrumentName, int quantity) {
        boolean availabilityForSale = false;
        for (HashMap<String, HashSet<Instrument>> stelaj : warehouse.values()) { //Обхождам стойностите на втория хешмап (всички стелажи)
            if (stelaj.containsKey(instrumentName)) {
                HashSet<Instrument> kashon = stelaj.get(instrumentName);             //Намерил съм кашона с търсените инструменти и го взимам от секцията
                if (kashon.size() >= quantity) {                          //Проверявам има ли достатъчно инструменти, спрямо количеството, което се търси
                    availabilityForSale = true;
                    int counter = 0;
                    for (Iterator<Instrument> it = kashon.iterator(); it.hasNext(); ) { //Ползвам итератор в хешсет, за да мога да премахвам елементи
                        counter++;
                        Instrument instrument = it.next();
                        this.money += instrument.getPrice();            //Добавям парите в касата на магазина
                        addToSold(instrument);                         //Викам метода за всеки един инструмент и после го премахвам, докато има налични инструменти
                        it.remove();
                        if (counter == quantity) {
                            break;
                        }
                    }
                    break;
                }
            }
        }
        if (!availabilityForSale) {
            System.out.println("Out of stock");
        }
    }

    public void addToSold(Instrument instrument) {
        if (!sold.containsKey(instrument.getType())) { //Проверка за наличност на ключ
            sold.put(instrument.getType(), new HashMap<>()); //Добавяне
        }
        if (!sold.get(instrument.getType()).containsKey(instrument.getName())) { //Проверка за наличност на ключ за втпрощ хешмап
            sold.get(instrument.getType()).put(instrument.getName(), 1); //Добавяне
        } else {
            sold.get(instrument.getType()).put(instrument.getName(), sold.get(instrument.getType()).get(instrument.getName()) + 1); //Инкрементирам хешмапа
        }

    }

    public void receiveInstrument(ArrayList<Instrument> instrumentsDelivery) {
        for (Instrument instrument : instrumentsDelivery) {
            if (!warehouse.containsKey(instrument.getType())) { //Проверявам има ли наличен ключ
                warehouse.put(instrument.getType(), new HashMap<>()); //Ако няма, създавам
            }
            if (!warehouse.get(instrument.getType()).containsKey(instrument.getName())) { //Проверявам отново за втория хешмап
                warehouse.get(instrument.getType()).put(instrument.getName(), new HashSet<>()); //Ако няма, създавам
            } else {
                warehouse.get(instrument.getType()).get(instrument.getName()).add(instrument); //Добавям инструмент
            }
        }
    }

    public void printAvailabilityByType() {
        System.out.println("-------------------------------");
        System.out.println("Warehouse availability by type:");
        for (Map.Entry<String, HashMap<String, HashSet<Instrument>>> e : warehouse.entrySet()) {
            System.out.println("======== " + e.getKey() + " ========");
            for (Map.Entry<String, HashSet<Instrument>> ee : e.getValue().entrySet()) {
                System.out.println(" - " + ee.getKey() + " - " + ee.getValue().size());
            }
        }
    }

    public void printSortedByName() {
        System.out.println("-------------------------------");
        System.out.println("Instruments sorted by name:");
        //Правя ArrayList, обхождам хешмапа с инструменти и преливам в ArrayList, за да мога да ползвам метод .sort()
        ArrayList<Instrument> sortedByName = new ArrayList<>();
        ;
        for (HashMap<String, HashSet<Instrument>> stelaj : warehouse.values()) {
            for (HashSet<Instrument> kashon : stelaj.values()) {
                sortedByName.addAll(kashon);
            }
        }
        sortedByName.sort((o1, o2) -> o1.getName().compareTo(o2.getName()));
        for (Instrument instrument : sortedByName) {
            System.out.println(instrument.getName());
        }
    }

    public void printSortedByPrice(SortingOrder order) { //Ползвам енъм като аргумент
        System.out.println("------------------------------");
        System.out.println("Instruments sorted by price:");
        ArrayList<Instrument> sortedByPrice = new ArrayList<>();
        for (HashMap<String, HashSet<Instrument>> stelaj : warehouse.values()) {
            for (HashSet<Instrument> kashon : stelaj.values()) {
                sortedByPrice.addAll(kashon);
            }
        }
        if (order == SortingOrder.ASC) {
            sortedByPrice.sort((o1, o2) -> Double.compare(o1.getPrice(), o2.getPrice()));
        }
        if (order == SortingOrder.DESC) {
            sortedByPrice.sort((o1, o2) -> Double.compare(o2.getPrice(), o1.getPrice()));
        }
        for (Instrument instrument : sortedByPrice) {
            if (order == SortingOrder.ASC) {
                System.out.println("ASC: " + instrument.getName() + " " + instrument.getPrice());
            } else {
                System.out.println("DESC: " + instrument.getName() + " " + instrument.getPrice());
            }
        }
    }

    public void printSoldInstruments() {
        System.out.println("------------------------------");
        System.out.println("Sold instruments:");
        for (Map.Entry<String, HashMap<String, Integer>> e : sold.entrySet()) {
            System.out.println("======== " + e.getKey() + " ========");
            for (Map.Entry<String, Integer> ee : e.getValue().entrySet()) {
                System.out.println(" - " + ee.getKey() + " - " + ee.getValue());
            }
        }
    }

    public void printProfit() {
        System.out.println("------------------------------");
        System.out.println("Total money in the shop after sales: " + getMoney());
    }

    //Getters and Setters:
    public double getMoney() {
        return money;
    }
}
