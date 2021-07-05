package com.app.androidhomework.models

class MapModel : ArrayList<MapModelItem>()

data class MapModelItem(
    val id: String,
    val is_active: Boolean,
    val is_available: Boolean,
    val lat: Double,
    val license_plate_number: String,
    val lng: Double,
    val remaining_mileage: Double,
    val remaining_range_in_meters: Double,
    val transmission_mode: String,
    val vehicle_make: String,
    val vehicle_pic_absolute_url: String,
    val vehicle_type: String,
    val vehicle_type_id: Double
)