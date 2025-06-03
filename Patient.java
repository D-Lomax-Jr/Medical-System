class Patient {
    private int id;
    private String name;
    private int age;
    private String diagnosis;
    private int priority;

    public Patient(int id, String name, int age, String diagnosis, int priority) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.diagnosis = diagnosis;
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public String toString() {
        return "Patient ID: " + id + ", Name: " + name + ", Age: " + age + ", Diagnosis: " + diagnosis + ", Priority: " + priority;
    }
}
