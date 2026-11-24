# Lesson 6: Polymorphism and Abstraction

## What each program does

**PolymorphismDemo.java** simulates a delivery app. A package can be sent by motorcycle, drone, or truck, and each option computes its own fee and delivery time. The program stores different delivery types in one array, loops through it, and prints a summary for each order without ever checking which type it is dealing with.

**AbstractionDemo.java** simulates the checkout of an online store. A customer can pay through GCash, credit card, or cash on delivery. All payment types share the same receipt-printing logic, but each one has its own way of processing the money (checking a wallet, contacting a bank, or collecting cash at the door). The program also shows a failed card payment, so the receipt can display both PAID and FAILED.

## How polymorphism works in my code

- **Parent class:** `DeliveryService`
- **Child classes:** `MotorcycleDelivery`, `DroneDelivery`, `TruckDelivery`
- **Overridden methods:** `calculateFee()` and `estimateTime()`

The parent gives a default version of each method (a flat PHP 50 fee and a 3-day estimate). Each child replaces them with its own rules. For example, the drone has a high starting fee but a cheap per-kilometer rate, while the motorcycle is the opposite.

The key line is `DeliveryService order2 = new DroneDelivery("Medicine", 15);`. The variable type is the parent, but the real object is a drone. When I call `order2.showSummary()`, Java looks at the real object at runtime and uses the drone's version of `calculateFee()`. So one loop handles every delivery type, and each object behaves in its own way.

## How abstraction works in my code

- **Abstract class:** `OnlinePayment`
- **Abstract method:** `processPayment(double amount)`, which has no body in the parent
- **Implementations:** `GCashPayment`, `CreditCardPayment`, and `CashOnDeliveryPayment` each write their own `processPayment`

`OnlinePayment` only declares *what* every payment must be able to do (process an amount) and leaves *how* to the children. It also holds concrete code that every child reuses: `printReceipt()` and `isValidAmount()`. The main method uses the child classes, and the line that tries to create an `OnlinePayment` directly is commented out because it would not compile.

## Difference between polymorphism and abstraction

Abstraction is about **design**: deciding what a group of classes must have in common and hiding the details that differ. Polymorphism is about **behavior at runtime**: the same method call doing different things depending on which object receives it.

A simple way I remember it: abstraction says "every payment must be processable," and polymorphism is what happens when I call `processPayment()` and each payment does it in its own way. Abstraction sets up the contract, and polymorphism is how that contract is used.

## Reflection questions

**1. Why is method overriding important in polymorphism? What happens if a child class does not override the parent's method?**

Overriding is what lets the same method name produce different results for different child objects. Without it, calling `calculateFee()` on a drone, a truck, and a motorcycle would give identical results, and there would be nothing "many-formed" about it. If a child does not override the method, it simply inherits the parent's version. In my program, if `DroneDelivery` had no `calculateFee()`, it would charge the flat PHP 50 from `DeliveryService`, which would be wrong for a drone. The program would still compile and run, so this kind of mistake can go unnoticed. That is one reason `@Override` is useful: it makes the compiler catch a misspelled method name.

**2. Why can't we create an object directly from an abstract class? What is the purpose of this restriction?**

An abstract class is intentionally incomplete because it has at least one method with no body. If I could create an `OnlinePayment` object and call `processPayment()`, there would be no code to run. Java blocks this so that only complete classes can become real objects. The restriction also protects the design: it forces every real payment type to define its own processing, so nobody can accidentally use a half-finished "generic payment."

**3. If you need to add another child class to your polymorphism program, how would your existing code handle it?**

It would handle it without any changes. If I add `BicycleDelivery extends DeliveryService` and override `calculateFee()` and `estimateTime()`, I only need to create the object and put it in the array. The loop and `showSummary()` already work with the parent type, so they would use the new class's behavior automatically. This is the real benefit of polymorphism: new features are added by writing new classes instead of editing code that already works (no growing chain of `if/else` checks on delivery type).

**4. Can polymorphism and abstraction work together in a single program? Explain how and provide an example.**

Yes, and they usually do. In `AbstractionDemo`, `OnlinePayment` is the abstraction and its children implement `processPayment()`. If I write `OnlinePayment p = new GCashPayment("Maria", 1500);` and later `p = new CreditCardPayment(...)`, then `p.processPayment(500)` runs a different version each time. That is polymorphism working on top of an abstract class. A quick example is a list such as `OnlinePayment[] payments = { gcash, card, cod };` with a loop calling `processPayment(amount)` on each one. The abstract class guarantees every item has that method, and polymorphism picks the right version when it runs.

**5. As a future programmer, when would you use polymorphism, and when would you use abstraction?**

I would use abstraction when I am designing a group of related classes and I want to force a common structure, such as every shape having an `area()` or every report having a `generate()`. It is the right tool when the parent class alone makes no sense as a real object. I would use polymorphism when my code needs to handle many types through one common interface, such as looping through different notifications, payments, or employees and calling the same method on each. In practice I would start with abstraction to define the shared design, then rely on polymorphism so the rest of the program does not need to know which specific type it is using.

## How to run

```
javac PolymorphismDemo.java AbstractionDemo.java
java PolymorphismDemo
java AbstractionDemo
```
