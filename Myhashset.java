Public class Myhashset {
    private boolean[] set;

    public MyHashSet() {
        // Constraint: values are in range [0, 10^6]
        set = new boolean[1000001];
    }

    public void add(int key) {
        set[key] = true;
    }

    public void remove(int key) {
        set[key] = false;
    }

    public boolean contains(int key) {
        return set[key];
    }
}
