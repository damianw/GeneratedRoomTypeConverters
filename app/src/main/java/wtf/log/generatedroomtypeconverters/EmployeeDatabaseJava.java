package wtf.log.generatedroomtypeconverters;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

@Database(
        entities = Employee.class,
        version = 1
)
@TypeConverters(EnumConverters.class)
public abstract class EmployeeDatabaseJava extends RoomDatabase {

    public abstract EmployeeDao employeeDao();

}
