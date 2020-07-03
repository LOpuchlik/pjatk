public class Car {
    String type;
    String manufacturer;
    String model;
    String engine;

    public Car() {
    }

    public Car(String type, String manufacturer) {
        this.type = type;
        this.manufacturer = manufacturer;
    }

    public Car(String type, String manufacturer, String model, String engine) {
        this.type = type;
        this.manufacturer = manufacturer;
        this.model = model;
        this.engine = engine;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

}
