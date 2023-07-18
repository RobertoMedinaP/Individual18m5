package com.example.individual18m5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.individual18m5.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //Declaro lo que usaré
        //importante declarar los 2 spinner

        val spinner = binding.spinner
        val spinner2 = binding.spinner2
        val editText = binding.Textinput.editText
        val button = binding.button
        val textomostrado=binding.textView3
        val button2=binding.button2

        //adapter para spinner
        ArrayAdapter.createFromResource(
            //divisas es el array en currencyarray.xml
            this, R.array.Divisas,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
            spinner2.adapter = adapter
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedOption = spinner.selectedItem.toString()
                // aqui se podria dar alguna accion a la eleccion
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // No se seleccionó ninguna opción en spinner, no le dí ninguna funcion
            }
        }

        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedOption = spinner2.selectedItem.toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        button.setOnClickListener {
            // en este valor se guarda lo escrito en el edittext
            val numberInput = editText?.text.toString().toDoubleOrNull()
            //y aca lo de cada spinner
            val selectedOptionSpinner1 = spinner.selectedItem.toString()
            val selectedOptionSpinner2 = spinner2.selectedItem.toString()

            if (numberInput != null) {
                //aca llamo a la funcion convertirvalores a la funcion le paso el numero escrito
                val convertedValue = convertValue(numberInput, selectedOptionSpinner1, selectedOptionSpinner2)
                textomostrado.setText(convertedValue.toString())
            } else {
                editText?.setError("Ingrese un número valido")
            }
        }

        button2.setOnClickListener {
            editText?.text?.clear()
            textomostrado.text = ""
            spinner.setSelection(0)
            spinner2.setSelection(0)
        }
    }

    private fun convertValue(value: Double, fromCurrency: String, toCurrency: String): Double {
        val conversionRate = when {
            //todas las opciones posibles, quizas haya una forma mas facil
            //los valores estan sacados del conversor de google
            fromCurrency == "CLP" && toCurrency == "USD" -> 0.0012
            fromCurrency == "CLP" && toCurrency == "EUR" -> 0.0011
            fromCurrency == "USD" && toCurrency == "EUR" -> 0.89
            fromCurrency == "USD" && toCurrency == "CLP" -> 814.7
            fromCurrency == "EUR" && toCurrency == "CLP" -> 914.79
            fromCurrency == "EUR" && toCurrency == "USD" -> 1.12

            else -> 1.0 // Por defecto, no hay conversión
        }
        //retorna el valor ingresado por la tasa de conversion segun sea el caso
        return value * conversionRate
    }
    }

