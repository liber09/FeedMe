package com.example.feedme
import Drink
import com.example.feedme.DataManagerDishes.dishes
import com.example.feedme.data.Dishes
import com.example.feedme.data.Order
import com.google.firebase.firestore.SetOptions
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object DataManagerOrders{
    val orders = mutableListOf<Order>()
    val OrderRows = mutableListOf<Dishes>()}


