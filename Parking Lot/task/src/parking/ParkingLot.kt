package parking

class ParkingLot() {

    var capacity: Int = 0
    private var spots: Array<Car?> = emptyArray()

    fun handle(requestLine: String) {

        val request = requestLine.split(" ")
        val type = request.first()

        if (!Command.isValidType(type)) {
            throw Exception("Invalid command")
        }

        val command = Command.findByType(type)

        if (!command.isValidSize(request.size)) {
            throw ParkingLotException("Invalid input size provided")
        }

        if (this.spots.isEmpty() && command.isBlockedUntilCreated()) {
            throw ParkingLotException("Sorry, a parking lot has not been created.")
        }

        when (command) {
            Command.EXIT -> {
                throw ExitCommandException("Bye!")
            }
            Command.CREATE -> {
                this.create(input = request[1])
            }
            Command.STATUS -> {
                this.status()
            }
            Command.PARK -> {
                val car = Car(registrationNumber = request[1], color = request[2].toLowerCase().capitalize())
                this.accommodate(car)
            }
            Command.LEAVE -> {
                val car = this.leave(index = request[1].toInt())
            }
            Command.REG_BY_COLOR -> {
                this.regByColor(input = request[1])
            }
            Command.SPOT_BY_COLOR -> {
                this.spotByColor(request[1])
            }
            Command.SPOT_BY_REG -> {
                this.spotByReg(request[1])
            }
        }
    }

    private fun accommodate(car: Car) {

        if (this.spots.firstOrNull { it -> it?.registrationNumber == car.registrationNumber } != null) {
            throw ParkingLotException("Car has been already parked")
        }

        val freeSpot = this.spots.withIndex().firstOrNull { it -> it.value == null }
        freeSpot ?: throw ParkingLotException("Sorry, the parking lot is full.")

        this.spots[freeSpot.index] = car

        println("${car.color} car parked in spot ${freeSpot.index + 1}.")
    }

    private fun create(input: String) {

        if (this.spots.isNotEmpty()) {
            throw ParkingLotException("A parking lot has been already created.")
        }

        this.capacity = input.toInt()
        this.spots = Array<Car?>(this.capacity) { null }

        println("Created a parking lot with ${this.capacity} spots.")
    }

    private fun status() {

        val items = this.spots
                .withIndex()
                .filter { it.value != null }

        if (items.isEmpty()) {
            throw ParkingLotException("Parking lot is empty.")
        }

        for ((index, car) in items) {
            println("${index + 1} $car")
        }
    }

    private fun leave(index: Int) {

        val realIndex = index - 1
        if (this.spots[realIndex] == null) {
            throw ParkingLotException("There is no car in spot $index.")
        }

        val car = this.spots[realIndex]
        this.spots[realIndex] = null

        println("Spot $index is free.")
    }

    private fun regByColor(input: String) {
        val color = input.toLowerCase().capitalize()
        val items = this.spots
                .filter { it?.color == color }
                .map { it?.registrationNumber }

        if (items.isEmpty()) {
            throw ParkingLotException("No cars with color $input were found.")
        }

        println(items.joinToString())
    }

    private fun spotByColor(input: String) {
        val color = input.toLowerCase().capitalize()
        val items = this.spots
                .withIndex()
                .filter { (index, value) -> value?.color == color }
                .map { (index, value) -> index + 1 }

        if (items.isEmpty()) {
            throw ParkingLotException("No cars with color $input were found.")
        }

        println(items.joinToString())
    }

    private fun spotByReg(registrationNumber: String) {
        val spot = this.spots
                .withIndex()
                .firstOrNull { (index, value) -> value?.registrationNumber == registrationNumber }

        spot ?: throw ParkingLotException("No cars with registration number $registrationNumber were found.")

        println(spot.index + 1)
    }
}