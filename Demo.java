import instruments.Instrument;
import instruments.keyboards.Piano;
import instruments.percussions.Drum;
import instruments.strings.Guitar;
import instruments.strings.Violin;
import instruments.winds.Flute;
import shop.Shop;
import shop.SortingOrder;

import java.util.ArrayList;

public class Demo {
    public static void main(String[] args) {

        Shop shop = new Shop("Andrew's shop");

        ArrayList<Instrument> instrumentsDelivery = new ArrayList<>();

        for (int i = 0; i < 11; i++) {
            instrumentsDelivery.add(new Violin(250.50));
            instrumentsDelivery.add(new Guitar(200.00));
            instrumentsDelivery.add(new Piano(500));
            instrumentsDelivery.add(new Drum(300));
            instrumentsDelivery.add(new Flute(100));
        }

        shop.receiveInstrument(instrumentsDelivery);

        shop.sellInstrument("Violin", 1);
        shop.sellInstrument("Guitar", 2);
        shop.sellInstrument("Piano", 3);
        shop.sellInstrument("Drum", 4);
        shop.sellInstrument("Flute", 5);

        shop.printAvailabilityByType();
        shop.printSoldInstruments();
        shop.printSortedByName();
        shop.printSortedByPrice(SortingOrder.ASC);
        shop.printSortedByPrice(SortingOrder.DESC);
        shop.printProfit();

    }
}
