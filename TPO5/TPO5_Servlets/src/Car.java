public class Car {

    private String type;
    private String brand;
    private String model;
    private int year;
    private String engine;

    public Car() {

    }

    public Car(String type, String brand, String model, int year, String engine) {
        this.type = type;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.engine = engine;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }



    @Override
    public String toString() {
        return "Car{" +
                "type='" + type + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", engine='" + engine + '\'' +
                '}';
    }
}
