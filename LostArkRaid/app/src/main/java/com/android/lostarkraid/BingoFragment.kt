package com.android.lostarkraid

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.lostarkraid.databinding.FragmentBaltanHardBinding
import com.android.lostarkraid.databinding.FragmentBingoBinding

class BingoFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentBingoBinding
    private lateinit var bingo: Array<Array<Int>>
    private lateinit var recommendedNumber: Array<Int>
    private var touchCnt:Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBingoBinding.inflate(inflater, container, false)

        val mActivity = activity as MainActivity

        bingo = Array<Array<Int>>(5){Array<Int>(5){0}}
        recommendedNumber = arrayOf(5,5)

        initClickListener()

        binding.resetBtn.setOnClickListener {
            mActivity.changeFrament("BINGORESET")
        }
        return binding.root
    }

    fun chkSecondRecommend(garo:Int, sero:Int):Int{
        Log.d("chkSecondRecommend[1]","bingo["+garo+"]["+sero+"]")

        var secondRecBingo = Array<Array<Int>>(5){Array<Int>(5){0}}
        var dRet:Int

        for(i in 0..4){
            for(j in 0..4){
                secondRecBingo[i][j] = bingo[i][j]
            }
        }
        setClickBingoData(garo,sero,secondRecBingo)

        for(i in 0..4){
            for(j in 0..4){
                dRet = chkThreeCountBingo(i,j,secondRecBingo)
                if(dRet == 0){
                    Log.d("chkSecondRecommend[2]","bingo["+i+"]["+j+"] = ["+dRet+"]")
                    return 0
                }
                else Log.d("chkSecondRecommend[2]","bingo["+i+"]["+j+"] = ["+dRet+"]")
            }
        }

        return -1
    }

    fun setRecommendedInit() {
        for(i in 0..4){
            for(j in 0..4) {
                if (bingo[i][j] == 3) {
                    bingo[i][j] = 0
                    setImageResource(recommendedNumber[0], recommendedNumber[1], 0)
                }
            }
        }
    }

    fun getSkullCnt(garo:Int, sero:Int):Int {
        Log.d("getSkullCnt[1]","garo["+garo+"]")
        Log.d("getSkullCnt[1]","sero["+sero+"]")
        var skullCnt = 0
        var blankCnt = 0
        if(garo != 0) if(bingo[garo-1][sero] != 0) skullCnt++ else if(bingo[garo-1][sero] == 0) blankCnt++
        if(sero != 0) if(bingo[garo][sero-1] != 0) skullCnt++ else if(bingo[garo][sero-1] == 0) blankCnt++
        if(garo != 4) if(bingo[garo+1][sero] != 0) skullCnt++ else if(bingo[garo+1][sero] == 0) blankCnt++
        if(sero != 4) if(bingo[garo][sero+1] != 0) skullCnt++ else if(bingo[garo][sero+1] == 0) blankCnt++
        Log.d("getSkullCnt[2]","skullCnt["+skullCnt+"]")
        Log.d("getSkullCnt[2]","blankCnt["+blankCnt+"]")

        return skullCnt-blankCnt
    }

    //터치카운트 3일때, 빙고 여부 체크 함수
    //input 칸이 빙고일 때 0, 빙고가 아닐 때 -1 return
    fun chkThreeCountBingo(garo:Int, sero:Int, bingo: Array<Array<Int>>):Int {
        Log.d("chkThreeCountBingo[1]","bingo["+garo+"]["+sero+"]")
        var isBingo = 0
        if(bingo[garo][sero] == 1) return -5
        //if(garo != 0) if(bingo[garo-1][sero] == 1 || bingo[garo][sero-1] == 1) return -1
        //if(garo != 4) if(bingo[garo+1][sero] == 1 || bingo[garo][sero+1] == 1) return -1

        for(i in 0..4){
            if(i == sero||i == sero-1||i == sero+1) {
                if (bingo[garo][i] == 1) {
                    Log.d("chkThreeCountBingo[2]","return["+i+"][-1]")
                    break
                }
            }
            else {
                if(bingo[garo][i] == 0) {
                    Log.d("chkThreeCountBingo[2]","return["+i+"][-2]")
                    break
                }
            }
            isBingo++
        }
        if(isBingo == 5) return 0
        isBingo = 0
        for(i in 0..4){
            if(i == garo||i == garo-1||i == garo+1) {
                if (bingo[i][sero] == 1) {
                    Log.d("chkThreeCountBingo[3]","return["+i+"][-3]")
                    break
                }
            }
            else {
                if(bingo[i][sero] == 0){
                    Log.d("chkThreeCountBingo[3]","return["+i+"][-4]")
                    break
                }
            }
            isBingo++
        }
        if(isBingo == 5) return 0
        else return -6
    }

    fun getRecommend(){
        var bingoCnt:Int = 3-(touchCnt-1)%3
        recommendedNumber = arrayOf(5,5)
        var leastCnt = -5

        val bingoArray0 = arrayOf(arrayOf(0,4,1),arrayOf(4,4,1),arrayOf(4,0,1),arrayOf(0,0,1),
            arrayOf(4,1,1),arrayOf(4,2,1),arrayOf(4,3,1), arrayOf(1,4,1),arrayOf(2,4,1),arrayOf(3,4,1),
            arrayOf(1,0,1),arrayOf(2,0,1),arrayOf(3,0,1),arrayOf(0,1,1),arrayOf(0,2,1),arrayOf(0,3,1),
            arrayOf(3,1,0),arrayOf(3,2,0),arrayOf(3,3,0),arrayOf(1,3,0),
            arrayOf(2,3,0),arrayOf(1,1,0),arrayOf(1,2,0),arrayOf(2,1,0),arrayOf(2,2,0))

        //추천 칸 찾기
        for(i in 0..24) {
            Log.d("getRecommend[1]","bingoArray0["+i+"]")
            if(bingo[bingoArray0[i][0]][bingoArray0[i][1]] != 0) continue
            if(bingoCnt == 1){
                var dThreeRet = chkThreeCountBingo(bingoArray0[i][0],bingoArray0[i][1],bingo)
                if(dThreeRet != 0) {
                    Log.d("chkThreeCountBingo[R]","bingo["+bingoArray0[i][0]+"]["+bingoArray0[i][1]+"]["+dThreeRet+"]")
                    continue
                }
            }
            if(bingoCnt == 2){
                var dTwoRet = chkSecondRecommend(bingoArray0[i][0],bingoArray0[i][1])
                if(dTwoRet != 0) {
                    Log.d("chkSecondRecommend[R]","bingo["+bingoArray0[i][0]+"]["+bingoArray0[i][1]+"]["+dTwoRet+"]")
                    continue
                }
            }
            bingoArray0[i][2] += getSkullCnt(bingoArray0[i][0],bingoArray0[i][1])
            Log.d("getRecommend[1]","bingoArray0["+i+"][3] = ["+bingoArray0[i][2]+"]")
            if(bingoArray0[i][2] > leastCnt) {
                recommendedNumber[0] = bingoArray0[i][0]
                recommendedNumber[1] = bingoArray0[i][1]
                leastCnt = bingoArray0[i][2]
            }
        }
        Log.d("getRecommend[2]","recommendedNumber = bingo["+recommendedNumber[0]+"]["+recommendedNumber[1]+"]")
        if(recommendedNumber[0] == 5 || recommendedNumber[1] == 5) {
            Log.d("getRecommend[3]","no Bingo recommeneded")
            Log.d("getRecommend[3]","Use Inanna")
            binding.bingoText.setText("빙고 불가능. 이난나를 사용하세요.")
            return
        }
        bingo[recommendedNumber[0]][recommendedNumber[1]] = 3

        return
    }

    fun initClickListener(){
        binding.zeroZero.setOnClickListener(this)
        binding.zeroOne.setOnClickListener(this)
        binding.zeroTwo.setOnClickListener(this)
        binding.zeroThree.setOnClickListener(this)
        binding.zeroFour.setOnClickListener(this)

        binding.oneZero.setOnClickListener(this)
        binding.oneOne.setOnClickListener(this)
        binding.oneTwo.setOnClickListener(this)
        binding.oneThree.setOnClickListener(this)
        binding.oneFour.setOnClickListener(this)

        binding.twoZero.setOnClickListener(this)
        binding.twoOne.setOnClickListener(this)
        binding.twoTwo.setOnClickListener(this)
        binding.twoThree.setOnClickListener(this)
        binding.twoFour.setOnClickListener(this)

        binding.threeZero.setOnClickListener(this)
        binding.threeOne.setOnClickListener(this)
        binding.threeTwo.setOnClickListener(this)
        binding.threeThree.setOnClickListener(this)
        binding.threeFour.setOnClickListener(this)

        binding.fourZero.setOnClickListener(this)
        binding.fourOne.setOnClickListener(this)
        binding.fourTwo.setOnClickListener(this)
        binding.fourThree.setOnClickListener(this)
        binding.fourFour.setOnClickListener(this)
    }

    override fun onClick(v:View){
        when (v.id) {
            R.id.zeroZero -> {
                chkImageResource(0,0)
            }
            R.id.zeroOne -> {
                chkImageResource(0,1)
            }
            R.id.zeroTwo -> {
                chkImageResource(0,2)
            }
            R.id.zeroThree -> {
                chkImageResource(0,3)
            }
            R.id.zeroFour -> {
                chkImageResource(0,4)
            }

            R.id.oneZero -> {
                chkImageResource(1,0)
            }
            R.id.oneOne -> {
                chkImageResource(1,1)
            }
            R.id.oneTwo -> {
                chkImageResource(1,2)
            }
            R.id.oneThree -> {
                chkImageResource(1,3)
            }
            R.id.oneFour -> {
                chkImageResource(1,4)
            }

            R.id.twoZero -> {
                chkImageResource(2,0)
            }
            R.id.twoOne -> {
                chkImageResource(2,1)
            }
            R.id.twoTwo -> {
                chkImageResource(2,2)
            }
            R.id.twoThree -> {
                chkImageResource(2,3)
            }
            R.id.twoFour -> {
                chkImageResource(2,4)
            }

            R.id.threeZero -> {
                chkImageResource(3,0)
            }
            R.id.threeOne -> {
                chkImageResource(3,1)
            }
            R.id.threeTwo -> {
                chkImageResource(3,2)
            }
            R.id.threeThree -> {
                chkImageResource(3,3)
            }
            R.id.threeFour -> {
                chkImageResource(3,4)
            }

            R.id.fourZero -> {
                chkImageResource(4,0)
            }
            R.id.fourOne -> {
                chkImageResource(4,1)
            }
            R.id.fourTwo -> {
                chkImageResource(4,2)
            }
            R.id.fourThree -> {
                chkImageResource(4,3)
            }
            R.id.fourFour -> {
                chkImageResource(4,4)
            }
        }
    }

    fun setClickBingoData(garo:Int,sero:Int, bingoData: Array<Array<Int>>){
        if (bingoData[garo][sero] == 0) {
            bingoData[garo][sero] = 1
        } else if (bingoData[garo][sero] == 1) {
            bingoData[garo][sero] = 0
        }
        if (garo != 0) {
            if (bingoData[garo - 1][sero] == 0) {
                bingoData[garo - 1][sero] = 1
            } else if (bingoData[garo - 1][sero] == 1) {
                bingoData[garo - 1][sero] = 0
            }
        }
        if (sero != 0) {
            if (bingoData[garo][sero - 1] == 0) {
                bingoData[garo][sero - 1] = 1
            } else if (bingoData[garo][sero - 1] == 1) {
                bingoData[garo][sero - 1] = 0
            }
        }
        if (garo != 4) {
            if (bingoData[garo + 1][sero] == 0) {
                bingoData[garo + 1][sero] = 1
            } else if (bingoData[garo + 1][sero] == 1) {
                bingoData[garo + 1][sero] = 0
            }
        }
        if (sero != 4) {
            if (bingoData[garo][sero + 1] == 0) {
                bingoData[garo][sero + 1] = 1
            } else if (bingoData[garo][sero + 1] == 1) {
                bingoData[garo][sero + 1] = 0
            }
        }
    }

    fun chkImageResource(garo:Int, sero:Int){
        Log.d("chkImageResource","garo = ["+garo+"]")
        Log.d("chkImageResource","sero = ["+sero+"]")
        Log.d("chkImageResource","bingoCnt = ["+bingo[garo][sero]+"]")
        Log.d("chkImageResource","touchCnt = ["+touchCnt+"]")
        setRecommendedInit()
        if(touchCnt < 1) {
            if(bingo[garo][sero] != 0) binding.bingoText.setText("잘못 누르셨습니다.")
            else {
                bingo[garo][sero] = 1
                touchCnt++
                if (touchCnt == 1) {
                    var bingoCnt:Int = 3-(touchCnt-1)%3
                    binding.bingoText.setText("폭탄까지 "+bingoCnt+"번 남았습니다.")
                    getRecommend()
                }
            }
        }
        else {
            setClickBingoData(garo,sero,bingo)
            chkBingo(bingo)
            touchCnt++
            var bingoCnt:Int = 3-(touchCnt-1)%3
            binding.bingoText.setText("폭탄까지 "+bingoCnt+"번 남았습니다.")
            getRecommend()
        }
        chkBingoImage(bingo)
    }

    fun setImageResource(garo: Int, sero: Int, bingoCnt:Int){
        when (garo) {
            0 -> {
                when (sero) {
                    0 -> {
                        when (bingoCnt) {
                            0 -> {
                                binding.zeroZero1.setImageResource(R.drawable.rectangle)
                            }
                            1 -> {
                                binding.zeroZero1.setImageResource(R.drawable.rectangle_white)
                            }
                            2 -> {
                                binding.zeroZero1.setImageResource(R.drawable.rectangle_red)
                            }
                            3 -> {
                                binding.zeroZero1.setImageResource(R.drawable.rectangle_ddabong)
                            }
                        }
                    }
                    1 -> {
                        when (bingoCnt) {
                            0 -> {
                                binding.zeroOne1.setImageResource(R.drawable.rectangle)
                            }
                            1 -> {
                                binding.zeroOne1.setImageResource(R.drawable.rectangle_white)
                            }
                            2 -> {
                                binding.zeroOne1.setImageResource(R.drawable.rectangle_red)
                            }
                            3 -> {
                                binding.zeroOne1.setImageResource(R.drawable.rectangle_ddabong)
                            }
                        }
                    }
                    2 -> {
                        when (bingoCnt) {
                            0 -> {
                                binding.zeroTwo1.setImageResource(R.drawable.rectangle)
                            }
                            1 -> {
                                binding.zeroTwo1.setImageResource(R.drawable.rectangle_white)
                            }
                            2 -> {
                                binding.zeroTwo1.setImageResource(R.drawable.rectangle_red)
                            }
                            3 -> {
                                binding.zeroTwo1.setImageResource(R.drawable.rectangle_ddabong)
                            }
                        }
                    }
                    3 -> {
                        when (bingoCnt) {
                            0 -> {
                                binding.zeroThree1.setImageResource(R.drawable.rectangle)
                            }
                            1 -> {
                                binding.zeroThree1.setImageResource(R.drawable.rectangle_white)
                            }
                            2 -> {
                                binding.zeroThree1.setImageResource(R.drawable.rectangle_red)
                            }
                            3 -> {
                                binding.zeroThree1.setImageResource(R.drawable.rectangle_ddabong)
                            }
                        }
                    }
                    4 -> {
                        when (bingoCnt) {
                            0 -> {
                                binding.zeroFour1.setImageResource(R.drawable.rectangle)
                            }
                            1 -> {
                                binding.zeroFour1.setImageResource(R.drawable.rectangle_white)
                            }
                            2 -> {
                                binding.zeroFour1.setImageResource(R.drawable.rectangle_red)
                            }
                            3 -> {
                                binding.zeroFour1.setImageResource(R.drawable.rectangle_ddabong)
                            }
                        }
                    }
                }
            }
            1 -> {
                when (sero) {
                    0 -> {
                        when (bingoCnt) {
                            0 -> {
                                binding.oneZero1.setImageResource(R.drawable.rectangle)
                            }
                            1 -> {
                                binding.oneZero1.setImageResource(R.drawable.rectangle_white)
                            }
                            2 -> {
                                binding.oneZero1.setImageResource(R.drawable.rectangle_red)
                            }
                            3 -> {
                                binding.oneZero1.setImageResource(R.drawable.rectangle_ddabong)
                            }
                        }
                    }
                    1 -> {
                        when (bingoCnt) {
                            0 -> {
                                binding.oneOne1.setImageResource(R.drawable.rectangle)
                            }
                            1 -> {
                                binding.oneOne1.setImageResource(R.drawable.rectangle_white)
                            }
                            2 -> {
                                binding.oneOne1.setImageResource(R.drawable.rectangle_red)
                            }
                            3 -> {
                                binding.oneOne1.setImageResource(R.drawable.rectangle_ddabong)
                            }
                        }
                    }
                    2 -> {
                        when (bingoCnt) {
                            0 -> {
                                binding.oneTwo1.setImageResource(R.drawable.rectangle)
                            }
                            1 -> {
                                binding.oneTwo1.setImageResource(R.drawable.rectangle_white)
                            }
                            2 -> {
                                binding.oneTwo1.setImageResource(R.drawable.rectangle_red)
                            }
                            3 -> {
                                binding.oneTwo1.setImageResource(R.drawable.rectangle_ddabong)
                            }
                        }
                    }
                    3 -> {
                        when (bingoCnt) {
                            0 -> {
                                binding.oneThree1.setImageResource(R.drawable.rectangle)
                            }
                            1 -> {
                                binding.oneThree1.setImageResource(R.drawable.rectangle_white)
                            }
                            2 -> {
                                binding.oneThree1.setImageResource(R.drawable.rectangle_red)
                            }
                            3 -> {
                                binding.oneThree1.setImageResource(R.drawable.rectangle_ddabong)
                            }
                        }
                    }
                    4 -> {
                        when (bingoCnt) {
                            0 -> {
                                binding.oneFour1.setImageResource(R.drawable.rectangle)
                            }
                            1 -> {
                                binding.oneFour1.setImageResource(R.drawable.rectangle_white)
                            }
                            2 -> {
                                binding.oneFour1.setImageResource(R.drawable.rectangle_red)
                            }
                            3 -> {
                                binding.oneFour1.setImageResource(R.drawable.rectangle_ddabong)
                            }
                        }
                    }
                }
            }
            2 -> {
                when (sero) {
                    0 -> {
                        when (bingoCnt) {
                            0 -> {
                                binding.twoZero1.setImageResource(R.drawable.rectangle)
                            }
                            1 -> {
                                binding.twoZero1.setImageResource(R.drawable.rectangle_white)
                            }
                            2 -> {
                                binding.twoZero1.setImageResource(R.drawable.rectangle_red)
                            }
                            3 -> {
                                binding.twoZero1.setImageResource(R.drawable.rectangle_ddabong)
                            }
                        }
                    }
                    1 -> {
                        when (bingoCnt) {
                            0 -> {
                                binding.twoOne1.setImageResource(R.drawable.rectangle)
                            }
                            1 -> {
                                binding.twoOne1.setImageResource(R.drawable.rectangle_white)
                            }
                            2 -> {
                                binding.twoOne1.setImageResource(R.drawable.rectangle_red)
                            }
                            3 -> {
                                binding.twoOne1.setImageResource(R.drawable.rectangle_ddabong)
                            }
                        }
                    }
                    2 -> {
                        when (bingoCnt) {
                            0 -> {
                                binding.twoTwo1.setImageResource(R.drawable.rectangle)
                            }
                            1 -> {
                                binding.twoTwo1.setImageResource(R.drawable.rectangle_white)
                            }
                            2 -> {
                                binding.twoTwo1.setImageResource(R.drawable.rectangle_red)
                            }
                            3 -> {
                                binding.twoTwo1.setImageResource(R.drawable.rectangle_ddabong)
                            }
                        }
                    }
                    3 -> {
                        when (bingoCnt) {
                            0 -> {
                                binding.twoThree1.setImageResource(R.drawable.rectangle)
                            }
                            1 -> {
                                binding.twoThree1.setImageResource(R.drawable.rectangle_white)
                            }
                            2 -> {
                                binding.twoThree1.setImageResource(R.drawable.rectangle_red)
                            }
                            3 -> {
                                binding.twoThree1.setImageResource(R.drawable.rectangle_ddabong)
                            }
                        }
                    }
                    4 -> {
                        when (bingoCnt) {
                            0 -> {
                                binding.twoFour1.setImageResource(R.drawable.rectangle)
                            }
                            1 -> {
                                binding.twoFour1.setImageResource(R.drawable.rectangle_white)
                            }
                            2 -> {
                                binding.twoFour1.setImageResource(R.drawable.rectangle_red)
                            }
                            3 -> {
                                binding.twoFour1.setImageResource(R.drawable.rectangle_ddabong)
                            }
                        }
                    }
                }
            }
            3 -> {
                when (sero) {
                    0 -> {
                        when (bingoCnt) {
                            0 -> {
                                binding.threeZero1.setImageResource(R.drawable.rectangle)
                            }
                            1 -> {
                                binding.threeZero1.setImageResource(R.drawable.rectangle_white)
                            }
                            2 -> {
                                binding.threeZero1.setImageResource(R.drawable.rectangle_red)
                            }
                            3 -> {
                                binding.threeZero1.setImageResource(R.drawable.rectangle_ddabong)
                            }
                        }
                    }
                    1 -> {
                        when (bingoCnt) {
                            0 -> {
                                binding.threeOne1.setImageResource(R.drawable.rectangle)
                            }
                            1 -> {
                                binding.threeOne1.setImageResource(R.drawable.rectangle_white)
                            }
                            2 -> {
                                binding.threeOne1.setImageResource(R.drawable.rectangle_red)
                            }
                            3 -> {
                                binding.threeOne1.setImageResource(R.drawable.rectangle_ddabong)
                            }
                        }
                    }
                    2 -> {
                        when (bingoCnt) {
                            0 -> {
                                binding.threeTwo1.setImageResource(R.drawable.rectangle)
                            }
                            1 -> {
                                binding.threeTwo1.setImageResource(R.drawable.rectangle_white)
                            }
                            2 -> {
                                binding.threeTwo1.setImageResource(R.drawable.rectangle_red)
                            }
                            3 -> {
                                binding.threeTwo1.setImageResource(R.drawable.rectangle_ddabong)
                            }
                        }
                    }
                    3 -> {
                        when (bingoCnt) {
                            0 -> {
                                binding.threeThree1.setImageResource(R.drawable.rectangle)
                            }
                            1 -> {
                                binding.threeThree1.setImageResource(R.drawable.rectangle_white)
                            }
                            2 -> {
                                binding.threeThree1.setImageResource(R.drawable.rectangle_red)
                            }
                            3 -> {
                                binding.threeThree1.setImageResource(R.drawable.rectangle_ddabong)
                            }
                        }
                    }
                    4 -> {
                        when (bingoCnt) {
                            0 -> {
                                binding.threeFour1.setImageResource(R.drawable.rectangle)
                            }
                            1 -> {
                                binding.threeFour1.setImageResource(R.drawable.rectangle_white)
                            }
                            2 -> {
                                binding.threeFour1.setImageResource(R.drawable.rectangle_red)
                            }
                            3 -> {
                                binding.threeFour1.setImageResource(R.drawable.rectangle_ddabong)
                            }
                        }
                    }
                }
            }
            4 -> {
                when (sero) {
                    0 -> {
                        when (bingoCnt) {
                            0 -> {
                                binding.fourZero1.setImageResource(R.drawable.rectangle)
                            }
                            1 -> {
                                binding.fourZero1.setImageResource(R.drawable.rectangle_white)
                            }
                            2 -> {
                                binding.fourZero1.setImageResource(R.drawable.rectangle_red)
                            }
                            3 -> {
                                binding.fourZero1.setImageResource(R.drawable.rectangle_ddabong)
                            }
                        }
                    }
                    1 -> {
                        when (bingoCnt) {
                            0 -> {
                                binding.fourOne1.setImageResource(R.drawable.rectangle)
                            }
                            1 -> {
                                binding.fourOne1.setImageResource(R.drawable.rectangle_white)
                            }
                            2 -> {
                                binding.fourOne1.setImageResource(R.drawable.rectangle_red)
                            }
                            3 -> {
                                binding.fourOne1.setImageResource(R.drawable.rectangle_ddabong)
                            }
                        }
                    }
                    2 -> {
                        when (bingoCnt) {
                            0 -> {
                                binding.fourTwo1.setImageResource(R.drawable.rectangle)
                            }
                            1 -> {
                                binding.fourTwo1.setImageResource(R.drawable.rectangle_white)
                            }
                            2 -> {
                                binding.fourTwo1.setImageResource(R.drawable.rectangle_red)
                            }
                            3 -> {
                                binding.fourTwo1.setImageResource(R.drawable.rectangle_ddabong)
                            }
                        }
                    }
                    3 -> {
                        when (bingoCnt) {
                            0 -> {
                                binding.fourThree1.setImageResource(R.drawable.rectangle)
                            }
                            1 -> {
                                binding.fourThree1.setImageResource(R.drawable.rectangle_white)
                            }
                            2 -> {
                                binding.fourThree1.setImageResource(R.drawable.rectangle_red)
                            }
                            3 -> {
                                binding.fourThree1.setImageResource(R.drawable.rectangle_ddabong)
                            }
                        }
                    }
                    4 -> {
                        when (bingoCnt) {
                            0 -> {
                                binding.fourFour1.setImageResource(R.drawable.rectangle)
                            }
                            1 -> {
                                binding.fourFour1.setImageResource(R.drawable.rectangle_white)
                            }
                            2 -> {
                                binding.fourFour1.setImageResource(R.drawable.rectangle_red)
                            }
                            3 -> {
                                binding.fourFour1.setImageResource(R.drawable.rectangle_ddabong)
                            }
                        }
                    }
                }
            }
        }
    }

    fun chkBingo(bingo:Array<Array<Int>>){
        Log.d("chkBingo","chkBingo Start")
        for(garo in 0..4){
            for(sero in 0..4){
                Log.d("chkBingo","garo = ["+garo+"]")
                Log.d("chkBingo","sero = ["+sero+"]")
                var isBingo = 0
                for(i in 0..4){
                    Log.d("chkBingo","for i= ["+i+"]")
                    if(bingo[garo][i] != 0){
                        isBingo++
                        Log.d("chkBingo","isBingo= ["+isBingo+"]")
                    }
                }
                if(isBingo == 5){
                    Log.d("chkBingo","isBingo= ["+isBingo+"]")
                    for(i in 0..4){
                        Log.d("chkBingo","isBingo= ["+isBingo+"]")
                        bingo[garo][i] = 2
                    }
                }
                isBingo = 0
                for(i in 0..4){
                    if(bingo[i][sero] != 0){
                        isBingo++
                        Log.d("chkBingo","isBingo= ["+isBingo+"]")
                    }
                }
                if(isBingo == 5) {
                    Log.d("chkBingo","isBingo= ["+isBingo+"]")
                    for (i in 0..4) {
                        Log.d("chkBingo","isBingo= ["+isBingo+"]")
                        bingo[i][sero] = 2
                    }
                }
            }
        }
    }
    fun chkBingoImage(bingo:Array<Array<Int>>){
        for(garo in 0..4){
            for(sero in 0..4){
                var i = bingo[garo][sero]
                setImageResource(garo,sero,i)
            }
        }
    }
}