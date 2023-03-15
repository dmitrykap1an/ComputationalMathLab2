package utils

data class Matrix(
    private var matrix: MutableList<MutableList<(Double, Double) -> Double>>
) {

    fun getMatrix() = matrix

    fun setMatrix(matrix: MutableList<MutableList<(Double, Double) -> Double>>){
        this.matrix = matrix
    }

    operator fun times(list: MutableList<Double>): MutableList<(Double, Double) -> Double> {
        val result = mutableListOf<(Double, Double) -> Double>()
        for(i in matrix){
            val tempResult: (Double, Double) -> Double = i[0] * list[0] + i[1] * list[1]
            result.add(tempResult)
        }
        return result
    }

    private operator fun ((Double, Double) -> Double).times(d: Double): (Double, Double) -> Double {
        return {x: Double, y: Double -> this(x, y) * d}
    }


}

private operator fun ((Double, Double) -> Double).plus(f: (Double, Double) -> Double): (Double, Double) -> Double {
    return {x: Double, y: Double -> f(x,y) + this(x,y)}
}


