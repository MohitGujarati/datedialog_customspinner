package com.example.interview_api_call

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.example.interview_api_call.adapter.spinner_adapter
import com.example.interview_api_call.apis.Retrofit_object
import com.example.interview_api_call.model.data_model
import com.example.interview_api_call.model.main_model
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    lateinit var btnspinner: Spinner
    lateinit var btnspinner2: Spinner

    private lateinit var tvStartDate: TextView
    private lateinit var tvEndDate: TextView

    var datalist: ArrayList<data_model> = ArrayList()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnspinner = findViewById(R.id.btnspinner)
        btnspinner2 = findViewById(R.id.btnspinner2)

        tvStartDate = findViewById(R.id.tvStartDate)
        tvEndDate = findViewById(R.id.tvEndDate)

        // Set click listeners on the TextViews to show the DatePickerDialog
        tvStartDate.setOnClickListener {
            showDatePickerDialog(true)

        }
        tvEndDate.setOnClickListener {

            showDatePickerDialog(false)


        }

        getalldata()

        var players = arrayListOf<String>("mohit", "mihir", "mohit", "mihir")
        val arrayAdapter =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, players)

        btnspinner.adapter = arrayAdapter
        btnspinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                postion: Int,
                id: Long
            ) {

                Toast.makeText(this@MainActivity, "${players[postion]}", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                Toast.makeText(this@MainActivity, "empty", Toast.LENGTH_SHORT).show()
            }

        }

        //go to spinner adapter to add custom data from api
        //then remove this data,for loop and
        val data = ArrayList<data_model>()
        for (i in 0..10) {
            data.add(
                data_model(
                    "hello $i",
                    R.drawable.ic_image
                )
            )

            data.add(
                data_model(
                    "mohit $i",
                    R.drawable.background
                )
            )
        }


        //if you are using custom data method then
        //create any arraylist

        val customArraydapter =
            spinner_adapter(this, data)

        btnspinner2.adapter = customArraydapter
        btnspinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                postion: Int,
                id: Long
            ) {

                Toast.makeText(this@MainActivity, "${data[postion]}", Toast.LENGTH_SHORT).show()


            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                Toast.makeText(this@MainActivity, "empty", Toast.LENGTH_SHORT).show()
            }

        }


    }


    private fun showDatePickerDialog(isStartDate: Boolean) {
        val currentDate = Calendar.getInstance()
        val year = currentDate.get(Calendar.YEAR)
        val month = currentDate.get(Calendar.MONTH)
        val dayOfMonth = currentDate.get(Calendar.DAY_OF_MONTH)

        // Set the minimum date to the current date or the start date,
        // depending on which is selected
        //call start date
        val minDate = if (isStartDate) {
            currentDate
        } else {
            getStartDateCalendar()
        }

        val datePickerDialog = DatePickerDialog(this, { _, yearSelected, monthOfYear, dayOfMonth ->
            val selectedDate = Calendar.getInstance()
            selectedDate.set(yearSelected, monthOfYear, dayOfMonth)

            // Check if selected date is on or after the start date
            if (!isStartDate && selectedDate.before(getStartDateCalendar())) {
                // End date must be on or after start date
                showToast("End date cannot be before start date.")
            } else {
                // Set the selected date to the appropriate TextView
                if (isStartDate) {
                    tvStartDate.text = formatDate(selectedDate)
                } else {
                    tvEndDate.text = formatDate(selectedDate)
                }
            }
        }, year, month, dayOfMonth)

        datePickerDialog.datePicker.minDate = minDate.timeInMillis
        datePickerDialog.show()
    }


    private fun getStartDateCalendar(): Calendar {
        val startDateString = tvStartDate.text.toString()
        val startDate = Calendar.getInstance()
        // Parse the start date string into a Calendar object
        if (startDateString.isNotEmpty()) {
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            startDate.time = dateFormat.parse(startDateString)!!

        }
        return startDate
    }


    private fun formatDate(calendar: Calendar): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun getalldata() {


        val retrofit = Retrofit_object.getapi
        val gson = GsonBuilder().setLenient().create()
        retrofit.getnew().enqueue(object : Callback<main_model> {

            override fun onResponse(call: Call<main_model>, response: Response<main_model>) {
                val data = response.body()
                if (data != null) {
                    datalist.addAll(data.Data)
                    Log.d("gotdata", "${datalist.get(1)}")


                } else {
                    Toast.makeText(this@MainActivity, "No data found", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<main_model>, t: Throwable) {
                Toast.makeText(this@MainActivity, "error", Toast.LENGTH_SHORT).show()
                Log.d("errorlist", "$t")
            }
        })
    }
}