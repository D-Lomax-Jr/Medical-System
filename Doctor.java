class Doctor {
    private int id;
    private String name;
    private String specialization;
    private String availableHours;

    public Doctor(int id, String name, String specialization, String availableHours) {
        this.id = id;
        this.name = name;
        this.specialization = specialization;
        this.availableHours = availableHours;
    }


    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Doctor ID: " + id + ", Name: " + name + ", Specialization: " + specialization + ", Available Hours: " + availableHours;
    }

}