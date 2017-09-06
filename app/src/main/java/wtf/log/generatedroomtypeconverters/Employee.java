package wtf.log.generatedroomtypeconverters;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import wtf.log.generatedroomtypeconverters.annotation.AutoEnum;

@Entity
public class Employee {

    @PrimaryKey
    private int id;

    private String name;

    private CompensationType compensationType;

    public Employee(int id, String name, CompensationType compensationType) {
        this.id = id;
        this.name = name;
        this.compensationType = compensationType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CompensationType getCompensationType() {
        return compensationType;
    }

    public void setCompensationType(CompensationType compensationType) {
        this.compensationType = compensationType;
    }


    @AutoEnum
    public enum CompensationType {
        HOURLY,
        SALARIED
    }

}
