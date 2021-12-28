package com.wpc.calculator

import ParsToCalc
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.Exception
import android.view.Gravity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_0.setOnClickListener { setTextFields("0") }
        btn_1.setOnClickListener { setTextFields("1") }
        btn_2.setOnClickListener { setTextFields("2") }
        btn_3.setOnClickListener { setTextFields("3") }
        btn_4.setOnClickListener { setTextFields("4") }
        btn_5.setOnClickListener { setTextFields("5") }
        btn_6.setOnClickListener { setTextFields("6") }
        btn_7.setOnClickListener { setTextFields("7") }
        btn_8.setOnClickListener { setTextFields("8") }
        btn_9.setOnClickListener { setTextFields("9") }
        minus_btn.setOnClickListener { setTextFields("-") }
        plus_btn.setOnClickListener { setTextFields("+") }
        multiply_btn.setOnClickListener { setTextFields("*") }
        division_btn.setOnClickListener { setTextFields("/") }
        bracket_open_btn.setOnClickListener { setTextFields("(") }
        bracket_close_btn.setOnClickListener { setTextFields(")") }
        dot_btn.setOnClickListener { setTextFields(".") }

        all_clear_btn.setOnClickListener {
            math_operation.text = ""
            result_text.text = ""
        }

        back_btn.setOnClickListener {
            val str = math_operation.text.toString()
            if (str.isNotEmpty())
                math_operation.text = str.substring(0, str.length - 1)
            result_text.text = ""
        }

        equal_btn.setOnClickListener {
            try {
                val result = ParsToCalc().calculate(math_operation.text.toString())
                // создание объекта класса подключенной библиотеки exp4j ."инициализировали"
                //val ex = ExpressionBuilder(math_operation.text.toString()).build()
                //val result = ex.evaluate()// возвращает значение выражения строки


                val longRes = result.toLong()
                if (result == longRes.toDouble())
                    result_text.text = longRes.toString()
                else
                    result_text.text = result.toString()


            } catch (e: Exception) {
                //Log.d("Error", "message: ${e.message}")
                val toast = Toast.makeText(this, "Error: message: ${e.message}", Toast.LENGTH_LONG)
                toast.show()
            }
        }
    }

    fun setTextFields(str: String) {
        // перенос результата в поле ввода при попытке ввода дополнительных значений
        if (result_text.text != "") {
            math_operation.text = result_text.text
            result_text.text = ""
        }
        math_operation.append(str)
    }

    // TODO add logic
}