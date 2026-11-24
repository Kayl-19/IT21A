package lesson6_polymorphism_abstraction;

class DeliveryService {

    protected String packageName;
    protected double distanceKm;

    public DeliveryService(String packageName, double distanceKm) {
        this.packageName = packageName;
        this.distanceKm = distanceKm;
    }

    public double calculateFee() {
        return 50.0;
    }

    public String estimateTime() {
        return "Standard delivery: 3 days";
    }

    public void showSummary() {
        System.out.println("Package   : " + packageName);
        System.out.println("Distance  : " + distanceKm + " km");
        System.out.println("Fee       : PHP " + calculateFee());
        System.out.println("Time      : " + estimateTime());
        System.out.println("-----------------------------------");
    }
}

class MotorcycleDelivery extends DeliveryService {

    public MotorcycleDelivery(String packageName, double distanceKm) {
        super(packageName, distanceKm);
    }

    @Override
    public double calculateFee() {
        return 40.0 + (distanceKm * 12.0);
    }

    @Override
    public String estimateTime() {
        return "Motorcycle rider: about " + (int) Math.ceil(distanceKm / 30.0 * 60) + " minutes";
    }
}

class DroneDelivery extends DeliveryService {

    public DroneDelivery(String packageName, double distanceKm) {
        super(packageName, distanceKm);
    }

    @Override
    public double calculateFee() {
        return 150.0 + (distanceKm * 8.0);
    }

    @Override
    public String estimateTime() {
        return "Drone flight: about " + (int) Math.ceil(distanceKm / 60.0 * 60) + " minutes";
    }
}

class TruckDelivery extends DeliveryService {

    public TruckDelivery(String packageName, double distanceKm) {
        super(packageName, distanceKm);
    }

    @Override
    public double calculateFee() {
        return 300.0 + (distanceKm * 5.0);
    }

    @Override
    public String estimateTime() {
        return "Truck: about " + (int) Math.ceil(distanceKm / 40.0) + " hour(s) plus loading time";
    }
}

public class PolymorphismDemo {

    public static void main(String[] args) {
        System.out.println("===== Delivery App: Polymorphism Demo =====\n");

        DeliveryService order1 = new MotorcycleDelivery("Birthday cake", 8);
        DeliveryService order2 = new DroneDelivery("Medicine", 15);
        DeliveryService order3 = new TruckDelivery("Refrigerator", 60);
        DeliveryService order4 = new DeliveryService("Letter", 5);

        DeliveryService[] orders = {order1, order2, order3, order4};
        for (DeliveryService order : orders) {
            order.showSummary();
        }

        double total = 0;
        for (DeliveryService order : orders) {
            total += order.calculateFee();
        }
        System.out.println("Total of all fees: PHP " + total);
    }
}
