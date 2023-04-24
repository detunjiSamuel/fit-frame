import android.content.Context
import android.os.AsyncTask
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class RewardHelper(private val context: Context, private val apiKey: String) {

    private val placesEndpoint = "https://maps.googleapis.com/maps/api/place/nearbysearch/json"

    fun getRewardNearBy(type: String, latitude: Double, longitude: Double, radius: Int, callback: (String?) -> Unit) {
        val latLng = "$latitude,$longitude"
        val url = "$placesEndpoint?location=$latLng&radius=$radius&type=$type&key=$apiKey"
        DownloadNearbyPlacesTask(callback).execute(url)
    }

    private class DownloadNearbyPlacesTask(private val callback: (String?) -> Unit) :
        AsyncTask<String, Void, String>() {

        override fun doInBackground(vararg urls: String): String? {
            try {
                val url = URL(urls[0])
                val urlConnection = url.openConnection() as HttpURLConnection
                val inputStream = urlConnection.inputStream
                val bufferedReader = BufferedReader(InputStreamReader(inputStream))
                val stringBuilder = StringBuilder()
                var line: String?
                while (bufferedReader.readLine().also { line = it } != null) {
                    stringBuilder.append(line)
                }
                bufferedReader.close()
                return stringBuilder.toString()
            } catch (e: IOException) {
                return null
            }
        }

        override fun onPostExecute(result: String?) {
            try {
                val jsonObject = JSONObject(result)
                if (jsonObject.has("results")) {
                    val results = jsonObject.getJSONArray("results")
                    if (results.length() > 0) {
                        val business = results.getJSONObject(0)
                        val name = business.getString("name")
                        val address = business.getString("vicinity")
                        val discount = "10%" // Replace with the actual discount offered by the business
                        val message =
                            "Way to go! You've completed all the steps of your productivity challenge. Now it's time to treat yourself to a relaxing $name at $address and get $discount off!"
                        callback(message)
                    } else {
                        callback("Sorry, we couldn't find any businesses near your location.")
                    }
                } else {
                    callback("Error: Missing results field in API response.")
                }
            } catch (e: JSONException) {
                callback("Error parsing API response.")
            }
        }
    }
}
