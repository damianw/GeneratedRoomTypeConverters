package wtf.log.generatedroomtypeconverters;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface EmployeeDao {

    @Query("SELECT * FROM Employee")
    List<Employee> getEmployees();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveEmployee(Employee employee);

}
