Public class Undergroundsystem {
    // Stores active check-ins: customerId -> (stationName, time)
    private Map<Integer, Pair<String, Integer>> checkIns;
    // Stores travel data: "startStation-endStation" -> (totalTime, tripCount)
    private Map<String, Pair<Integer, Integer>> travelData;

    public UndergroundSystem() {
        checkIns = new HashMap<>();
        travelData = new HashMap<>();
    }

    public void checkIn(int id, String stationName, int t) {
        checkIns.put(id, new Pair<>(stationName, t));
    }

    public void checkOut(int id, String stationName, int t) {
        Pair<String, Integer> checkInInfo = checkIns.get(id);
        String startStation = checkInInfo.getKey();
        int startTime = checkInInfo.getValue();
        int duration = t - startTime;

        String route = startStation + "-" + stationName;
        Pair<Integer, Integer> data = travelData.getOrDefault(route, new Pair<>(0, 0));
        travelData.put(route, new Pair<>(data.getKey() + duration, data.getValue() + 1));

        checkIns.remove(id);
    }

    public double getAverageTime(String startStation, String endStation) {
        String route = startStation + "-" + endStation;
        Pair<Integer, Integer> data = travelData.get(route);
        return (double) data.getKey() / data.getValue();
    }
}

// Helper Pair class since Java doesn't have a built-in one
class Pair<K, V> {
    private K key;
    private V value;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() { return key; }
    public V getValue() { return value; }
}
