package com.coffee.sale.configuration;

import com.coffee.sale.entity.coffee.Coffee;
import com.coffee.sale.entity.coffee.CoffeeHouse;
import com.coffee.sale.entity.coffee.CoffeeId;
import com.coffee.sale.entity.coffee.CoffeeInventory;
import com.coffee.sale.entity.coffee.CoffeeInventoryId;
import com.coffee.sale.entity.coffee.MerchInventory;
import com.coffee.sale.entity.coffee.Supplier;
import com.coffee.sale.entity.employee.Role;
import com.coffee.sale.entity.employee.User;
import com.coffee.sale.repository.jpa.coffee.CoffeeHouseJpa;
import com.coffee.sale.repository.jpa.coffee.CoffeeInventoryJpa;
import com.coffee.sale.repository.jpa.coffee.CoffeeJpa;
import com.coffee.sale.repository.jpa.coffee.MerchInventoryJpa;
import com.coffee.sale.repository.jpa.coffee.SupplierJpa;
import com.coffee.sale.repository.jpa.employee.RoleJpa;
import com.coffee.sale.repository.jpa.employee.UserJpa;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@DependsOn({"employeeEntityManagerFactory", "coffeeEntityManagerFactory"})
public class DataInitializer implements CommandLineRunner {

    public static final List<String> COFFEES = List.of(
            "Эспрессо", "Арабика", "Латте", "Макиато", "Капучино",
            "Ристрето", "Кафе бомбон", "Кофе по-венски", "Меланж", "Фраппе"
    );

    public static final List<String> MERCH = List.of(
            "Чайник", "Кофеварка", "Турка", "Чашка", "Питчер",
            "Аэропресс", "Кофемолка", "Френч-пресс", "Весы", "Разрыхлитель"
    );

    public static final List<String> STREET = List.of(
            "ул. Чынгыз Айтматов ",
            "ул. Ахунбаева ",
            "ул. Алыкулова ",
            "ул. Тоголок Молдо ",
            "ул. Юсупа Абдрахманова ",
            "ул. Курманжан-Датка ",
            "ул. Салиева ",
            "пр. Абсамата Масалиева ",
            "ул. Медерова ",
            "ул. Байтик Баатыр ",
            "ул. Баялинова "
    );

    public static final List<String> CITY = List.of(
            "Бишкек", "Ош", "Каракол", "Талас", "Нарын", "Баткен",
            "Жалал-Абад", "Кызыл-кыя", "Токмок", "Таш-комур", "Город"
    );

    public static final int MIN_QUANTITY = 50;
    public static final int MAX_QUANTITY = 100;
    public static final int MIN_ID = 10_000;
    public static final int MAX_ID = 99_999;

    private final CoffeeJpa coffeeJpa;
    private final CoffeeInventoryJpa coffeeInventoryJpa;
    private final SupplierJpa supplierJpa;
    private final CoffeeHouseJpa coffeeHouseJpa;
    private final MerchInventoryJpa merchInventoryJpa;

    private final UserJpa userJpa;
    private final RoleJpa roleJpa;

    private final Random random;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(
            CoffeeJpa coffeeJpa,
            CoffeeInventoryJpa coffeeInventoryJpa,
            SupplierJpa supplierJpa,
            CoffeeHouseJpa coffeeHouseJpa,
            MerchInventoryJpa merchInventoryJpa,
            UserJpa userJpa,
            RoleJpa roleJpa,
            PasswordEncoder passwordEncoder
    ) {
        this.coffeeJpa = coffeeJpa;
        this.coffeeInventoryJpa = coffeeInventoryJpa;
        this.supplierJpa = supplierJpa;
        this.coffeeHouseJpa = coffeeHouseJpa;
        this.merchInventoryJpa = merchInventoryJpa;
        this.userJpa = userJpa;
        this.roleJpa = roleJpa;
        this.passwordEncoder = passwordEncoder;
        random = new Random();
    }

    public void run(String... args) throws Exception {
        Role role_owner = new Role("OWNER");
        Role role_admin = new Role("ADMIN");
        Role role_user = new Role("USER");

        role_owner = roleJpa.save(role_owner);
        role_admin = roleJpa.save(role_admin);
        role_user = roleJpa.save(role_user);

        User das = new User("das", passwordEncoder.encode("das"), true, List.of(role_owner, role_admin, role_user));
        userJpa.save(das);

        User admin = new User("admin", passwordEncoder.encode("admin"), true, List.of(role_admin, role_user));
        userJpa.save(admin);

        User user = new User("user", passwordEncoder.encode("user"), true, List.of(role_user));
        userJpa.save(user);

        for (int i = 0; i < 10; i++) {
            int warehouseId = getIdRandom();
            Supplier supplier = saveSupplier(COFFEES.get(random(10)));

            int coffeeCount = generateCoffeeData(supplier, warehouseId);
            int merchCount = generateMerchData(supplier, getIdRandom());
            saveHouse(getIdRandom(), coffeeCount, merchCount);
        }
    }

    private int generateCoffeeData(Supplier supplier, int warehouseId) {
        int count = 0;
        for (int j = 0; j < 3; j++) {
            String coffeeName = COFFEES.get(random(COFFEES.size()));
            Coffee coffee = saveCoffee(supplier, coffeeName);
            CoffeeInventory coffeeInventory = saveCoffeeInventory(coffee, warehouseId);
            count = count + coffeeInventory.getQuantity();
        }
        return count;
    }

    private int generateMerchData(Supplier supplier, int baseMerchId) {
        int count = 0;
        for (int j = 0; j < 3; j++) {
            String merch = MERCH.get(random(MERCH.size()));
            MerchInventory merchInventory = saveMerch(baseMerchId + j, supplier, merch);
            count = count + merchInventory.getQuantity();
        }
        return count;
    }

    private Supplier saveSupplier(String product) {
        Supplier supplier = new Supplier();
        supplier.setName("ИП Поставщик " + product);
        supplier.setStreet(STREET.get(random()) + " " + random(10, 250));
        supplier.setCity(CITY.get(random()));
        supplier.setState("Кыргызстан");
        supplier.setZip(getZipRandom());
        return supplierJpa.save(supplier);
    }

    private Coffee saveCoffee(Supplier supplier, String coffeeName) {
        float price = getPriceRandom();
        CoffeeId coffeeId = new CoffeeId(coffeeName + " " + price, supplier.getId());
        Coffee coffee = new Coffee(coffeeId, price, random(0, 50), getQuantityRandom(), supplier);
        return coffeeJpa.save(coffee);
    }

    private CoffeeInventory saveCoffeeInventory(Coffee coffee, int warehouseId) {
        CoffeeInventoryId coffeeInventoryId = new CoffeeInventoryId(coffee.getId().name(), coffee.getId().supplier(), warehouseId);
        CoffeeInventory coffeeInventory = new CoffeeInventory(coffeeInventoryId, coffee, getQuantityRandom());
        return coffeeInventoryJpa.save(coffeeInventory);
    }

    private MerchInventory saveMerch(int merchId, Supplier supplier, String merch) {
        MerchInventory merchInventory = new MerchInventory(merchId, merch, supplier, getQuantityRandom());
        return merchInventoryJpa.save(merchInventory);
    }

    private CoffeeHouse saveHouse(int storeId, int coffee, int merch) {
        CoffeeHouse coffeeHouse = new CoffeeHouse(storeId, CITY.get(random()), coffee, merch);
        return coffeeHouseJpa.save(coffeeHouse);
    }

    private int getQuantityRandom() {
        return random(50, 100);
    }

    private int getIdRandom() {
        return random(10_000, 99_999);
    }

    private String getZipRandom() {
        return random(100_000, 999_999) + "";
    }

    private int random(int max) {
        return random(0, max);
    }

    private int random() {
        return random(0, 10);
    }

    private int random(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }

    private float getPriceRandom() {
        return ThreadLocalRandom.current().nextFloat() * 100;
    }
}