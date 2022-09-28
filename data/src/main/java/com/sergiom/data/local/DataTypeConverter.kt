package com.sergiom.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.sergiom.data.models.LocationDataBaseModel
import com.sergiom.data.models.OriginDataBaseModel

class DataTypeConverter {

    companion object {
        var gson = Gson()

        @TypeConverter
        @JvmStatic
        fun originString(origin: OriginDataBaseModel): String =
            gson.toJson(origin)

        @TypeConverter
        @JvmStatic
        fun stringToOrigin(origin: String): OriginDataBaseModel =
            try {
                gson.fromJson(origin, OriginDataBaseModel::class.java)
            } catch (e: JsonSyntaxException) {
                null
            } ?: OriginDataBaseModel()

        @TypeConverter
        @JvmStatic
        fun locationString(location: LocationDataBaseModel): String =
            gson.toJson(location)

        @TypeConverter
        @JvmStatic
        fun stringToLocation(location: String): LocationDataBaseModel =
            try {
                gson.fromJson(location, LocationDataBaseModel::class.java)
            } catch (e: JsonSyntaxException) {
                null
            } ?: LocationDataBaseModel()

    }

}