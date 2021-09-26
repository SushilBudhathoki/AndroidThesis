package com.xrest.finalassignment

import com.google.android.gms.maps.model.LatLng
import com.xrest.finalassignment.Class.*
import com.xrest.finalassignment.Models.User
import com.xrest.finalassignment.Retrofit.FoodRepo
import com.xrest.finalassignment.Retrofit.ResturantRepo
import com.xrest.finalassignment.Retrofit.RetrofitBuilder
import com.xrest.finalassignment.Retrofit.UserRepo
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class ApiTesting {
    val ur = UserRepo()
    @Test
    fun Login()= runBlocking{
        val response = ur.Login("123","123")
        val expectedResult =true
        Assert.assertEquals(expectedResult,response.status)


    }

    @Test
    fun register()= runBlocking {

        val user =User(fname="Asd",lname="asd",phone = "5545+38",username = "555",password = "555")
        val response =ur.register(user)
        val exp = true
        Assert.assertEquals(exp,response.status)
    }

    @Test
    fun addFood()= runBlocking {

        val responses = ur.Login("sonam","sonam").token
        RetrofitBuilder.token=responses

        val foodRepo = FoodRepo()
        val food= Food(Name = "Hawa",Price = "200",Rating = "3",Description = "Des")
val response = foodRepo.insertFood(food,"6044e47bccf32a302441be96")
        val exp =true
        Assert.assertEquals(exp,response.success)
    }

    @Test
fun addToCart() = runBlocking {
    val responses = ur.Login("asd","asd").token
    RetrofitBuilder.token="Bearer "+responses
    val foodRepo = FoodRepo()
    val r2 = foodRepo.book("6044e4bcccf32a302441be99", Bookings(1))
    val exp = true
    Assert.assertEquals(exp,r2.success)


}

    @Test
    fun order() = runBlocking {
        val repo =FoodRepo()
        val res =ur.Login("asd","asd").token
        RetrofitBuilder.token="Bearer "+res
        val response = repo.UserOrder(UserLat("27.72789494297777","85.30856966972351"))
        val exp = true
        Assert.assertEquals(exp,response.success)

    }

@Test
fun updateBooking() = runBlocking {
    val res = ur.Login("asd","asd").token
    RetrofitBuilder.token="Bearer "+res
    val repo =FoodRepo()
    val response = repo.updateBooking("6044e4bcccf32a302441be99", Bookings(5))
    val exp = true
    Assert.assertEquals(exp,response.success)


}

    @Test
    fun addResturant() = runBlocking {
        val res = ur.Login("asd","asd").token
        RetrofitBuilder.token="Bearer "+res
        val repo =ResturantRepo()
        val response = repo.addRest(Restuarant(name="asdasd",address = "asdas",rating = "5",phone = "asdasasd"))
        val exp=true
        Assert.assertEquals(exp,response.data)

    }


    fun Logins()= runBlocking{
        val response = ur.Login("111    ","123")
        val expectedResult =true
        Assert.assertEquals(expectedResult,response.status)


    }
    @Test
    fun updateBookings() = runBlocking {
        val res = ur.Login("asd","asd").token
        RetrofitBuilder.token="Bearer "+res
        val repo =FoodRepo()
        val response = repo.updateBooking("644e4bcccf32a302441be99", Bookings(5))
        val exp = true
        Assert.assertEquals(exp,response.success)


    }



    @Test
    fun addFoods()= runBlocking {

        val responses = ur.Login("sonam","sonam").token
        RetrofitBuilder.token=responses
        val foodRepo = FoodRepo()
        val food= Food(Name = "Hawa",Price = "200",Rating = "3",Description = "Des")
        val response = foodRepo.insertFood(food,"6044e47bccf32a30")
        val exp =true
        Assert.assertEquals(exp,response.success)
    }


}
