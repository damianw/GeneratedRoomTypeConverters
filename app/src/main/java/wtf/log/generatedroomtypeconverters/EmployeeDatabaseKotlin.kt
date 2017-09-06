package wtf.log.generatedroomtypeconverters

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters

/**
 * This class is ostensibly the same as [EmployeeDatabaseJava], except that the Room compiler
 * will not be able to find the type converters generated in [EnumConverters]. Commenting out
 * this file or deleting the class will fix the build.
 */
@Database(entities = arrayOf(Employee::class), version = 1)
@TypeConverters(EnumConverters::class)
abstract class EmployeeDatabaseKotlin : RoomDatabase() {

    abstract fun employeeDao(): EmployeeDao

}
