import java.util.*;
import java.util.regex.*;
import java.util.stream.*;

// -------------------- CUSTOM EXCEPTIONS --------------------
class InvalidCapacityException extends Exception {
    public InvalidCapacityException(String msg) {
        super(msg);
    }
}

class CargoSafetyException extends RuntimeException {
    public CargoSafetyException(String msg) {
        super(msg);
    }
}

// -------------------- BOGIE CLASS --------------------
class Bogie {
    String name;
    int capacity;

    public Bogie(String name, int capacity) throws InvalidCapacityException {
        if (capacity <= 0) {
            throw new InvalidCapacityException("Capacity must be greater than zero");
        }
        this.name = name;
        this.capacity = capacity;
    }

    public String toString() {
        return name + "(" + capacity + ")";
    }
}

// -------------------- GOODS BOGIE --------------------
class GoodsBogie {
    String type;
    String cargo;

    public GoodsBogie(String type, String cargo) {
        this.type = type;
        this.cargo = cargo;
    }
}

// -------------------- MAIN APP --------------------
public class Train_Consist_Management_App {

    public static void main(String[] args) {

        System.out.println("=== Train Consist Management App ===");

        // ---------------- UC1 ----------------
        List<String> consist = new ArrayList<>();
        System.out.println("Initial Bogie Count: " + consist.size());

        // ---------------- UC2 ----------------
        List<String> passenger = new ArrayList<>();
        passenger.add("Sleeper");
        passenger.add("AC Chair");
        passenger.add("First Class");
        passenger.remove("AC Chair");
        System.out.println("Passenger Bogies: " + passenger);

        // ---------------- UC3 ----------------
        Set<String> ids = new HashSet<>();
        ids.add("BG101");
        ids.add("BG102");
        ids.add("BG101");
        System.out.println("Unique IDs: " + ids);

        // ---------------- UC4 ----------------
        LinkedList<String> train = new LinkedList<>();
        train.add("Engine");
        train.add("Sleeper");
        train.add("AC");
        train.add("Cargo");
        train.add("Guard");
        train.add(2, "Pantry");
        train.removeFirst();
        train.removeLast();
        System.out.println("Train Order: " + train);

        // ---------------- UC5 ----------------
        Set<String> formation = new LinkedHashSet<>();
        formation.add("Engine");
        formation.add("Sleeper");
        formation.add("Cargo");
        formation.add("Guard");
        formation.add("Sleeper");
        System.out.println("Formation: " + formation);

        // ---------------- UC6 ----------------
        Map<String, Integer> capacityMap = new HashMap<>();
        capacityMap.put("Sleeper", 72);
        capacityMap.put("AC Chair", 56);
        capacityMap.put("First Class", 24);

        capacityMap.forEach((k, v) -> System.out.println(k + " -> " + v));

        // ---------------- UC7 ----------------
        List<Bogie> bogies = new ArrayList<>();
        try {
            bogies.add(new Bogie("Sleeper", 72));
            bogies.add(new Bogie("AC Chair", 56));
            bogies.add(new Bogie("First Class", 24));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        bogies.sort(Comparator.comparingInt(b -> b.capacity));
        System.out.println("Sorted Bogies: " + bogies);

        // ---------------- UC8 ----------------
        List<Bogie> filtered = bogies.stream()
                .filter(b -> b.capacity > 60)
                .toList();
        System.out.println("Filtered (>60): " + filtered);

        // ---------------- UC9 ----------------
        Map<String, List<Bogie>> grouped =
                bogies.stream().collect(Collectors.groupingBy(b -> b.name));

        System.out.println("Grouped: " + grouped);

        // ---------------- UC10 ----------------
        int total = bogies.stream()
                .map(b -> b.capacity)
                .reduce(0, Integer::sum);

        System.out.println("Total Capacity: " + total);

        // ---------------- UC11 ----------------
        String trainId = "TRN-1234";
        Pattern p = Pattern.compile("TRN-\\d{4}");
        System.out.println("Train ID Valid: " + p.matcher(trainId).matches());

        // ---------------- UC12 ----------------
        List<GoodsBogie> goods = List.of(
                new GoodsBogie("Cylindrical", "Petroleum"),
                new GoodsBogie("Rectangular", "Coal")
        );

        boolean safe = goods.stream()
                .allMatch(g -> !g.type.equals("Cylindrical") || g.cargo.equals("Petroleum"));

        System.out.println("Safety Check: " + safe);

        // ---------------- UC13 ----------------
        long start = System.nanoTime();
        List<Bogie> loopResult = new ArrayList<>();
        for (Bogie b : bogies) {
            if (b.capacity > 60) loopResult.add(b);
        }
        long end = System.nanoTime();
        System.out.println("Loop Time: " + (end - start));

        start = System.nanoTime();
        bogies.stream().filter(b -> b.capacity > 60).toList();
        end = System.nanoTime();
        System.out.println("Stream Time: " + (end - start));

        // ---------------- UC14 ----------------
        try {
            new Bogie("Invalid", -10);
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }

        // ---------------- UC15 ----------------
        try {
            String type = "Rectangular";
            String cargo = "Petroleum";

            if (type.equals("Rectangular") && cargo.equals("Petroleum")) {
                throw new CargoSafetyException("Unsafe cargo!");
            }
        } catch (CargoSafetyException e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("Cargo check done");
        }

        // ---------------- UC16 ----------------
        int[] arr = {72, 56, 24, 70};
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
        System.out.println("Bubble Sorted: " + Arrays.toString(arr));

        // ---------------- UC17 ----------------
        String[] names = {"Sleeper", "AC Chair", "First Class"};
        Arrays.sort(names);
        System.out.println("Sorted Names: " + Arrays.toString(names));

        // ---------------- UC18 ----------------
        String[] searchArr = {"BG101", "BG205", "BG309"};
        String key = "BG309";
        boolean found = false;

        for (String s : searchArr) {
            if (s.equals(key)) {
                found = true;
                break;
            }
        }
        System.out.println("Linear Search Found: " + found);

        // ---------------- UC19 ----------------
        Arrays.sort(searchArr);
        int low = 0, high = searchArr.length - 1;
        found = false;

        while (low <= high) {
            int mid = (low + high) / 2;
            int cmp = searchArr[mid].compareTo(key);

            if (cmp == 0) {
                found = true;
                break;
            } else if (cmp < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        System.out.println("Binary Search Found: " + found);

        // ---------------- UC20 ----------------
        List<String> emptyList = new ArrayList<>();

        if (emptyList.isEmpty()) {
            throw new IllegalStateException("Train has no bogies!");
        }
    }
}