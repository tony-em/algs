package algs;

public class Symbol implements Comparable<Symbol> {

    private String name;
    private double weight;
    private String code = "";

    public Symbol(String symbolName, double weight) {
        this.name = symbolName;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    public String getCode() {
        return code;
    }

    public void setName(String symbolName) {
        this.name = symbolName;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public int compareTo(Symbol s) {
        return (this.weight > s.getWeight()) ? 1 : -1;
    }

    @Override
    public String toString() {
        return name.concat(":=" + code);
    }
}
