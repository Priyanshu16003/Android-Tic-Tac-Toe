package from.bhaskar.tictactoe

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import from.bhaskar.tictactoe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private val player1 = 0
    private val player2 = 1
    private var activePlayer = player1
    private var gameActive = true
    private lateinit var filedpos: IntArray
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        filedpos = intArrayOf(-1, -1, -1, -1, -1, -1, -1, -1, -1)

        binding.b0.setOnClickListener(this)
        binding.b1.setOnClickListener(this)
        binding.b2.setOnClickListener(this)
        binding.b3.setOnClickListener(this)
        binding.b4.setOnClickListener(this)
        binding.b5.setOnClickListener(this)
        binding.b6.setOnClickListener(this)
        binding.b7.setOnClickListener(this)
        binding.b8.setOnClickListener(this)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onClick(v: View?) {
        val btnClicked = findViewById<Button>(v!!.id)
        val clickedTag = btnClicked.tag.toString().toInt()

        if (!gameActive)
            return

        if (filedpos[clickedTag] != -1)
            return

        filedpos[clickedTag] = activePlayer

        if (activePlayer == player1) {
            btnClicked.text = "O"
            activePlayer = player2
        } else {
            btnClicked.text = "X"
            activePlayer = player1
        }
        checkWinner()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkWinner() {
        val winpos = arrayOf(
            intArrayOf(0, 1, 2), intArrayOf(3, 4, 5), intArrayOf(6, 7, 8),//horizontally
            intArrayOf(0, 3, 6), intArrayOf(1, 4, 7), intArrayOf(2, 5, 8),//vertically
            intArrayOf(0, 4, 8), intArrayOf(2, 4, 6)                    //digonaly
        )

        for (i in winpos.indices) {
            val val0 = winpos[i][0]
            val val1 = winpos[i][1]
            val val2 = winpos[i][2]

            if (filedpos[val0] == filedpos[val1] && filedpos[val1] == filedpos[val2]) {
                if (filedpos[val0] != -1) {
                    gameActive = false
                    if (filedpos[val0] == player1)
                        showMessage("O - is Winner")
                    else
                        showMessage("X - is Winner")
                    return
                }
            }

        }
        var count = 0
        for (element in filedpos) {
            if (element == -1)
                count++
        }
        if (count == 0) {
            showMessage("It's Draw")
            return
        }

    }


    @SuppressLint("InflateParams")
    @RequiresApi(Build.VERSION_CODES.M)
    private fun showMessage(message : String){
        val view = layoutInflater.inflate(R.layout.gameover_dailog,null,false)
        val btnPlayAgain = view.findViewById<Button>(R.id.btnPlayAgain)
        val btnExit = view.findViewById<Button>(R.id.btnExit)
        val tvMessage = view.findViewById<TextView>(R.id.tvMeassage)

        val ad = AlertDialog.Builder(this).apply {
            tvMessage.text = message
            setView(view)
            setCancelable(false)
        }.show()
        btnPlayAgain.setOnClickListener {
            restartGame()
            ad.dismiss()
        }

        btnExit.setOnClickListener { finish() }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun restartGame(){
        filedpos = intArrayOf(-1, -1, -1, -1, -1, -1, -1, -1, -1)
        activePlayer = player1
        gameActive = true

        //Reset Text
        binding.b0.text = ""
        binding.b1.text = ""
        binding.b2.text = ""
        binding.b3.text = ""
        binding.b4.text = ""
        binding.b5.text = ""
        binding.b6.text = ""
        binding.b7.text = ""
        binding.b8.text = ""
    }


}