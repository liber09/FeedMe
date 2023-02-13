import DirectionsApi.Companion.API_KEY
import android.util.Log
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.PolyUtil
import kotlinx.coroutines.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class DirectionsApi {

    companion object {
        private const val API_KEY = "AIzaSyBAYnujt3mZxNSpQsrhsG3kOwWu59GThAE"


        fun getDrivingDirections(origin: LatLng, destination: LatLng, callback: (List<LatLng>, String, String) -> Unit): Job {
            return GlobalScope.launch(Dispatchers.Main) {
                val result = async(Dispatchers.IO) {
                    val url = "https://maps.googleapis.com/maps/api/directions/json?" +
                            "origin=${origin.latitude},${origin.longitude}&" +
                            "destination=${destination.latitude},${destination.longitude}&" +
                            "mode=driving&" +
                            "key=$API_KEY"

                    val request = Request.Builder().url(url).build()
                    val client = OkHttpClient()

                    val response = client.newCall(request).execute()

                    if (!response.isSuccessful) {
                        throw IOException("Failed to get directions: ${response.message}")
                    }

                    val json = response.body?.string()
                    val result = JSONObject(json)

                    val routes = result.getJSONArray("routes")
                    val legs = routes.getJSONObject(0).getJSONArray("legs")
                    val steps = legs.getJSONObject(0).getJSONArray("steps")

                    val decodedPaths = mutableListOf<List<LatLng>>()
                    var totalDistance = 0.0
                    var totalDuration = 0.0
                    for (i in 0 until legs.length()) {
                        val leg = legs.getJSONObject(i)
                        totalDistance += leg.getJSONObject("distance").getDouble("value")
                        totalDuration += leg.getJSONObject("duration").getDouble("value")
                    }
                    for (i in 0 until steps.length()) {
                        val step = steps.getJSONObject(i)
                        val points = PolyUtil.decode(step.getJSONObject("polyline").getString("points"))
                        decodedPaths.add(points)
                    }
                    Triple(decodedPaths.flatten(), totalDistance, totalDuration)
                }.await()
                callback(result.first, "${result.second} meters", "${result.third} seconds")
            }
        }

    }

/*
        fun getDrivingDirections(origin: LatLng, destination: LatLng): Deferred<List<LatLng>> {
            return GlobalScope.async {
                val url = "https://maps.googleapis.com/maps/api/directions/json?" +
                        "origin=${origin.latitude},${origin.longitude}&" +
                        "destination=${destination.latitude},${destination.longitude}&" +
                        "mode=driving&" +
                        "key=$API_KEY"

                val request = Request.Builder().url(url).build()
                val client = OkHttpClient()

                val response = client.newCall(request).execute()

                if (!response.isSuccessful) {
                    throw IOException("Failed to get directions: ${response.message}")
                }

                val json = response.body?.string()
                val result = JSONObject(json)

                val routes = result.getJSONArray("routes")
                val legs = routes.getJSONObject(0).getJSONArray("legs")
                val steps = legs.getJSONObject(0).getJSONArray("steps")

                val decodedPaths = mutableListOf<List<LatLng>>()
                for (i in 0 until steps.length()) {
                    val step = steps.getJSONObject(i)
                    val points = PolyUtil.decode(step.getJSONObject("polyline").getString("points"))
                    decodedPaths.add(points)
                }

                decodedPaths.flatten()
            }
        }*/
    }

